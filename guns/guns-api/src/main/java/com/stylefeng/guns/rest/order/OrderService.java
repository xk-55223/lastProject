package com.stylefeng.guns.rest.order;

import com.stylefeng.guns.rest.order.bean.OrderInfoVO;

import java.util.List;

public interface OrderService {
    List<OrderInfoVO> getOrderInfo(String username);
}
