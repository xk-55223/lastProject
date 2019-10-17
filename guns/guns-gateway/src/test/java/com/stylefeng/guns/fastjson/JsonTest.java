package com.stylefeng.guns.fastjson;

import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.common.SimpleObject;
import com.stylefeng.guns.rest.modular.auth.converter.BaseTransferEntity;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;

/**
 * json测试
 *
 * @author fengshuonan
 * @date 2017-08-25 16:11
 */


public class JsonTest {

    public static void main(String[] args) {
        /*String randomKey = "1xm7hw";

        BaseTransferEntity baseTransferEntity = new BaseTransferEntity();
        SimpleObject simpleObject = new SimpleObject();
        simpleObject.setUser("fsn");
        baseTransferEntity.setObject("123123");

        String json = JSON.toJSONString(simpleObject);

        //md5签名
        String encrypt = MD5Util.encrypt(json + randomKey);
        baseTransferEntity.setSign(encrypt);

        System.out.println(JSON.toJSONString(baseTransferEntity));*/

        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.set("seat","{\"limit\":5,\"ids\":\"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24\",\"single\":[[{\"seatId\":1,\"row\":1,\"column\":1},{\"seatId\":2,\"row\":1,\"column\":2},{\"seatId\":3,\"row\":1,\"column\":3},{\"seatId\":4,\"row\":1,\"column\":4},{\"seatId\":5,\"row\":1,\"column\":5},{\"seatId\":6,\"row\":1,\"column\":6}],[{\"seatId\":7,\"row\":2,\"column\":1},{\"seatId\":8,\"row\":2,\"column\":2},{\"seatId\":9,\"row\":2,\"column\":3},{\"seatId\":10,\"row\":2,\"column\":4},{\"seatId\":11,\"row\":2,\"column\":5},{\"seatId\":12,\"row\":2,\"column\":6}]],\"couple\":[[{\"seatId\":13,\"row\":3,\"column\":1},{\"seatId\":14,\"row\":3,\"column\":2},{\"seatId\":15,\"row\":3,\"column\":3},{\"seatId\":16,\"row\":3,\"column\":4},{\"seatId\":17,\"row\":3,\"column\":5},{\"seatId\":18,\"row\":3,\"column\":6}],[{\"seatId\":19,\"row\":4,\"column\":1},{\"seatId\":20,\"row\":4,\"column\":2},{\"seatId\":21,\"row\":4,\"column\":3},{\"seatId\":22,\"row\":4,\"column\":4},{\"seatId\":23,\"row\":4,\"column\":5},{\"seatId\":24,\"row\":4,\"column\":6}]]}");

        String seat = jedis.get("seat");
        System.out.println(seat);
    }
}
