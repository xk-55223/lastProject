package com.stylefeng.guns.rest.cinema.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class HalltypeVo implements Serializable {
    private boolean active;
    private int halltypeId;
    private String halltypeName;
}
