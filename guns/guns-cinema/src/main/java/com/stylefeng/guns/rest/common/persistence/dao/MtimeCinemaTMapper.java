package com.stylefeng.guns.rest.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.rest.cinema.bean.FieldFilmInfoVO;
import com.stylefeng.guns.rest.cinema.bean.FieldVO;
import com.stylefeng.guns.rest.cinema.bean.MtimeCinemaT;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 影院信息表 Mapper 接口
 * </p>
 *
 * @author xkkk
 * @since 2019-10-12
 */
public interface MtimeCinemaTMapper extends BaseMapper<MtimeCinemaT> {

    List<FieldFilmInfoVO> selectFieldFilmInfoVOs(@Param("cinemaId") Integer cinemaId);

    List<FieldVO> selectFilmFields(@Param("cinemaId") Integer cinemaId, @Param("filmId") Integer filmId);
}
