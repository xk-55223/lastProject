package com.stylefeng.guns.rest.common.persistence.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeFilmTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeFilmT;
import com.stylefeng.guns.rest.film.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = FilmService.class)
public class FilmServiceImpl implements FilmService {
    @Autowired
    MtimeFilmTMapper filmTMapper;

    @Override
    public String selectNameById(Integer id) {
        MtimeFilmT mtimeFilmT = filmTMapper.selectById(id);
        String filmName = mtimeFilmT.getFilmName();
        return filmName;
    }
}
