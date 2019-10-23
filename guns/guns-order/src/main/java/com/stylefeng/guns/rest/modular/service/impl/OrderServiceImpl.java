package com.stylefeng.guns.rest.modular.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.gson.Gson;
import com.stylefeng.guns.rest.PageInfoVO;
import com.stylefeng.guns.rest.cinema.bean.MtimeCinemaT;
import com.stylefeng.guns.rest.common.persistence.dao.MoocOrderTMapper;
import com.stylefeng.guns.rest.film.vo.FilmInfo;
import com.stylefeng.guns.rest.film.vo.FilmVo;
import com.stylefeng.guns.rest.modular.status.OrderStatusEnum;
import com.stylefeng.guns.rest.order.OrderService;
import com.stylefeng.guns.rest.order.bean.FieldSeatsInfoVO;
import com.stylefeng.guns.rest.order.bean.OrderInfoVO;
import com.stylefeng.guns.rest.order.bean.SeatInfoVO;
import com.stylefeng.guns.rest.order.vo.FiledVo;
import com.stylefeng.guns.rest.order.vo.OrderVo;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {
    @Autowired
    MoocOrderTMapper orderTMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    public boolean isTrueSeats(String fieldId, String seats) {
        FiledVo filedVo = orderTMapper.selectFieldById(fieldId);
        FieldSeatsInfoVO fieldSeats = (FieldSeatsInfoVO) redisTemplate.opsForValue().get("hall" + filedVo.getHallId());
            String ids = fieldSeats.getIds();
            if (! ids.contains(seats) || seats.equals("0")) return false;
        return true;
    }


    public boolean isSoldSeats(String fieldId, String seats) {
        List<OrderVo> orderVo = orderTMapper.selectOrderByFiledId(fieldId);
        if (orderVo == null) return false;
        for (OrderVo vo : orderVo) {
            String seatsIds = vo.getSeatsIds();
            String[] split = seatsIds.split(",");
            for (String seatId : split) {
                    if(seatId.equals(seats)){
                        return true;
                    }
            }
        }
        return false;
    }

    @Override
    public OrderVo saveOrderInfo(Integer fieldId, String soldSeats, String seatsTypeName, Integer userId) {
        String[] seatsId = soldSeats.split(",");
        String fieldIdOfString = String.valueOf(fieldId);
        for (String seatId : seatsId)
        {
            if(!(isTrueSeats(fieldIdOfString,seatId))&&(!isSoldSeats(fieldIdOfString,seatId))){
                throw new JwtException("订单创建失败");
            }
        }

        OrderVo orderVo = new OrderVo();

        Date date = new Date();
        FiledVo filedVo = orderTMapper.selectCinemaIdfromFieldByFieldId(fieldId);
        int cinemaId = filedVo.getCinemaId();
        int filmId = filedVo.getFilmId();
        MtimeCinemaT cinemaT =  orderTMapper.selectCinemaNameById(cinemaId);
        FilmInfo filmInfo = orderTMapper.selectFilmNameById(filmId);
        String filmName = filmInfo.getFilmName();
        String cinemaName = cinemaT.getCinemaName();
        String format = new SimpleDateFormat("今天 MM月dd号 hh:mm").format(date);
        orderVo.setFieldTime(format);
        orderVo.setOrderUser(userId);
        orderVo.setOrderStatus(0);
        orderVo.setCinemaName(cinemaName);
        orderVo.setFilmName(filmName);
        orderVo.setFilmId(filmId);
        orderVo.setOrderTime(date);
        long time = date.getTime();
        orderVo.setOrderTimeStamp(time);
        double filmPrice = filedVo.getPrice();
        orderVo.setFilmPrice(filmPrice);
        FieldSeatsInfoVO seat = (FieldSeatsInfoVO) redisTemplate.opsForValue().get("hall" + filedVo.getHallId());
        int num = seatsId.length;
        String seatsName = getSeatsName(seat,seatsId,seatsTypeName);
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        orderVo.setCinemaId(cinemaId);
        orderVo.setFieldId(fieldId);
        orderVo.setSeatsName(seatsName);
        orderVo.setOrderPrice(filmPrice * num);
        orderVo.setOrderId(uuid);
        orderVo.setSeatsIds(soldSeats);

        orderTMapper.insertOrder(orderVo);
        return orderVo;
    }

    private String getSeatsName(FieldSeatsInfoVO seat, String[] seatsId, String  seatsTypeName) {
        StringBuilder seatsName = new StringBuilder();
        int[] seatIds = stringArrays2intArraySort(seatsId);
        if ("单排座".equals(seatsTypeName)) {
            List<List<SeatInfoVO>> single = seat.getSingle();
            getSeatName(single, seatIds, seatsName);
        } else if ("双排座".equals(seatsTypeName)) {
            List<List<SeatInfoVO>> couple = seat.getCouple();
            getSeatName(couple, seatIds, seatsName);
        } else {
            List<List<SeatInfoVO>> allseats = seat.getSingle();
            allseats.addAll(seat.getCouple());
            getSeatName(allseats, seatIds, seatsName);
        }

        return seatsName.toString().trim();
    }

    private int[] stringArrays2intArraySort(String[] seatsId) {
        int[] ints = new int[seatsId.length];
        for (int i = 0; i < seatsId.length; i++) {
            ints[i] = Integer.valueOf(seatsId[i]);
        }
        Arrays.sort(ints);
        return ints;
    }

    private void getSeatName(List<List<SeatInfoVO>> seat, int[] seatsId, StringBuilder seatsName) {
        int i = 0;
        for (List<SeatInfoVO> seatInfos : seat) {
            if (i >= seatsId.length) break;
            for (SeatInfoVO seatInfo : seatInfos) {
                if (i >= seatsId.length) break;
                if (seatInfo.getSeatId() == seatsId[i]) {
                    seatsName.append(seatInfo.getRow() + "排" + seatInfo.getColumn() + "座 ");
                    i++;
                }
            }
        }
    }

    @Override
    public List<OrderInfoVO> getOrderInfo(String userId, PageInfoVO pageInfoVO) {
        List<OrderInfoVO> orderInfos = orderTMapper.selectOrdersByUsername(userId,pageInfoVO);
        for (OrderInfoVO orderInfo : orderInfos) {
            // 将日期转化为时间戳
            String orderTime = orderInfo.getOrderTime();
            try {
                Date parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(orderTime);
                long time = parse.getTime() / 1000;
                orderInfo.setOrderTimestamp(time + "");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // 将数字订单状态，转化为真实订单状态
            String orderStatus = orderInfo.getOrderStatus();
            if (OrderStatusEnum.ORDER_STATUS_UNPAY.getIndex() == Integer.valueOf(orderStatus)) {
                orderInfo.setOrderStatus(OrderStatusEnum.ORDER_STATUS_UNPAY.getMsg());
            } else if (OrderStatusEnum.ORDER_STATUS_PAY.getIndex() == Integer.valueOf(orderStatus)) {
                orderInfo.setOrderStatus(OrderStatusEnum.ORDER_STATUS_PAY.getMsg());
            } else if (OrderStatusEnum.ORDER_STATUS_CLOSE.getIndex() == Integer.valueOf(orderStatus)) {
                orderInfo.setOrderStatus(OrderStatusEnum.ORDER_STATUS_CLOSE.getMsg());
            }
        }
        return orderInfos;
    }


}
