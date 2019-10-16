package com.stylefeng.guns.rest.modular.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.order.OrderService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Reference(interfaceClass = OrderService.class)
    OrderService orderService;
}
