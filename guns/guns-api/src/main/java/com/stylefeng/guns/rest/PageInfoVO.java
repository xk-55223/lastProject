package com.stylefeng.guns.rest;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageInfoVO implements Serializable {
    private static final long serialVersionUID = 8166116746955144837L;
    Integer nowPage = 1;
    Integer pageSize = 5;
}
