package com.stylefeng.guns.rest.modular.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.PageInfoVO;
import com.stylefeng.guns.rest.common.persistence.dao.MoocOrderTMapper;
import com.stylefeng.guns.rest.order.OrderService;
import com.stylefeng.guns.rest.order.bean.OrderInfoVO;
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
}
