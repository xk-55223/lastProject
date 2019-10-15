package com.stylefeng.guns.rest.modular.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.cinema.bean.*;
import com.stylefeng.guns.rest.cinema.service.CinemaService;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeCinemaTMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@Service(interfaceClass = CinemaService.class)
public class CinemaServiceImpl implements CinemaService {
    @Autowired
    MtimeCinemaTMapper cinemaTMapper;

    @Override
    public FieldInfoVO getFields(Integer cinemaId) {
        MtimeCinemaT mtimeCinemaT = cinemaTMapper.selectById(cinemaId);
        FieldCinemaVO cinemaVO = converter2FieldCinemaVO(mtimeCinemaT);
        List<FieldFilmInfoVO> filmList = cinemaTMapper.selectFieldFilmInfoVOs(cinemaId);
        for (FieldFilmInfoVO fieldFilmInfoVO : filmList) {
            List<FieldVO> fields = getFilmFields(cinemaId,fieldFilmInfoVO.getFilmId());
            fieldFilmInfoVO.setFilmFields(fields);
        }
        FieldInfoVO fieldInfoVO = new FieldInfoVO();
        fieldInfoVO.setCinemaInfo(cinemaVO);
        fieldInfoVO.setFilmList(filmList);
        return fieldInfoVO;
    }

    private List<FieldVO> getFilmFields(Integer cinemaId, Integer filmId) {
        return cinemaTMapper.selectFilmFields(cinemaId,filmId);
    }

    private FieldCinemaVO converter2FieldCinemaVO(MtimeCinemaT mtimeCinemaT) {
        FieldCinemaVO cinemaVO = new FieldCinemaVO();
        cinemaVO.setCinemaAdress(mtimeCinemaT.getCinemaAddress());
        cinemaVO.setCinemaId(mtimeCinemaT.getUuid());
        cinemaVO.setCinemaName(mtimeCinemaT.getCinemaName());
        cinemaVO.setCinemaPhone(mtimeCinemaT.getCinemaPhone());
        cinemaVO.setImgUrl(mtimeCinemaT.getImgAddress());
        return cinemaVO;
    }
}
