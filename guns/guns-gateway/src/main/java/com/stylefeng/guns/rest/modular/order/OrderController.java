package com.stylefeng.guns.rest.modular.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.BaseRespVO;
import com.stylefeng.guns.rest.PageInfoVO;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.order.OrderService;
import com.stylefeng.guns.rest.order.bean.OrderInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    Jedis jdies;

    @Reference(interfaceClass = OrderService.class , check = false)
    OrderService orderService;

    @RequestMapping("getOrderInfo")
    public BaseRespVO getOrderInfo(PageInfoVO pageInfo, HttpServletRequest request) {
        String token = request.getHeader(jwtProperties.getHeader()).substring(7);
        String userId = jdies.get(token);
        List<OrderInfoVO> orders = orderService.getOrderInfo(userId,pageInfo);
        return BaseRespVO.ok(orders);
    }
}
