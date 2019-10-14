package com.stylefeng.guns.rest.common.persistence.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.cinema.service.CinemaService;
import com.stylefeng.guns.rest.cinema.bean.MtimeCinemaT;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeCinemaTMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = CinemaService.class)
public class CinemaServiceImpl implements CinemaService {
    @Autowired
    MtimeCinemaTMapper mapper;
    @Override
    public String selectNameById(Integer id) {
        MtimeCinemaT mtimeCinemaT = mapper.selectById(id);
        String cinemaName = mtimeCinemaT.getCinemaName();
        return cinemaName;
    }
}
