package com.stylefeng.guns.rest.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: guns-parent
 * @description:
 * @author: silphon
 * * @create: 2019-10-15 15:35
 **/
@Data
public class ImgVo implements Serializable {
    private static final long serialVersionUID = -3336596151949828255L;
    private String img01;
    private String img02;
    private String img03;
    private String img04;
    private String mainImg;
}
