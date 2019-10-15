package com.stylefeng.guns.rest.cinema.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class HallInfoVO implements Serializable {
    private static final long serialVersionUID = -7318616019347384589L;
    private String discountPrice;
    private Integer hallFieldId;
    private String hallName;
    private Integer price;
    private String seatFile;
    private String soldSeats;

}
