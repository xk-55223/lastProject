package com.stylefeng.guns.rest.promoljq.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PromoInfoVo implements Serializable {
    private static final long serialVersionUID = -90713439085769237L;
    private Integer uuid;
    private String cinemaAddress;
    private Integer cinemaId;
    private String description;
    private String imgAddress;
    private Integer price;
    private Date startTime;
    private Date endTime;
    private Integer status;
    private Integer stock;

}
