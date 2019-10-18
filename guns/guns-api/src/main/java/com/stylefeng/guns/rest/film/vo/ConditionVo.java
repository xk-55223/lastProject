package com.stylefeng.guns.rest.film.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: guns-parent
 * @description:
 * @author: silphon
 * * @create: 2019-10-14 23:59
 **/
@Data
public class ConditionVo implements Serializable {
    private static final long serialVersionUID = -5300291191563888755L;
    private List<CatInfoVo> catInfo;
    private List<SourceInfoVo> sourceInfo;
    private List<YearInfoVo> yearInfo;
}
