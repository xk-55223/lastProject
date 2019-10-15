package com.stylefeng.guns.rest.film.vo;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @program: guns-parent
 * @description:
 * @author: silphon
 * * @create: 2019-10-14 20:44
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FilmVo implements Serializable {
    private int filmNum;
    private int nowPage;
    private int totalPage;
    private List<FilmInfo> filmInfo;
}
