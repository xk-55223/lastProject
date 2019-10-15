package com.stylefeng.guns.rest.cinema.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConditionBeanVo implements Serializable {
    private int brandId;
    private int hallType;
    private int areaId;
    private int pageSize;
    private int nowPage;
}
