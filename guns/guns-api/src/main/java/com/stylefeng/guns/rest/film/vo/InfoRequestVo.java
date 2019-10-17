package com.stylefeng.guns.rest.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: guns-parent
 * @description:
 * @author: silphon
 * * @create: 2019-10-15 15:14
 **/
@Data
public class InfoRequestVo implements Serializable {
    private static final long serialVersionUID = 4782950768132292028L;
    private String biopgraphy;
    private Actor_director actors ;
    private String filmId;
    private ImgVo imgVO ;
}
