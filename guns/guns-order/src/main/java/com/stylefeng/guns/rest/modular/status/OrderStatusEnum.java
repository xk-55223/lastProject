package com.stylefeng.guns.rest.modular.status;

public enum OrderStatusEnum {

    ORDER_STATUS_UNPAY(0,"未支付"),
    ORDER_STATUS_PAY(1,"已支付"),
    ORDER_STATUS_CLOSE(2,"已关闭");

    private Integer index;
    private String msg;

    OrderStatusEnum(Integer index, String msg) {
        this.index = index;
        this.msg = msg;
    }

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
