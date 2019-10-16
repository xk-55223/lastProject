package com.stylefeng.guns.rest.film.vo;

import lombok.*;

import java.io.Serializable;

/**
 * @program: guns-parent
 * @description:
 * @author: silphon
 * * @create: 2019-10-14 20:46
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FilmInfo implements Serializable {
    private static final long serialVersionUID = -2110569484034054255L;
    private String filmId;
    private int filmType;
    private String imgAddress;
    private String filmName;
    private String filmScore;
    private int expectNum;
    private String showTime;
    private int boxNum;
    private String score;
}
