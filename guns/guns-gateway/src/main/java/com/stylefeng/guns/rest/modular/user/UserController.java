package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.BaseRespVO;
import com.stylefeng.guns.rest.user.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    @Reference(interfaceClass = UserService.class, check = false)
    private UserService userService;

    @RequestMapping("query/username")
    public String getUsernameById(Integer id) {
        return userService.getUserNameById(id);
    }

    @RequestMapping("check")
    public BaseRespVO checkUsername(String username) {
        return null;
    }
}
