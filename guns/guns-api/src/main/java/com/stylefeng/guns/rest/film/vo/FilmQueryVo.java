package com.stylefeng.guns.rest.film.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FilmQueryVo implements Serializable {


    private static final long serialVersionUID = 2071595339314005116L;
    private Integer status ;
    private String imgPre = "http://img.meetingshop.cn/";
    private int nowPage;
    private int totalPage;
    private List<FilmInfo> data;

}
