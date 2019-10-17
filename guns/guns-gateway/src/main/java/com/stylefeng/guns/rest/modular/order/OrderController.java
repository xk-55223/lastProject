package com.stylefeng.guns.rest.modular.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.BaseRespVO;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.order.OrderService;
import com.stylefeng.guns.rest.order.vo.OrderBeanVo;
import com.stylefeng.guns.rest.order.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    Jedis jedis;
    @Reference(interfaceClass = OrderService.class, check = false)
    OrderService orderService;
    @Autowired
    JwtProperties jwtProperties;

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
}
