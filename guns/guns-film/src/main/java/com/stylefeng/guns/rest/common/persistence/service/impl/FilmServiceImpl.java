package com.stylefeng.guns.rest.common.persistence.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.common.persistence.dao.FilmMapper;
import com.stylefeng.guns.rest.common.persistence.model.Actor_Film;
import com.stylefeng.guns.rest.film.FilmService;
import com.stylefeng.guns.rest.film.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
        List<SourceInfoVo> sourceInfoVos = filmMapper.selectSourceInfo(id);
        for (SourceInfoVo sourceInfoVo : sourceInfoVos) {
            if (sourceInfoVo.getSourceId().equals(id) ){
                sourceInfoVo.setActive(true);
            }
        }
        return sourceInfoVos;
    }

    @Override
    public List<YearInfoVo> selectyearInfo(String id) {
        List<YearInfoVo> selectYearInfos = filmMapper.selectYearInfo(id);
        for (YearInfoVo selectYearInfo : selectYearInfos) {
            if (selectYearInfo.getYearId().equals(id)){
                selectYearInfo.setActive(true);
            }
        }
        return selectYearInfos;
    }

    @Override
    public FilmDetailVo getFilmDetail(int searchType) {
        FilmDetailVo filmdetail = filmMapper.getFilmDetail(searchType);
        String filmId = Integer.toString(searchType);
        filmdetail.setFilmId(filmId);
        filmdetail.setFilmEnName(filmMapper.selectEnName(filmId));
        filmdetail.setScoreNum(filmMapper.selectScoreNum(filmId));
        /*查询电影类型*/
        String filmCat = filmMapper.selectFilmCat(filmId);
        String[] split = filmCat.split("#");
        StringBuffer info01 = new StringBuffer();
        for (String s : split) {
            if (s != null){
                String cat = filmMapper.selectCatDict(s);
                info01.append(cat + ",");
            }
        }
        filmdetail.setInfo01(info01.substring(5,info01.length()-1));//从第五个字符到倒数第二个字符进行tostring
        /*查询地区和时间*/
        String areaid = filmMapper.selectArea(searchType);
        String area = filmMapper.selectAreaName(Integer.valueOf(areaid));
        String time = filmMapper.selectTime(filmId);
        filmdetail.setInfo02(area + "/" + time + "分钟");
        /*查询上映时间*/
        String filmTime = filmMapper.selectFilmTime(searchType);
        filmdetail.setInfo03(filmTime + area + "上映");
        /*查询演员信息*/
        InfoRequestVo requestVo = new InfoRequestVo();
        requestVo.setBiography(filmMapper.selectBiography(filmId));
        requestVo.setFilmId(filmId);
        String directorId = filmMapper.selectDirectorId(filmId);
        Actor director = filmMapper.selectActor(Integer.valueOf(directorId));
        List<Actor_Film> actorId = filmMapper.selectActorId(filmId);
        List<Actor> actors = new ArrayList<>();
        for (Actor_Film actor : actorId) {
            Actor actor1 = filmMapper.selectActor(Integer.valueOf(actor.getActorId()));
            actor1.setRoleName(actor.getRole_name());
            actors.add(actor1);
        }
        Actor_director actor_director = new Actor_director();
        actor_director.setActors(actors);
        actor_director.setDirector(director);
        requestVo.setActors(actor_director);
        String imgs = filmMapper.selectFilmImgs(filmId);
        String[] img = imgs.split(",");
        ImgVo imgVo = new ImgVo();
        if (img[0] != null){
            imgVo.setMainImg(img[0]);
        }
        if (img[1] != null){
            imgVo.setImg01(img[1]);
        }
        if (img[2] != null){
            imgVo.setImg02(img[2]);
        }
        if (img[3] != null){
            imgVo.setImg03(img[3]);
        }
        if (img[4] != null){
            imgVo.setImg04(img[4]);
        }
        requestVo.setImgVo(imgVo);
        filmdetail.setInfo04(requestVo);
        return filmdetail;
    }

}
