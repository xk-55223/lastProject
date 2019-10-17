package com.stylefeng.guns.rest.pay.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderPayInfo implements Serializable {
    private static final long serialVersionUID = 2504059688753530113L;
    private String orderId;
    private Integer orderStatus;
    private String orderMsg;
}
