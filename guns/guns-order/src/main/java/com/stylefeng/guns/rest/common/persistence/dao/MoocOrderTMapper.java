package com.stylefeng.guns.rest.common.persistence.dao;

import com.stylefeng.guns.rest.PageInfoVO;
import com.stylefeng.guns.rest.cinema.bean.MtimeCinemaT;
import com.stylefeng.guns.rest.common.persistence.model.MoocOrderT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.rest.film.vo.FilmInfo;
import com.stylefeng.guns.rest.order.vo.FiledVo;
import com.stylefeng.guns.rest.order.vo.OrderVo;
import org.apache.ibatis.annotations.Param;
import com.stylefeng.guns.rest.order.bean.OrderInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * ??????Ϣ? Mapper 接口
 * </p>
 *
 * @author xkkk
 * @since 2019-10-16
 */
public interface MoocOrderTMapper extends BaseMapper<MoocOrderT> {

    List<OrderInfoVO> selectOrdersByUsername(@Param("userId") String userId, @Param("pageInfo") PageInfoVO pageInfo);

    FiledVo selectFieldById(@Param("fieldId") String fieldId);

    List<OrderVo> selectOrderByFiledId(@Param("fieldId") String fieldId);

    FiledVo selectCinemaIdfromFieldByFieldId(@Param("fieldId") Integer fields);

    MtimeCinemaT selectCinemaNameById(@Param("cinemaId") int cinemaId);

    FilmInfo selectFilmNameById(@Param("filmId") int filmId);

    void insertOrder(@Param("order") OrderVo orderVo);

}
