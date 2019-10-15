package com.stylefeng.guns.rest.cinema.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class FieldDetailInfoVO implements Serializable {
    private static final long serialVersionUID = 4993524241855501890L;
    private FieldCinemaVO cinemaInfo;
    private FieldFilmInfoVO filmInfo;
    private HallInfoVO hallInfo;

}
