package com.stylefeng.guns.rest.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderVo implements Serializable {
    private static final long serialVersionUID = -7464261586134282078L;
    String orderId;
    String filmName;
    String cinemaName;
    String seatsName;
    Integer cinemaId;
    String fields;
    Integer fieldId;
    Integer filmId;
    String seatsIds;
    double filmPrice;
    double orderPrice;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    Date orderTime;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    Integer orderUser;
    String fieldTime;
    Integer orderStatus;
    long orderTimeStamp;
}
