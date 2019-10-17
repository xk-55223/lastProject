package com.stylefeng.guns.rest.modular.cinema;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.rest.BaseRespVO;
import com.stylefeng.guns.rest.cinema.bean.*;
import com.stylefeng.guns.rest.BaseRespVO;
import com.stylefeng.guns.rest.cinema.bean.FieldDetailInfoVO;
import com.stylefeng.guns.rest.cinema.bean.FieldInfoVO;
import com.stylefeng.guns.rest.cinema.service.CinemaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("cinema")
public class CinemaController {
    @Reference(interfaceClass = CinemaService.class,check = false)

    CinemaService service;


    private final String IMG_PRE = "http://img.meetingshop.cn/";

    @RequestMapping("getFields")
    public BaseRespVO getFields(Integer cinemaId) {
        FieldInfoVO fieldInfoVO = service.getFields(cinemaId);
        BaseRespVO respVO = BaseRespVO.ok(fieldInfoVO);
        respVO.setImgPre(IMG_PRE);
        return respVO;
    }


    @RequestMapping("getCinemas")
    public BaseRespVO getCinemas(CinemaBeanVo cinemaBeanVo){

        CinemaResponseVo cinemaInfoVos = null;

        cinemaInfoVos = service.getCinemas(cinemaBeanVo);

        BaseRespVO ok = BaseRespVO.ok(cinemaInfoVos.getMtimeCinemaTS());
        ok.setTotalPage(cinemaInfoVos.getTotalPage());
        ok.setNowPage(cinemaBeanVo.getNowPage());
        if(cinemaBeanVo.getNowPage()==0){
            ok.setNowPage(1);
        }
        return ok;
    }

    @RequestMapping("getCondition")
    public BaseRespVO getCondition(ConditionBeanVo conditionBeanVo) {
        ConditionVo conditionVo = null;
        conditionVo = service.getCondition(conditionBeanVo);

        BaseRespVO ok = BaseRespVO.ok(conditionVo);
        return ok;
    }
    @RequestMapping("getFieldInfo")
    public BaseRespVO getFieldInfo(Integer cinemaId, Integer fieldId) {
        FieldDetailInfoVO fieldDetailInfoVO = service.getFieldInfo(cinemaId,fieldId);
        BaseRespVO respVO = BaseRespVO.ok(fieldDetailInfoVO);
        respVO.setImgPre(IMG_PRE);
        return respVO;
    }

}
