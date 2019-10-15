package com.stylefeng.guns.rest.cinema.service;

import com.stylefeng.guns.rest.cinema.bean.FieldDetailInfoVO;
import com.stylefeng.guns.rest.cinema.bean.FieldInfoVO;

public interface CinemaService {

    FieldInfoVO getFields(Integer cinemaId);

    FieldDetailInfoVO getFieldInfo(Integer cinemaId, Integer fieldId);
}
