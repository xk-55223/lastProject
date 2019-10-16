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
    private static final long serialVersionUID = -8719672446119775996L;
    boolean active;
    String yearId;
    String yearName;
}
