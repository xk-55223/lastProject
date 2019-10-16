package com.stylefeng.guns.rest.film.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: guns-parent
 * @description:
 * @author: silphon
 * * @create: 2019-10-15 15:21
 **/
@Data
public class Actor_director implements Serializable {
    private static final long serialVersionUID = 250023305066376804L;
    private Actor director;
    private List<Actor> actors;
}
