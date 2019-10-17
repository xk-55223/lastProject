package com.stylefeng.guns.rest.modular.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.rest.cinema.bean.MtimeCinemaT;
import com.stylefeng.guns.rest.common.persistence.dao.MoocOrderTMapper;
import com.stylefeng.guns.rest.film.vo.FilmInfo;
import com.stylefeng.guns.rest.film.vo.FilmVo;
import com.stylefeng.guns.rest.order.OrderService;
import com.stylefeng.guns.rest.order.vo.FiledVo;
import com.stylefeng.guns.rest.order.vo.OrderVo;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {
    @Autowired
    MoocOrderTMapper moocOrderTMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean isTrueSeats(String fieldId, String seats) {
        FiledVo filedVo = moocOrderTMapper.selectFieldById(fieldId);

        String s = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24";
        redisTemplate.opsForValue().set("Ids",s);
        String ids1 = (String) redisTemplate.opsForValue().get("Ids");
        String[] split = ids1.split(",");
        for (String s1 : split) {
            if (fieldId!=null&&s1.equals(seats)){
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean isNotSoldSeats(String fieldId, String seats) {
        List<OrderVo> orderVo = moocOrderTMapper.selectOrderByFiledId(fieldId);
        String[] split = seats.split(",");

        for (OrderVo vo : orderVo) {
            String seatsIds = vo.getSeatsIds();
            String[] split1 = seatsIds.split(",");
            for (String s : split1) {
                for (String s1 : split) {
                    if(s.equals(s1)){
                        return false;
                    }
                }
            }
        }

        return true;
    }

    @Override
    public OrderVo saveOrderInfo(Integer fields, String soldSeats, String seatsName, Integer userId) {
        String[] split = soldSeats.split(",");
        String s2 = String.valueOf(fields);
        for (String s : split) {
            if(isTrueSeats(s2,s)==false||isNotSoldSeats(s2,s)==false){
                throw new JwtException("该订单不存在");
            }
        }

        OrderVo orderVo = new OrderVo();

        Date date = new Date();
        FiledVo filedVo = moocOrderTMapper.selectCinemaIdfromFieldByFieldId(fields);
        int cinemaId = filedVo.getCinemaId();
        int filmId = filedVo.getFilmId();
        MtimeCinemaT cinemaT =  moocOrderTMapper.selectCinemaNameById(cinemaId);
        FilmInfo filmInfo = moocOrderTMapper.selectFilmNameById(filmId);
        String filmName = filmInfo.getFilmName();
        String cinemaName = cinemaT.getCinemaName();
        orderVo.setFieldTime(date);
        orderVo.setCinemaName(cinemaName);
        orderVo.setFilmName(filmName);
        long time = date.getTime();
        orderVo.setOrderTimeStamp(time);
        double orderPrice = orderVo.getOrderPrice();

        jedisAddSeat();

        String seat = (String) redisTemplate.opsForValue().get("seat");
        String[] split1 = seat.split(",");

        StringBuilder sb = new StringBuilder();

        int num = 0;

        for (String s : split) {
            for (String s1 : split1) {
                if(s1.equals(s)){
                    sb.append(s1);
                    sb.append(" ");
                }
            }
        }

        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        orderVo.setSeatsName(sb.toString());
        orderVo.setOrderPrice(orderPrice * num);
        orderVo.setOrderId(uuid);
        String s = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        orderVo.setSeatsIds(s);

        moocOrderTMapper.insertOrder(orderVo);
        return orderVo;
    }

    private void jedisAddSeat() {
        redisTemplate.opsForValue().set("seat","1排1座");
        redisTemplate.opsForValue().set("seat","1排2座");
        redisTemplate.opsForValue().set("seat","1排3座");
        redisTemplate.opsForValue().set("seat","1排4座");
        redisTemplate.opsForValue().set("seat","1排5座");
        redisTemplate.opsForValue().set("seat","1排6座");
        redisTemplate.opsForValue().set("seat","2排1座");
        redisTemplate.opsForValue().set("seat","2排2座");
        redisTemplate.opsForValue().set("seat","2排3座");
        redisTemplate.opsForValue().set("seat","2排4座");
        redisTemplate.opsForValue().set("seat","2排5座");
        redisTemplate.opsForValue().set("seat","2排6座");
        redisTemplate.opsForValue().set("seat","3排1座");
        redisTemplate.opsForValue().set("seat","3排2座");
        redisTemplate.opsForValue().set("seat","3排3座");
        redisTemplate.opsForValue().set("seat","3排4座");
        redisTemplate.opsForValue().set("seat","3排5座");
        redisTemplate.opsForValue().set("seat","3排6座");
        redisTemplate.opsForValue().set("seat","4排1座");
        redisTemplate.opsForValue().set("seat","4排2座");
        redisTemplate.opsForValue().set("seat","4排3座");
        redisTemplate.opsForValue().set("seat","4排4座");
        redisTemplate.opsForValue().set("seat","4排5座");
        redisTemplate.opsForValue().set("seat","4排6座");
    }

    @Override
    public Page<OrderVo> getOrderByUserId(Integer userId, Page<OrderVo> page) {
        return null;
    }

    @Override
    public String getSoldSeatsByFieldId(Integer fieldId) {
        return null;
    }
}
