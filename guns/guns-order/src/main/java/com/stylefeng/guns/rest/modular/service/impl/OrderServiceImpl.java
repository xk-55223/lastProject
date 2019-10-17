package com.stylefeng.guns.rest.modular.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.PageInfoVO;
import com.stylefeng.guns.rest.common.persistence.dao.MoocOrderTMapper;
import com.stylefeng.guns.rest.order.OrderService;
import com.stylefeng.guns.rest.order.bean.OrderInfoVO;
import com.stylefeng.guns.rest.order.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {
    @Autowired
    MoocOrderTMapper orderTMapper;

    @Override
    public List<OrderInfoVO> getOrderInfo(String userId, PageInfoVO pageInfo) {
        List<OrderInfoVO> orderInfos = orderTMapper.selectOrdersByUsername(userId,pageInfo);
        return orderInfos;
    }

    @Override
    public boolean isTrueSeats(String fieldId, String seats) {
        return false;
    }

    @Override
    public boolean isNotSoldSeats(String fieldId, String seats) {
        return false;
    }

    @Override
    public OrderVo saveOrderInfo(Integer fields, String soldSeats, String seatsName, Integer userId) {
        return null;
    }

    @Override
    public List<OrderInfoVO> getOrderInfo(String username) {
        return null;
    }
}
