package com.stylefeng.guns.rest.cinema.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CinemaResponseVo implements Serializable {
   private int nowPage;
   private int totalPage;
   private List<MtimeCinemaT> mtimeCinemaTS;
}
