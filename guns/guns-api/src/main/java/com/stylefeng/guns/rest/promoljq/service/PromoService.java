package com.stylefeng.guns.rest.promoljq.service;

import com.stylefeng.guns.rest.promoljq.exception.PromoEnum;
import com.stylefeng.guns.rest.promoljq.vo.PromoInfoVo;
import com.stylefeng.guns.rest.promoljq.vo.PromoResultVO;

import java.util.List;

public interface PromoService {
    List<PromoInfoVo> getPromo(Integer cinemaId);

    PromoEnum transactionCreateOrder(Integer userId, Integer promoId, Integer amount);

    boolean publishPromoStock(Integer cinemaId);

    String generateToken(Integer promoId, Integer userId);
}
