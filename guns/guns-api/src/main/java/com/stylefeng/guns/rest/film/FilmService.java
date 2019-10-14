package com.stylefeng.guns.rest.film;

import com.stylefeng.guns.rest.film.vo.BannerVo;
import com.stylefeng.guns.rest.film.vo.FilmIndexVo;
import com.stylefeng.guns.rest.film.vo.FilmInfo;
import com.stylefeng.guns.rest.film.vo.FilmVo;

import java.util.List;

public interface FilmService {
    FilmIndexVo getIndex();
    List<BannerVo> getBanners();
    FilmVo getHotFilms();
    FilmVo getSoonFilms();
    List<FilmInfo> getBoxRanking();
    List<FilmInfo> getExpectRanking();
    List<FilmInfo> getTop100();;
}
