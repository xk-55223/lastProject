package com.stylefeng.guns.rest.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class OrderVo {
    String orderId;
    String filmName;
    String cinemaName;
    String seatsName;
    String cinemaId;
    String fields;
    String fieldId;
    String filmId;
    String seatsIds;
    double filePrice;
    double orderPrice;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    Date orderTime;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    Date orderUser;
    @JsonFormat(pattern = "MM月dd号 hh:mm")
    Date fieldTime;
    String orderStatus;
    long orderTimeStamp;
}
