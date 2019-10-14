package com.stylefeng.guns.rest.film.vo;

import lombok.*;
import org.springframework.boot.Banner;

import java.io.Serializable;
import java.util.List;

/**
 * @program: guns-parent
 * @description:
 * @author: silphon
 * * @create: 2019-10-14 20:30
 **/
@Data
public class FilmIndexVo implements Serializable {
    private List<BannerVo> banners;
    private FilmVo hotFilms;
    private FilmVo soonFilms;
    private List<FilmInfo> boxRanking;
    private List<FilmInfo> expectRanking;
    private List<FilmInfo> top100;
}
