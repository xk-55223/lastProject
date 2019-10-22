package com.stylefeng.guns.rest.promoljq.exception;

public enum PromoEnum {

    PARAM_ERROR(1,"参数错误"),
    CREATE_SUCCESS(0,"成功"),
    CREATE_FAIL(2,"创建订单失败"),
    STOCK_ERROR(3,"库存不足");

    PromoEnum(Integer index, String msg) {
        this.index = index;
        this.msg = msg;
    }

    private Integer index;

    private String msg;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
