package com.stylefeng.guns.rest.modular.auth.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.BaseRespVO;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthRequest;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthResponse;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

/**
 * 请求验证的
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:22
 */
@RestController
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Reference(interfaceClass = UserService.class, check = false)
    private UserService userService;
    @Autowired
    private Jedis jedis;

    @RequestMapping(value = "${jwt.auth-path}")
    public BaseRespVO createAuthenticationToken(AuthRequest authRequest) {

        boolean validate = userService.login(authRequest.getUserName(), authRequest.getPassword());
        String userId = userService.getUserInfo(authRequest.getUserName()).getUuid() + "";
        if (validate) {
            final String randomKey = jwtTokenUtil.getRandomKey();
            final String token = jwtTokenUtil.generateToken(authRequest.getUserName(), randomKey);
            jedis.set(token, userId);
            System.out.println(jedis.get("myKey"));
            System.out.println(jedis.get(token));
            System.out.println(token);
            jedis.expire(token, 3600);
            return BaseRespVO.ok(new AuthResponse(token, randomKey));
        } else {
            return BaseRespVO.fail("用户名或密码错误");
        }
    }
}
