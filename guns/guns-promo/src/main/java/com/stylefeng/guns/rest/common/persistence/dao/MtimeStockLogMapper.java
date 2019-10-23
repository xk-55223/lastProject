package com.stylefeng.guns.rest.common.persistence.dao;

import com.stylefeng.guns.rest.common.persistence.model.MtimeStockLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.rest.modular.PromoStockStatus;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xkkk
 * @since 2019-10-21
 */
public interface MtimeStockLogMapper extends BaseMapper<MtimeStockLog> {

    Integer insertLog(@Param("stockLog") MtimeStockLog mtimeStockLog);

    void updateStatus(@Param("stockLogId") String stockLogId,@Param("stockStatus") Integer stockStatus);
}
