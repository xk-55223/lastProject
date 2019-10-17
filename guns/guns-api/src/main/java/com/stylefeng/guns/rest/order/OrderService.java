package com.stylefeng.guns.rest.order;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.rest.order.vo.OrderVo;

public interface OrderService {

    boolean isTrueSeats(String fieldId,String seats);

    boolean isNotSoldSeats(String fieldId,String seats);

    OrderVo saveOrderInfo(Integer fields,String soldSeats,String seatsName,Integer userId);

    Page<OrderVo> getOrderByUserId(Integer userId, Page<OrderVo> page);

    String getSoldSeatsByFieldId(Integer fieldId);
}
