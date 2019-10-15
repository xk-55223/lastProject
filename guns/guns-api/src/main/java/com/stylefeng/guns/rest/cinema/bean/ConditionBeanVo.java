package com.stylefeng.guns.rest.cinema.bean;

import lombok.Data;

@Data
public class ConditionBeanVo {
    private int brandId;
    private int hallType;
    private int areaId;
    private int pageSize;
    private int nowPage;
}
