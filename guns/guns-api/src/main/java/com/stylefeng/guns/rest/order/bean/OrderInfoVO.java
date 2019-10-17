package com.stylefeng.guns.rest.order.bean;

import lombok.Data;

@Data
public class OrderInfoVO {
    private String orderId;
    private String filmName;
    private String fieldTime;
    private String cinemaName;
    private String seatsName;
    private String orderPrice;
    private String orderStatus;

}
