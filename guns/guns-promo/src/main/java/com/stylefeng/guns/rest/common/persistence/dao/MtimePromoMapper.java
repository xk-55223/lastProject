package com.stylefeng.guns.rest.common.persistence.dao;

import com.stylefeng.guns.rest.common.persistence.model.MtimePromo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.rest.promoljq.vo.PromoInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xkkk
 * @since 2019-10-19
 */
public interface MtimePromoMapper extends BaseMapper<MtimePromo> {

    List<PromoInfoVo> selectPromos(@Param("cinemaId") Integer cinemaId);

    Integer selectAmountByPromoId(@Param("promoId") Integer promoId);

    Integer insertOrder(@Param("promoId") Integer promoId,@Param("amount") Integer amount);
}
