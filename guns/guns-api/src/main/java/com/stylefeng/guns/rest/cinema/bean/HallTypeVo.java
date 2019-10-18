package com.stylefeng.guns.rest.cinema.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class HallTypeVo implements Serializable {
    private static final long serialVersionUID = -5773109786024258992L;
    private boolean active;
    private int halltypeId;
    private String halltypeName;
}
