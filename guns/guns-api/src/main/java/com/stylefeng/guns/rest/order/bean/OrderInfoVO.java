package com.stylefeng.guns.rest.order.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderInfoVO implements Serializable {
    private static final long serialVersionUID = 6579838243129585563L;
    private String orderId;
    private String filmName;
    private String fieldTime;
    private String cinemaName;
    private String seatsName;
    private String orderPrice;
    private String orderStatus;
    private String orderTimestamp;
    private String orderTime;

}
