package com.stylefeng.guns.rest.modular.pay;

import com.alibaba.dubbo.config.annotation.Service;
import com.alipay.api.AlipayResponse;
import com.alipay.api.domain.Topic;
import com.alipay.api.domain.TradeFundBill;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.rest.common.persistence.dao.MoocOrderTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MoocOrderT;
import com.stylefeng.guns.rest.modular.oss.config.propertise.AliyunProperties;
import com.stylefeng.guns.rest.modular.oss.util.AliyunOss;
import com.stylefeng.guns.rest.modular.pay.config.Configs;
import com.stylefeng.guns.rest.modular.pay.config.properties.AlipayProperties;
import com.stylefeng.guns.rest.modular.pay.model.ExtendParams;
import com.stylefeng.guns.rest.modular.pay.model.GoodsDetail;
import com.stylefeng.guns.rest.modular.pay.model.builder.AlipayTradePrecreateRequestBuilder;
import com.stylefeng.guns.rest.modular.pay.model.builder.AlipayTradeQueryRequestBuilder;
import com.stylefeng.guns.rest.modular.pay.model.result.AlipayF2FPrecreateResult;
import com.stylefeng.guns.rest.modular.pay.model.result.AlipayF2FQueryResult;
import com.stylefeng.guns.rest.modular.pay.service.AlipayMonitorService;
import com.stylefeng.guns.rest.modular.pay.service.AlipayTradeService;
import com.stylefeng.guns.rest.modular.pay.service.impl.AlipayMonitorServiceImpl;
import com.stylefeng.guns.rest.modular.pay.service.impl.AlipayTradeServiceImpl;
import com.stylefeng.guns.rest.modular.pay.service.impl.AlipayTradeWithHBServiceImpl;
import com.stylefeng.guns.rest.modular.pay.utils.Utils;
import com.stylefeng.guns.rest.modular.pay.utils.ZxingUtils;
import com.stylefeng.guns.rest.pay.service.PayService;
import com.stylefeng.guns.rest.pay.vo.OrderPayInfo;
import com.stylefeng.guns.rest.pay.vo.PayInfoVo;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Component
@Service(interfaceClass = PayService.class)
public class PayServiceImpl implements PayService {
    @Autowired
    private MoocOrderTMapper moocOrderTMapper;
    @Autowired
    private AliyunOss aliyunOss;
    @Autowired
    private AlipayProperties alipayProperties;
    @Autowired
    private AliyunProperties aliyunProperties;


    private static Log log = LogFactory.getLog(PayServiceImpl.class);

    // 支付宝当面付2.0服务
    private static AlipayTradeService tradeService;

    // 支付宝当面付2.0服务（集成了交易保障接口逻辑）
    private static AlipayTradeService tradeWithHBService;

    // 支付宝交易保障接口服务，供测试接口api使用，请先阅读readme.txt
    private static AlipayMonitorService monitorService;

    static {
        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

        // 支付宝当面付2.0服务（集成了交易保障接口逻辑）
        tradeWithHBService = new AlipayTradeWithHBServiceImpl.ClientBuilder().build();

        /** 如果需要在程序中覆盖Configs提供的默认参数, 可以使用ClientBuilder类的setXXX方法修改默认参数 否则使用代码中的默认设置 */
        monitorService = new AlipayMonitorServiceImpl.ClientBuilder()
                .setGatewayUrl("http://mcloudmonitor.com/gateway.do").setCharset("GBK")
                .setFormat("json").build();
    }

    // 简单打印应答
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            log.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                log.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            log.info("body:" + response.getBody());
        }
    }

    @Override
    public PayInfoVo getPayInfo(String orderId) throws URISyntaxException, FileNotFoundException {
        PayInfoVo payInfoVo = new PayInfoVo();
        payInfoVo.setOrderId(orderId);
        if (StringUtils.isNotBlank(orderId)) {
            String filePath = test_trade_precreate(orderId);
            String QRCodeAddress = filePath.replace(alipayProperties.getQr().getLocalAddress(), "");
            payInfoVo.setQRCodeAddress(QRCodeAddress);
            InputStream inputStream = new FileInputStream(filePath);
            aliyunOss.upload(inputStream, QRCodeAddress, aliyunProperties.getOss().getUpload().getContentType());
        }
        return payInfoVo;
    }


    // 测试当面付2.0支付
    public String test_trade_precreate(String orderId) {
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = orderId;

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = "性感荷官在线发牌";

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = "9999";

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "9998";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = "澳门赌场性感荷官在线发牌啦";

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
        // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
        GoodsDetail goods1 = GoodsDetail.newInstance("goods_id001", "一号荷官", 9999, 1);
        // 创建好一个商品后添加至商品明细列表
        goodsDetailList.add(goods1);

        // 继续创建并添加第一条商品信息，用户购买的产品为“黑人牙刷”，单价为5.00元，购买了两件
        //GoodsDetail goods2 = GoodsDetail.newInstance("goods_id002", "xxx牙刷", 500, 2);
        //goodsDetailList.add(goods2);

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                // .setNotifyUrl("http://5b0988e595225.cdn.sohucs.com/images/20190221/5c1b73f1e2954af4a044888259978673.gif")//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setGoodsDetailList(goodsDetailList);

        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        String filePath = null;
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝预下单成功: )");

                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);

                // 需要修改为运行机器上的路径
                filePath = String.format(alipayProperties.getQr().getLocalAddress() + alipayProperties.getQr().getAddress(), response.getOutTradeNo());
                log.info("filePath:" + filePath);
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, filePath);
                break;

            case FAILED:
                log.error("支付宝预下单失败!!!");
                break;

            case UNKNOWN:
                log.error("系统异常，预下单状态未知!!!");
                break;

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                break;
        }
        return filePath;
    }

    @Override
    public OrderPayInfo getPayResult(String orderId) {
        OrderPayInfo orderPayInfo = new OrderPayInfo();
        orderPayInfo.setOrderId(orderId);
        if (test_trade_query(orderId)) {
            MoocOrderT moocOrderT = new MoocOrderT();
            moocOrderT.setOrderStatus(1);
            EntityWrapper<MoocOrderT> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("UUID", orderId);
            if (moocOrderTMapper.update(moocOrderT, entityWrapper) == 1) {
                orderPayInfo.setOrderStatus(1);
                orderPayInfo.setOrderMsg("支付成功");
                return orderPayInfo;
            }
        }
        orderPayInfo.setOrderMsg("支付未成功");
        return orderPayInfo;
    }

    // 测试当面付2.0查询订单
    public boolean test_trade_query(String orderId) {
        // (必填) 商户订单号，通过此商户订单号查询当面付的交易状态
        String outTradeNo = orderId;

        // 创建查询请求builder，设置请求参数
        AlipayTradeQueryRequestBuilder builder = new AlipayTradeQueryRequestBuilder()
                .setOutTradeNo(outTradeNo);

        AlipayF2FQueryResult result = tradeService.queryTradeResult(builder);
        boolean succeed = false;
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("查询返回该订单支付成功: )");

                AlipayTradeQueryResponse response = result.getResponse();
                dumpResponse(response);

                log.info(response.getTradeStatus());
                if (Utils.isListNotEmpty(response.getFundBillList())) {
                    for (TradeFundBill bill : response.getFundBillList()) {
                        log.info(bill.getFundChannel() + ":" + bill.getAmount());
                    }
                }
                succeed = true;
                break;

            case FAILED:
                log.error("查询返回该订单支付失败或被关闭!!!");
                break;

            case UNKNOWN:
                log.error("系统异常，订单支付状态未知!!!");
                break;

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                break;
        }
        return succeed;
    }
}
