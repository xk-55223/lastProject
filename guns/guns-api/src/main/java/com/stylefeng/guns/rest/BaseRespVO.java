package com.stylefeng.guns.rest;


public class BaseRespVO<T> {
    private Integer status;
    private Integer nowPage;
    private Integer totalPage;
    private String msg;
    private String imgPre;
    Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static BaseRespVO ok(Object data) {

        BaseRespVO<Object> baseRespVO = new BaseRespVO<>();
        baseRespVO.setStatus(0);
        baseRespVO.setData(data);
        return baseRespVO;
    }

    public static BaseRespVO ok(String msg) {
        BaseRespVO<Object> baseRespVO = new BaseRespVO<>();
        baseRespVO.setMsg(msg);
        return baseRespVO;
    }

    public static BaseRespVO fail(String msg) {
        BaseRespVO<Object> baseRespVO = new BaseRespVO<>();
        baseRespVO.setStatus(1);
        baseRespVO.setMsg(msg);
        return baseRespVO;
    }
}
