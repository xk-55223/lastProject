package com.stylefeng.guns.rest.order;

<<<<<<< HEAD
import com.stylefeng.guns.rest.PageInfoVO;
import com.stylefeng.guns.rest.order.bean.OrderInfoVO;
=======
>>>>>>> 5d33411dbc2ef239fc828a3ec5f651d80ce11ff1

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.rest.order.vo.OrderVo;
import com.stylefeng.guns.rest.order.bean.OrderInfoVO;
import java.util.List;

public interface OrderService {
<<<<<<< HEAD
    List<OrderInfoVO> getOrderInfo(String userId, PageInfoVO pageInfo);
=======

    boolean isTrueSeats(String fieldId,String seats);

    boolean isNotSoldSeats(String fieldId,String seats);

    OrderVo saveOrderInfo(Integer fields,String soldSeats,String seatsName,Integer userId);


    List<OrderInfoVO> getOrderInfo(String username);

>>>>>>> 5d33411dbc2ef239fc828a3ec5f651d80ce11ff1
}
