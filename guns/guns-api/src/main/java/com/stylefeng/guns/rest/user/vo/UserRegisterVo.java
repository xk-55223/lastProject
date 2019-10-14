package com.stylefeng.guns.rest.user.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterVo implements Serializable {
    private static final long serialVersionUID = 4841736972662513974L;
    private String username;
    private String password;
    private String mobile;
    private String email;
    private String address;
}
