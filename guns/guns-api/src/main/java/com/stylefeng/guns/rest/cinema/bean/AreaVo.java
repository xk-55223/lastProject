package com.stylefeng.guns.rest.cinema.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class AreaVo implements Serializable {
    int areaId;
    String areaName;
    boolean active;
}
