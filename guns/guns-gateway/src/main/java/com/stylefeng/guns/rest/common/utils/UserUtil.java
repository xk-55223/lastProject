package com.stylefeng.guns.rest.common.utils;

import com.stylefeng.guns.rest.config.properties.JwtProperties;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;

public class UserUtil {

    public static Integer getUserId(HttpServletRequest request, JwtProperties jwtProperties, Jedis jedis) {
        String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken = null;
        Integer userId = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);
            //验证token是否过期,包含了验证jwt是否正确
            String s = jedis.get(authToken);
            if (s == null) return null;
            userId = Integer.valueOf(s);
        }
        return userId;
    }
}
