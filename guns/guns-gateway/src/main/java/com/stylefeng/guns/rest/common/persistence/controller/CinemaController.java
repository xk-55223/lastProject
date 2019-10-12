package com.stylefeng.guns.rest.common.persistence.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.cinema.CinemaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jia.xue
 * @create: 2019-10-12 15:21
 * @Description
 **/
@RestController
@RequestMapping("cinema")
public class CinemaController {


    @Reference(interfaceClass = CinemaService.class)
    CinemaService service;
    @RequestMapping("getNameById")
    public String getNameById(Integer id){
        String username = service.selectNameById(id);
        return username;

    }
}
