package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeUserTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeUserT;
import com.stylefeng.guns.rest.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService{
    @Autowired
    private MtimeUserTMapper mtimeUserTMapper;

    @Override
    public String getUserNameById(Integer id) {
        MtimeUserT user = mtimeUserTMapper.selectById(id);
        return user.getUserName();
    }
}
