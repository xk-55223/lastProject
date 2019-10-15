package com.stylefeng.guns.rest.cinema.bean;

import java.io.Serializable;
import java.util.List;

public class FieldInfoVO implements Serializable {
    private static final long serialVersionUID = -3575292080941920430L;
    private FieldCinemaVO cinemaInfo;
   private List<FieldFilmInfoVO> filmList;

    public FieldCinemaVO getCinemaInfo() {
        return cinemaInfo;
    }

    public void setCinemaInfo(FieldCinemaVO cinemaInfo) {
        this.cinemaInfo = cinemaInfo;
    }

    public List<FieldFilmInfoVO> getFilmList() {
        return filmList;
    }

    public void setFilmList(List<FieldFilmInfoVO> filmList) {
        this.filmList = filmList;
    }
}
