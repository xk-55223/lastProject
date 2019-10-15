package com.stylefeng.guns.rest.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: guns-parent
 * @description:
 * @author: silphon
 * * @create: 2019-10-15 00:01
 **/
@Data
public class CatInfoVo implements Serializable {
    boolean active;
    String catId;
    String catName;
}
