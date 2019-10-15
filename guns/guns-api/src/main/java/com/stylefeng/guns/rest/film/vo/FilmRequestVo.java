package com.stylefeng.guns.rest.film.vo;

import lombok.Data;
import java.io.Serializable;

@Data
public class FilmRequestVo implements Serializable {

    private static final long serialVersionUID = -3388127742749315568L;
    private Integer showType = 1;
    private Integer sortId = 1;
    private Integer sourceId = 99;
    private Integer catId = 99;
    private Integer yearId = 99;
    private Integer nowPage = 1;
    private Integer pageSize = 18;
    private String s;
    private String cat;

}
