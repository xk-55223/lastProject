package com.stylefeng.guns.rest.modular.cinema;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.BaseRespVO;
import com.stylefeng.guns.rest.cinema.bean.FieldInfoVO;
import com.stylefeng.guns.rest.cinema.service.CinemaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cinema")
public class CinemaController {
    @Reference(interfaceClass = CinemaService.class, check = false)
    CinemaService service;

    @RequestMapping("getFields")
    public BaseRespVO getFields(Integer cinemaId) {
        FieldInfoVO fieldInfoVO = service.getFields(cinemaId);
        return BaseRespVO.ok(fieldInfoVO);
    }

}
