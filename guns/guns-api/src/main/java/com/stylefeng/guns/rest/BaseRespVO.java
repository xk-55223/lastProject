package com.stylefeng.guns.rest;

public class BaseRespVO<T> {
    private Integer status;
    private Integer nowPage;
    private Integer totalPage;
    private String msg;
    private String imgPre;
    T data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getNowPage() {
        return nowPage;
    }

    public void setNowPage(Integer nowPage) {
        this.nowPage = nowPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getImgPre() {
        return imgPre;
    }

    public void setImgPre(String imgPre) {
        this.imgPre = imgPre;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BaseRespVO ok(T data) {
        BaseRespVO<T> baseRespVO = new BaseRespVO<>();
        baseRespVO.setData(data);
        return baseRespVO;
    }
}
