package com.stylefeng.guns.rest.modular.service;

import com.stylefeng.guns.rest.common.persistence.dao.MoocOrderTMapper;
import com.stylefeng.guns.rest.order.OrderService;
import com.stylefeng.guns.rest.order.bean.OrderInfoVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    @Autowired
    MoocOrderTMapper orderTMapper;
    @Override
    public List<OrderInfoVO> getOrderInfo(String username) {
        List<OrderInfoVO> orderInfos = orderTMapper.selectOrdersByUsername(username);
        return orderInfos;
    }
}
