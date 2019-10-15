package com.stylefeng.guns.rest.common.persistence.dao;

import com.stylefeng.guns.rest.common.persistence.model.MtimeBannerT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.rest.film.vo.BannerVo;
import com.stylefeng.guns.rest.film.vo.FilmInfo;
import com.stylefeng.guns.rest.film.vo.FilmVo;

import java.util.List;

/**
 * <p>
 * banner信息表 Mapper 接口
 * </p>
 *
 * @author lanzhao
 * @since 2019-10-14
 */
public interface FilmMapper extends BaseMapper<MtimeBannerT> {
    List<BannerVo> selectAllBanner();
    List<FilmInfo> selectBoxRanking();
    List<FilmInfo> selectexceptRocking();
    List<FilmInfo> selectTop100();
    List<FilmInfo> selectFilmInfoByStatus(int id);
    int countFilmStatus(int id);
}
