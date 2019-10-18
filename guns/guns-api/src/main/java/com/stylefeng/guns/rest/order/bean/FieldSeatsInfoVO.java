package com.stylefeng.guns.rest.order.bean;

import lombok.Data;

import java.util.List;
@Data
public class FieldSeatsInfoVO {
    private String limit;
    private String ids;
    private List<List<SeatInfoVO>> single;
    private List<List<SeatInfoVO>> couple;
}
