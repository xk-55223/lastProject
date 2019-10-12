package com.stylefeng.guns.rest.common.persistence.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.user.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jia.xue
 * @create: 2019-10-12 15:21
 * @Description
 **/
@RestController
@RequestMapping("user")
public class UserController {


    @Reference(interfaceClass = UserService.class)
    UserService userService;
    @RequestMapping("getUserNameById")
    public String getUsernameById(Integer id){
        String username = userService.getNameById(id);

        return username;

    }
}
