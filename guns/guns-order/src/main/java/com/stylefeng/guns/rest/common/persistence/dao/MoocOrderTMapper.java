package com.stylefeng.guns.rest.common.persistence.dao;

import com.stylefeng.guns.rest.common.persistence.model.MoocOrderT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.rest.order.bean.OrderInfoVO;

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

    List<OrderInfoVO> selectOrdersByUsername(String username);
}
