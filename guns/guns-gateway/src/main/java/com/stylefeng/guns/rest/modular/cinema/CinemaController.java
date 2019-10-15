package com.stylefeng.guns.rest.modular.cinema;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.BaseRespVO;
import com.stylefeng.guns.rest.cinema.bean.FieldDetailInfoVO;
import com.stylefeng.guns.rest.cinema.bean.FieldInfoVO;
import com.stylefeng.guns.rest.cinema.service.CinemaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cinema")
public class CinemaController {
    @Reference(interfaceClass = CinemaService.class,check = false)
    CinemaService cinemaServiceService;

    private final String IMG_PRE = "http://img.meetingshop.cn/";

    @RequestMapping("getFields")
    public BaseRespVO getFields(Integer cinemaId) {
        FieldInfoVO fieldInfoVO = cinemaServiceService.getFields(cinemaId);
        BaseRespVO respVO = BaseRespVO.ok(fieldInfoVO);
        respVO.setImgPre(IMG_PRE);
        return respVO;
    }

    @RequestMapping("getFieldInfo")
    public BaseRespVO getFieldInfo(Integer cinemaId, Integer fieldId) {
        FieldDetailInfoVO fieldDetailInfoVO = cinemaServiceService.getFieldInfo(cinemaId,fieldId);
        BaseRespVO respVO = BaseRespVO.ok(fieldDetailInfoVO);
        respVO.setImgPre(IMG_PRE);
        return respVO;
    }

}
