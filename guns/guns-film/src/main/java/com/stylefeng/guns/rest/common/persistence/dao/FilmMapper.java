package com.stylefeng.guns.rest.common.persistence.dao;

import com.stylefeng.guns.rest.common.persistence.model.MtimeBannerT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeFilmT;
import com.stylefeng.guns.rest.film.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * banner信息表 Mapper 接口
 * </p>
 *
 * @author lanzhao
 * @since 2019-10-14
 */
public interface FilmMapper extends BaseMapper<MtimeFilmT> {
    List<BannerVo> selectAllBanner();
    List<FilmInfo> selectBoxRanking();
    List<FilmInfo> selectexceptRocking();
    List<FilmInfo> selectTop100();
    List<FilmInfo> selectFilmInfoByStatus(int id);
    int countFilmStatus(int id);
    List<CatInfoVo> selectcatInfo(String id);

}
