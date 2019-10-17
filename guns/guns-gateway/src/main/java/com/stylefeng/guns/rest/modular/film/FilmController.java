package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.BaseRespVO;
import com.stylefeng.guns.rest.film.FilmService;
import com.stylefeng.guns.rest.film.vo.FilmQueryVo;
import com.stylefeng.guns.rest.film.vo.FilmRequestVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: guns-parent
 * @description:
 * @author: silphon
 * * @create: 2019-10-14 19:23
 **/
@RestController
@RequestMapping("film")
public class FilmController {
    @Reference(interfaceClass = FilmService.class,check = false)
    FilmService filmService;
    @RequestMapping("getIndex")
    public BaseRespVO getIndex(){
        BaseRespVO respVO = new BaseRespVO();
        return respVO.ok(filmService.getIndex());
    }

    @RequestMapping("getConditionList")
    public BaseRespVO getConditionList(String catId,String sourceId,String yearId){
        BaseRespVO respVO = new BaseRespVO();
        return respVO.ok(filmService.getConditionList(catId,sourceId,yearId));
    }
    @RequestMapping("films")
    public BaseRespVO ShowFilms(int searchType){
        BaseRespVO ok = new BaseRespVO().ok(filmService.getFilmDetail(searchType));
        return ok;
    }

    @RequestMapping("getFilms")
    public BaseRespVO getFilmsList(FilmRequestVo filmRes){
        FilmQueryVo filmsList = filmService.getFilmsList(filmRes);
        BaseRespVO respVO = BaseRespVO.ok(filmsList.getData());
        respVO.setImgPre(filmsList.getImgPre());
        respVO.setTotalPage(filmsList.getTotalPage());
        respVO.setNowPage(filmRes.getNowPage());
        return respVO;
    }

}
