package com.stylefeng.guns.rest.film.vo;

import lombok.*;

import java.io.Serializable;

/**
 * @program: guns-parent
 * @description:
 * @author: silphon
 * * @create: 2019-10-14 20:42
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BannerVo implements Serializable {
    private static final long serialVersionUID = 1513488442135862592L;
    private String bannerId;

    private String bannerAddress;

    private String bannerUrl;
}
