package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.BaseRespVO;
import com.stylefeng.guns.rest.user.service.UserService;
import com.stylefeng.guns.rest.user.vo.UserInfoVo;
import com.stylefeng.guns.rest.user.vo.UserRegisterVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    @Reference(interfaceClass = UserService.class, check = false)
    private UserService userService;

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public BaseRespVO registerUser(UserRegisterVo registerVo) {
        if (!userService.register(registerVo)) {
            return BaseRespVO.fail("用户已存在");
        }
        return BaseRespVO.ok("注册成功");
    }

    @RequestMapping(value = "check", method = RequestMethod.POST)
    public BaseRespVO checkUsername(String username) {
        if (userService.checkUsername(username)) {
            BaseRespVO.fail("用户已存在");
        }
        return BaseRespVO.ok("用户名不存在");
    }

    @RequestMapping(value = "getUserInfo", method = RequestMethod.GET)
    public BaseRespVO getUserInfo(String username) {
        /*username要修改*/
        UserInfoVo userInfoVoResp = userService.getUserInfo(username);
        if (userInfoVoResp == null) {
            return BaseRespVO.fail("查询失败，用户尚未登陆");
        }
        return BaseRespVO.ok(userInfoVoResp);
    }

    @RequestMapping(value = "updateUserInfo", method = RequestMethod.POST)
    public BaseRespVO updateUserInfo(UserInfoVo userInfoReq) {
        UserInfoVo userInfoVoResp = userService.updateUserInfo(userInfoReq);
        if (userInfoVoResp == null) {
            return BaseRespVO.fail("用户信息修改失败");
        }
        return BaseRespVO.ok(userInfoVoResp);
    }
}
