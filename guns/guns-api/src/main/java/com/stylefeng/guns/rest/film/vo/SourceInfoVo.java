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
public class SourceInfoVo implements Serializable {
    private static final long serialVersionUID = 7740701653860145937L;
    boolean active;
    String sourceId;
    String sourceName;
}
