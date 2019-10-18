package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeUserTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeUserT;
import com.stylefeng.guns.rest.modular.user.util.MD5Digest;
import com.stylefeng.guns.rest.user.service.UserService;
import com.stylefeng.guns.rest.user.vo.UserInfoVo;
import com.stylefeng.guns.rest.user.vo.UserRegisterVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private MtimeUserTMapper mtimeUserTMapper;

    @Override
    public boolean register(UserRegisterVo registerVo) {
        registerVo.setPassword(MD5Digest.encode(registerVo.getPassword()));
        if (!checkUsername(registerVo.getUsername()) && mtimeUserTMapper.insert(userRegisterVo2MtimeUserT(registerVo)) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean login(String username, String password) {
        password = MD5Digest.encode(password);
        EntityWrapper<MtimeUserT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_name", username);
        List<MtimeUserT> mtimeUserTS = mtimeUserTMapper.selectList(entityWrapper);
        if (CollectionUtils.isNotEmpty(mtimeUserTS) && mtimeUserTS.get(0).getUserPwd().equals(password)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkUsername(String username) {
        EntityWrapper<MtimeUserT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_name", username);
        List<MtimeUserT> mtimeUserTS = mtimeUserTMapper.selectList(entityWrapper);
        if (CollectionUtils.isNotEmpty(mtimeUserTS)) {
            return true;
        }
        return false;
    }

    @Override
    public UserInfoVo getUserInfo(Integer userId) {
        EntityWrapper<MtimeUserT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("UUID", userId);
        return MtimeUserT2userInfoVo(mtimeUserTMapper.selectList(entityWrapper).get(0));
    }

    @Override
    public UserInfoVo getUserInfo(String username) {
        EntityWrapper<MtimeUserT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_name", username);
        return MtimeUserT2userInfoVo(mtimeUserTMapper.selectList(entityWrapper).get(0));
    }

    @Override
    public UserInfoVo updateUserInfo(UserInfoVo userInfoVoReq) {
        EntityWrapper<MtimeUserT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("UUID", userInfoVoReq.getUuid());
        mtimeUserTMapper.update(userInfoVo2MtimeUserT(userInfoVoReq), entityWrapper);
        return MtimeUserT2userInfoVo(mtimeUserTMapper.selectById(userInfoVoReq.getUuid()));
    }

    private MtimeUserT userRegisterVo2MtimeUserT(UserRegisterVo registerVo) {
        MtimeUserT mtimeUserT = new MtimeUserT();
        mtimeUserT.setUserName(registerVo.getUsername());
        mtimeUserT.setUserPwd(registerVo.getPassword());
        mtimeUserT.setEmail(registerVo.getEmail());
        mtimeUserT.setUserPhone(registerVo.getMobile());
        mtimeUserT.setAddress(registerVo.getAddress());
        mtimeUserT.setBeginTime(new Date());
        mtimeUserT.setUpdateTime(new Date());
        return mtimeUserT;
    }

    private UserInfoVo MtimeUserT2userInfoVo(MtimeUserT mtimeUserT) {
        UserInfoVo userInfoVo = new UserInfoVo();
        if (mtimeUserT == null) {
            return null;
        }
        userInfoVo.setAddress(mtimeUserT.getAddress());
        userInfoVo.setBiography(mtimeUserT.getBiography());
        userInfoVo.setBirthday(mtimeUserT.getBirthday());
        userInfoVo.setEmail(mtimeUserT.getEmail());
        userInfoVo.setHeadAddress(mtimeUserT.getHeadUrl());
        userInfoVo.setLifeState(mtimeUserT.getLifeState());
        userInfoVo.setNickname(mtimeUserT.getNickName());
        userInfoVo.setPhone(mtimeUserT.getUserPhone());
        userInfoVo.setSex(mtimeUserT.getUserSex());
        userInfoVo.setUsername(mtimeUserT.getUserName());
        userInfoVo.setUuid(mtimeUserT.getUuid());
        userInfoVo.setUpdateTime(mtimeUserT.getUpdateTime());
        userInfoVo.setBeginTime(mtimeUserT.getBeginTime());
        return userInfoVo;
    }


    private MtimeUserT userInfoVo2MtimeUserT(UserInfoVo userInfoVoReq) {
        MtimeUserT mtimeUserT = new MtimeUserT();
        mtimeUserT.setUuid(userInfoVoReq.getUuid());
        mtimeUserT.setUserName(userInfoVoReq.getUsername());
        mtimeUserT.setNickName(userInfoVoReq.getNickname());
        mtimeUserT.setEmail(userInfoVoReq.getEmail());
        mtimeUserT.setUserPhone(userInfoVoReq.getPhone());
        mtimeUserT.setUserSex(userInfoVoReq.getSex());
        mtimeUserT.setBirthday(userInfoVoReq.getBirthday());
        mtimeUserT.setLifeState(userInfoVoReq.getLifeState());
        mtimeUserT.setBiography(userInfoVoReq.getBiography());
        mtimeUserT.setAddress(userInfoVoReq.getAddress());
        mtimeUserT.setHeadUrl(userInfoVoReq.getHeadAddress());
        mtimeUserT.setUpdateTime(new Date());
        return mtimeUserT;
    }

}
