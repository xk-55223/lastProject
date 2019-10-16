package com.stylefeng.guns.rest.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: guns-parent
 * @description:
 * @author: silphon
 * * @create: 2019-10-15 15:19
 **/
@Data
public class Actor implements Serializable {
    private static final long serialVersionUID = -4783932998485534325L;
    private String imgAddress;
    private String directorName;
    private String roleName;
}
