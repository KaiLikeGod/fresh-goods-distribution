package cn.edu.zucc.distribution.model;

public class order_information {
    private int orderid;
    private int goodsid;
    private int ordersnumber;
    private float orderweight;
    private float ordersize;
    private float orderspecifications;

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(int goodsid) {
        this.goodsid = goodsid;
    }

    public int getOrdersnumber() {
        return ordersnumber;
    }

    public void setOrdersnumber(int ordersnumber) {
        this.ordersnumber = ordersnumber;
    }

    public float getOrderweight() {
        return orderweight;
    }

    public void setOrderweight(float orderweight) {
        this.orderweight = orderweight;
    }

    public float getOrdersize() {
        return ordersize;
    }

    public void setOrdersize(float ordersize) {
        this.ordersize = ordersize;
    }

    public float getOrderspecifications() {
        return orderspecifications;
    }

    public void setOrderspecifications(float orderspecifications) {
        this.orderspecifications = orderspecifications;
    }
}
