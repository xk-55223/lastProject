package com.stylefeng.guns.rest.modular.film;

import com.stylefeng.guns.rest.common.persistence.model.MtimeFilmT;
import com.stylefeng.guns.rest.common.persistence.service.IMtimeFilmTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jia.xue
 * @create: 2019-10-12 10:25
 * @Description
 **/
@RestController
public class FilmController {


    @Autowired
    IMtimeFilmTService iMtimeFilmTService;

    @RequestMapping("/test")
    public String test(){
        return "ok";
    }


    @RequestMapping("/getFilmById")
    public MtimeFilmT getFilmById(Integer id){

        MtimeFilmT mtimeFilmT = iMtimeFilmTService.selectById(id);

        return mtimeFilmT;
    }
}