package com.stylefeng.guns.rest.cinema.service;

import com.stylefeng.guns.rest.cinema.bean.*;

import java.util.List;

public interface CinemaService {
    String selectNameById(Integer id);

    CinemaResponseVo getCinemas(CinemaBeanVo cinemaBeanVo);

    ConditionVo getCondition(ConditionBeanVo conditionBeanVo);
}
