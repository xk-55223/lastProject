package com.stylefeng.guns.rest.common.persistence.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.film.FilmService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jia.xue
 * @create: 2019-10-12 10:25
 * @Description
 **/
@RestController
@RequestMapping("film")
public class FilmController {


    @Reference(interfaceClass = FilmService.class)
    FilmService filmService;

    @RequestMapping("/getFilmById")
    public String getFilmById(Integer id){

        String fileName = filmService.selectNameById(id);

        return fileName;
    }
}
