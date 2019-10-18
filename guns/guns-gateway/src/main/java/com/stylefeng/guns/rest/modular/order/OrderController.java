package com.stylefeng.guns.rest.modular.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.BaseRespVO;
import com.stylefeng.guns.rest.config.properties.AliyunProperties;
import com.stylefeng.guns.rest.order.OrderService;
import com.stylefeng.guns.rest.pay.service.PayService;
import com.stylefeng.guns.rest.pay.vo.OrderPayInfo;
import com.stylefeng.guns.rest.pay.vo.PayInfoVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.stylefeng.guns.rest.PageInfoVO;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.order.bean.OrderInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    Jedis jdies;
    @Autowired
    AliyunProperties aliyunProperties;
    @Reference(interfaceClass = OrderService.class, check = false)
    OrderService orderService;

    @Reference(interfaceClass = PayService.class, check = false)
    PayService payService;

    @RequestMapping(value = "getPayInfo", method = RequestMethod.POST)
    public BaseRespVO getPayInfo(String orderId) {
        PayInfoVo payInfoVo = null;
        try {
            payInfoVo = payService.getPayInfo(orderId);
        } catch (URISyntaxException | FileNotFoundException e) {
            e.printStackTrace();
        }
        if (payInfoVo.getQRCodeAddress() != null) {
            BaseRespVO ok = BaseRespVO.ok(payInfoVo);
            ok.setImgPre(aliyunProperties.getOss().getImg().getDomain()+"/");
            return ok;
        } else {
            return BaseRespVO.fail("订单支付失败，请稍后重试");
        }
    }

    @RequestMapping(value = "getPayResult", method = RequestMethod.POST)
    public BaseRespVO getPayResult(String orderId, Integer tryNums) {
        if (tryNums > 3) {
            return BaseRespVO.fail("订单支付失败，请稍后重试");
        }
        OrderPayInfo orderPayInfo = payService.getPayResult(orderId);
        return BaseRespVO.ok(orderPayInfo);
    }

    @RequestMapping("getOrderInfo")
    public BaseRespVO getOrderInfo(PageInfoVO pageInfo, String token) {
        String usernameFromToken = new JwtTokenUtil().getUsernameFromToken(token);
        String username = jdies.get(usernameFromToken);
        List<OrderInfoVO> orders = orderService.getOrderInfo(username);
        return BaseRespVO.ok(orders);
    }
}
