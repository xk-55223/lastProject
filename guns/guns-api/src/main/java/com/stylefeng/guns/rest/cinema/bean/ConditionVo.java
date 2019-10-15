package com.stylefeng.guns.rest.cinema.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ConditionVo implements Serializable {
    private List<AreaVo> areaList;
    private List<HalltypeVo> halltypeList;
    private List<BrandVo> brandList;
}
