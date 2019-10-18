package com.stylefeng.guns.rest.pay.service;

import com.stylefeng.guns.rest.pay.vo.OrderPayInfo;
import com.stylefeng.guns.rest.pay.vo.PayInfoVo;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public interface PayService {
    PayInfoVo getPayInfo(String orderId) throws URISyntaxException, FileNotFoundException;

    OrderPayInfo getPayResult(String orderId);
}
