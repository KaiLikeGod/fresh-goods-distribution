package cn.edu.zucc.distribution.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class orders {
    public static final String [] tableTitlesCustomer={"订单编号","订单重量","商品数量","订单体积","要求送达时间","订单状态"};
    public static final String [] tableTitlesAdmin={"订单编号","用户编号","订单重量","商品数量","订单体积","要求送达时间","订单状态"};
    public static final String [] tableTitlesdelivery={"订单编号","用户编号","订单重量","商品数量","订单体积","要求送达时间","是否需要冷藏","订单状态"};;


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

    public String getCell(int col){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        if (col==0) return Integer.toString(this.getOrderid());
        else if (col==1) return Integer.toString(this.getUserid());
        else if (col==2) return Float.toString(this.getOrderweight());
        else if (col==3) return Integer.toString(this.getOrdernumber());
        else if (col==4) return Float.toString(this.getOrderv());
        else if (col==5) return sf.format(this.getOrdertime());
        else if (col==6) return this.isOrderiscold() ? "冷藏" : "常温";
        else if (col==7) return this.getOrderstate();
        else return "";
    }
}
