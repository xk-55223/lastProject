package com.stylefeng.guns.rest.promoljq.vo;

public class PromoResultVO {
   private Integer status;
   private String msg;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static PromoResultVO success(String message) {
        PromoResultVO promoResultVO = new PromoResultVO();
        promoResultVO.setMsg(message);
        promoResultVO.setStatus(0);
        return promoResultVO;
    }

    public static PromoResultVO fail(String message){
        PromoResultVO promoResultVO = new PromoResultVO();
        promoResultVO.setStatus(1);
        promoResultVO.setMsg(message);
        return promoResultVO;
    }
}
