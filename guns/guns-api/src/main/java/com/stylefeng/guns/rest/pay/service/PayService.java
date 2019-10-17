package com.stylefeng.guns.rest.pay.service;

import com.stylefeng.guns.rest.pay.vo.OrderPayInfo;
import com.stylefeng.guns.rest.pay.vo.PayInfoVo;

public interface PayService {
    PayInfoVo getPayInfo(String orderId);

    OrderPayInfo getPayResult(String orderId);
}
