package com.stylefeng.guns.rest.common.persistence.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.common.persistence.dao.FilmMapper;
import com.stylefeng.guns.rest.film.FilmService;
import com.stylefeng.guns.rest.film.vo.BannerVo;
import com.stylefeng.guns.rest.film.vo.FilmIndexVo;
import com.stylefeng.guns.rest.film.vo.FilmInfo;
import com.stylefeng.guns.rest.film.vo.FilmVo;
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
}
