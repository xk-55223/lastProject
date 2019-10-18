package com.stylefeng.guns.rest.modular.promoljq;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.BaseRespVO;
import com.stylefeng.guns.rest.promoljq.service.PromoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("promo")
public class promoController {
    @Reference(interfaceClass = PromoService.class, check = false)
    private PromoService promoService;

    @RequestMapping("getPromo")
    public BaseRespVO getPromo(Integer cinemaId){
        /*promoService.getPromo(cinemaId);*/
        return null;
    }
}
