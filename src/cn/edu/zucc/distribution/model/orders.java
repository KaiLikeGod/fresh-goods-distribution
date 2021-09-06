package cn.edu.zucc.distribution.model;

import java.util.Date;

public class orders {
    private int orderid;
    private int userid;
    private float orderweight;
    private int ordernumber;
    private float orderv;
    private Date ordertime;
    private boolean orderiscold;
    private String orderstate;

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public float getOrderweight() {
        return orderweight;
    }

    public void setOrderweight(float orderweight) {
        this.orderweight = orderweight;
    }

    public int getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(int ordernumber) {
        this.ordernumber = ordernumber;
    }

    public float getOrderv() {
        return orderv;
    }

    public void setOrderv(float orderv) {
        this.orderv = orderv;
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public boolean isOrderiscold() {
        return orderiscold;
    }

    public void setOrderiscold(boolean orderiscold) {
        this.orderiscold = orderiscold;
    }

    public String getOrderstate() {
        return orderstate;
    }

    public void setOrderstate(String orderstate) {
        this.orderstate = orderstate;
    }
}
