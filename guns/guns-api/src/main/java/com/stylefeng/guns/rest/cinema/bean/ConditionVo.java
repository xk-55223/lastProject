package com.stylefeng.guns.rest.cinema.bean;

import lombok.Data;

import java.util.List;

@Data
public class ConditionVo {
    private List<AreaVo> areaList;
    private List<HalltypeVo> halltypeList;
    private List<BrandVo> brandList;
}
