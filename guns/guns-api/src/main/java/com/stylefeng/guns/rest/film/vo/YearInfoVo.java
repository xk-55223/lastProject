package com.stylefeng.guns.rest.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: guns-parent
 * @description:
 * @author: silphon
 * * @create: 2019-10-15 00:02
 **/
@Data
public class YearInfoVo implements Serializable {
    Boolean active;
    String catId;
    String catName;
}
