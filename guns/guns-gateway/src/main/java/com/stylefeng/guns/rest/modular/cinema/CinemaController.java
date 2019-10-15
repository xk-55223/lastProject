package com.stylefeng.guns.rest.modular.cinema;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.rest.BaseRespVO;
import com.stylefeng.guns.rest.cinema.bean.*;
import com.stylefeng.guns.rest.cinema.service.CinemaService;
import com.stylefeng.guns.rest.common.exception.BizExceptionEnum;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("cinema")
public class CinemaController {
    @Reference(interfaceClass = CinemaService.class,check = false)
    CinemaService service;

    /*@RequestMapping(value = "getFields", method = RequestMethod.GET)
    public BaseResVO getFields(Integer cinemaId) {


    }*/
    @RequestMapping("getCinemas")
    public BaseRespVO getCinemas(CinemaBeanVo cinemaBeanVo){

        CinemaResponseVo cinemaInfoVos = null;

        cinemaInfoVos = service.getCinemas(cinemaBeanVo);

        BaseRespVO ok = BaseRespVO.ok(cinemaInfoVos);
        ok.setTotalPage(cinemaInfoVos.getTotalPage());
        ok.setNowPage(cinemaBeanVo.getNowPage());
        if(cinemaBeanVo.getNowPage()==0){
            ok.setNowPage(1);
        }
        return ok;
    }

    @RequestMapping("getCondition")
    public BaseRespVO getCondition(ConditionBeanVo conditionBeanVo){
        ConditionVo conditionVo = null;
        conditionVo = service.getCondition(conditionBeanVo);

        BaseRespVO ok = BaseRespVO.ok(cinemaInfoVos);
        ok.setTotalPage(cinemaInfoVos.getTotalPage());
        ok.setNowPage(cinemaBeanVo.getNowPage());
        if(cinemaBeanVo.getNowPage()==0){
            ok.setNowPage(1);
        }
        return ok;
    }

}
