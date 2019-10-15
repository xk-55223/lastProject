package com.stylefeng.guns.rest.cinema.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class CinemaBeanVo implements Serializable {
    private int brandld;
    private int hallType;
    private int districtId;
    private int pageSize;
    private int nowPage;
}
