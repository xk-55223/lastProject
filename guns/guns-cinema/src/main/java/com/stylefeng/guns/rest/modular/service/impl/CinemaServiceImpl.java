package com.stylefeng.guns.rest.modular.service.impl;

import com.alibaba.dubbo.common.bytecode.Wrapper;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.rest.cinema.bean.*;
import com.stylefeng.guns.rest.cinema.service.CinemaService;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeCinemaTMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service(interfaceClass = CinemaService.class)
public class CinemaServiceImpl implements CinemaService {
    @Autowired
    MtimeCinemaTMapper mtimeCinemaTMapper;

    @Override
    public String selectNameById(Integer id) {
        return null;
    }

    @Override
    public CinemaResponseVo getCinemas(CinemaBeanVo cinemaBeanVo) {
        Page page = new Page<>();
        EntityWrapper<MtimeCinemaT> EntityWrapper = new EntityWrapper<>();
        List<MtimeCinemaT> mtimeCinemaTS1 = mtimeCinemaTMapper.selectPage(page,EntityWrapper);
        long total = page.getTotal();
        CinemaResponseVo cinemaResponseVo = new CinemaResponseVo();
        cinemaResponseVo.setMtimeCinemaTS(mtimeCinemaTS1);
        cinemaResponseVo.setNowPage(cinemaBeanVo.getNowPage());
        cinemaResponseVo.setTotalPage((int) total);

        return cinemaResponseVo;
    }

    @Override
    public ConditionVo getCondition(ConditionBeanVo conditionBeanVo) {
        ConditionVo conditionVo = new ConditionVo();
        conditionVo.setAreaList();
    }
}
