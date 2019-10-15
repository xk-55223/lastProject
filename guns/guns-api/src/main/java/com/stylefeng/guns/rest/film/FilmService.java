package com.stylefeng.guns.rest.film;

import com.stylefeng.guns.rest.film.vo.*;

import java.util.List;

public interface FilmService {
    FilmIndexVo getIndex();
    List<BannerVo> getBanners();
    FilmVo getHotFilms();
    FilmVo getSoonFilms();
    List<FilmInfo> getBoxRanking();
    List<FilmInfo> getExpectRanking();
    List<FilmInfo> getTop100();
    ConditionVo getConditionList(String catId, String sourceId, String yearId);
    List<CatInfoVo> selectcatInfo(String id);
    List<SourceInfoVo> selectsourceInfo(String id);
    List<YearInfoVo> selectyearInfo(String id);

    FilmQueryVo getFilmsList(FilmRequestVo filmRes);
}
