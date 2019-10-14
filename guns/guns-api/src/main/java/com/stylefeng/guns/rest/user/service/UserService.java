package com.stylefeng.guns.rest.user.service;

import com.stylefeng.guns.rest.user.vo.UserInfoVo;
import com.stylefeng.guns.rest.user.vo.UserRegisterVo;

/**
 * @author: jia.xue
 * @create: 2019-10-12 15:18
 * @Description
 **/
public interface UserService {
    boolean checkUsername(String username);

    boolean register(UserRegisterVo registerVo);

    UserInfoVo updateUserInfo(UserInfoVo userInfoVoReq);

    UserInfoVo getUserInfo(String username);
}
