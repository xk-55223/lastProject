package com.stylefeng.guns.rest.cinema.service;

import com.stylefeng.guns.rest.cinema.bean.*;
import com.stylefeng.guns.rest.cinema.bean.FieldDetailInfoVO;
import com.stylefeng.guns.rest.cinema.bean.FieldInfoVO;
import java.util.List;

public interface CinemaService {
    String selectNameById(Integer id);

    CinemaResponseVo getCinemas(CinemaBeanVo cinemaBeanVo);

    ConditionVo getCondition(ConditionBeanVo conditionBeanVo);

    FieldInfoVO getFields(Integer cinemaId);

    FieldDetailInfoVO getFieldInfo(Integer cinemaId, Integer fieldId);

}
