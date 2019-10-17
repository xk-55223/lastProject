package com.stylefeng.guns.rest.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class FiledVo {
    int filedId;
    int cinemaId;
    int filmId;
    @JsonFormat(pattern = "hh:mm")
    Date beginTime;
    @JsonFormat(pattern = "hh:mm")
    Date endTime;
    int hallId;
    String hallName;
    double price;
}
