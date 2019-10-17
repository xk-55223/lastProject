package com.stylefeng.guns.rest.pay.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PayInfoVo implements Serializable {
    private static final long serialVersionUID = -5466746862456025803L;
    private String orderId;
    private String QRCodeAddress;
}
