package com.stylefeng.guns.rest.modular.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.BaseRespVO;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.order.OrderService;
import com.stylefeng.guns.rest.order.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import com.stylefeng.guns.rest.pay.service.PayService;
import com.stylefeng.guns.rest.pay.vo.OrderPayInfo;
import com.stylefeng.guns.rest.pay.vo.PayInfoVo;
import org.springframework.web.bind.annotation.RequestMethod;
import com.stylefeng.guns.rest.PageInfoVO;
import com.stylefeng.guns.rest.order.bean.OrderInfoVO;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {
    @Reference(interfaceClass = OrderService.class, check = false)
    OrderService orderService;
    @Autowired
    JwtProperties jwtProperties;
    @Reference(interfaceClass = PayService.class, check = false)
    PayService payService;
    @Autowired
    Jedis jedis;

    @RequestMapping("buyTickets")
    public BaseRespVO insertOrder(Integer fieldId, String soldSeats, String seatsName, HttpServletRequest request) {
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken = null;
        Integer userId = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);
            //验证token是否过期,包含了验证jwt是否正确
            userId = Integer.valueOf(jedis.get(authToken));
        }
        OrderVo orderVo = orderService.saveOrderInfo(fieldId, soldSeats, seatsName, userId);
        BaseRespVO ok = BaseRespVO.ok(orderVo);
        return ok;
    }


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

    @RequestMapping("getOrderInfo")
    public BaseRespVO getOrderInfo(PageInfoVO pageInfo, HttpServletRequest request) {
        String token = request.getHeader(jwtProperties.getHeader()).substring(7);
        String userId = jedis.get(token);
        List<OrderInfoVO> orders = orderService.getOrderInfo(userId,pageInfo);
        return BaseRespVO.ok(orders);
    }
}
