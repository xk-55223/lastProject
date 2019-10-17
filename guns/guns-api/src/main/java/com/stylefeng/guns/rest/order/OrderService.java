package com.stylefeng.guns.rest.order;

import com.stylefeng.guns.rest.PageInfoVO;
import com.stylefeng.guns.rest.order.bean.OrderInfoVO;
import com.stylefeng.guns.rest.order.vo.OrderVo;
import java.util.List;

public interface OrderService {
    List<OrderInfoVO> getOrderInfo(String userId, PageInfoVO pageInfo);

    boolean isTrueSeats(String fieldId,String seats);

    boolean isNotSoldSeats(String fieldId,String seats);

    OrderVo saveOrderInfo(Integer fields,String soldSeats,String seatsName,Integer userId);


    List<OrderInfoVO> getOrderInfo(String username);

}
