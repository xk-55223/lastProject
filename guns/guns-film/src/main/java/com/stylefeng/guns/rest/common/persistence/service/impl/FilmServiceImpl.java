package com.stylefeng.guns.rest.common.persistence.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.common.persistence.dao.FilmMapper;
import com.stylefeng.guns.rest.film.FilmService;
import com.stylefeng.guns.rest.film.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service(interfaceClass = FilmService.class)
public class FilmServiceImpl implements FilmService {
    @Autowired
    FilmMapper filmMapper;

    @Override
    public FilmIndexVo getIndex() {
        FilmIndexVo filmIndexVo = new FilmIndexVo();
        filmIndexVo.setBanners(this.getBanners());
        filmIndexVo.setBoxRanking(this.getBoxRanking());
        filmIndexVo.setExpectRanking(this.getExpectRanking());
        filmIndexVo.setHotFilms(this.getHotFilms());
        filmIndexVo.setSoonFilms(this.getSoonFilms());
        filmIndexVo.setTop100(this.getTop100());
        return filmIndexVo;
    }

    @Override
    public List<BannerVo> getBanners() {
        return filmMapper.selectAllBanner();
    }

    @Override
    public FilmVo getHotFilms() {
        FilmVo filmVo = new FilmVo();
        filmVo.setFilmInfo(filmMapper.selectFilmInfoByStatus(1));
        filmVo.setFilmNum(filmMapper.countFilmStatus(1));
        return filmVo;
    }

    @Override
    public FilmVo getSoonFilms() {
        FilmVo filmVo = new FilmVo();
        filmVo.setFilmInfo(filmMapper.selectFilmInfoByStatus(2));
        filmVo.setFilmNum(filmMapper.countFilmStatus(2));
        return filmVo;
    }

    @Override
    public List<FilmInfo> getBoxRanking() {
        return filmMapper.selectBoxRanking();
    }

    @Override
    public List<FilmInfo> getExpectRanking() {
        return filmMapper.selectexceptRocking();
    }

    @Override
    public List<FilmInfo> getTop100() {
        return filmMapper.selectTop100();
    }

    @Override
    public ConditionVo getConditionList(String catId, String sourceId, String yearId) {
        if (catId == null){
            catId = "99";
        }
        if (sourceId == null){
            sourceId =  "99";
        }
        if (yearId == null){
            yearId =  "99";
        }
        ConditionVo conditionVo = new ConditionVo();
        conditionVo.setCatInfoV(this.selectcatInfo(catId));
        conditionVo.setSourceInfo(this.selectsourceInfo(sourceId));
        conditionVo.setYearInfo(this.selectyearInfo(yearId));
        return conditionVo;
    }

    @Override
    public List<CatInfoVo> selectcatInfo(String id) {
        List<CatInfoVo> catInfoVos = filmMapper.selectcatInfo(id);
        for (CatInfoVo catInfoVo : catInfoVos) {
            if (catInfoVo.getCatId().equals(id)){
                catInfoVo.setActive(true);
            }
        }
        return catInfoVos;
    }

    @Override
    public List<SourceInfoVo> selectsourceInfo(String id) {
        return null;
    }

    @Override
    public List<YearInfoVo> selectyearInfo(String id) {
        return null;
    }

}
