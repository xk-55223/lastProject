package com.stylefeng.guns.rest.cinema.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class BrandVo implements Serializable {
    private int brandId;
    private String brandName;
    private boolean active;
}
