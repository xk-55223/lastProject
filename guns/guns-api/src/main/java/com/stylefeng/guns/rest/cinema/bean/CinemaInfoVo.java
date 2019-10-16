package com.stylefeng.guns.rest.cinema.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class CinemaInfoVo implements Serializable {
    private int UUid;
    private String cinemaName;
    private String address;
    private double minimumPrice;
}
