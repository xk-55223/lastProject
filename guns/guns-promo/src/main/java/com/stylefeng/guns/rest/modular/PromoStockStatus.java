package com.stylefeng.guns.rest.modular;

public enum PromoStockStatus {
    STOCK_LOG_INIT(1,"初始化"),
    STOCK_LOG_SUCCESS(2,"成功"),
    STOCK_LOG_FAIL(3,"失败");

    private int index;
    private String msg;

    PromoStockStatus(int index, String msg) {
        this.index = index;
        this.msg = msg;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
