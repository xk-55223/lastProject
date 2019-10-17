package com.stylefeng.guns.rest.modular.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.BaseRespVO;
import com.stylefeng.guns.rest.order.OrderService;
import com.stylefeng.guns.rest.pay.service.PayService;
import com.stylefeng.guns.rest.pay.vo.OrderPayInfo;
import com.stylefeng.guns.rest.pay.vo.PayInfoVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {

    @Reference(interfaceClass = OrderService.class, check = false)
    OrderService orderService;
    @Reference(interfaceClass = PayService.class, check = false)
    PayService payService;

    @RequestMapping(value = "getPayInfo", method = RequestMethod.POST)
    public BaseRespVO getPayInfo(String orderId) {
        PayInfoVo payInfoVo = payService.getPayInfo(orderId);
        if (payInfoVo.getQRCodeAddress() != null) {
            BaseRespVO ok = BaseRespVO.ok(payInfoVo);
            ok.setImgPre("D:/");
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
}
