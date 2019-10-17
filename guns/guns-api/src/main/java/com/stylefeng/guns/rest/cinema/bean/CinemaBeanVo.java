package com.stylefeng.guns.rest.cinema.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class CinemaBeanVo implements Serializable {
    private int brandId;
    private int halltypeId;
    private int districtId;
    private int areaId;
    private int pageSize;
    private int nowPage;
}
