package cn.edu.zucc.distribution.model;

public class order_information {
    public static final String [] tableTitlesCustomer={"商品编号","商品名称","订单总重","购买数量","总价"};

    private int orderid;
    private int goodsid;
    private int ordersnumber;
    private float orderweight;
    private float ordersize;
    private float orderspecifications;
    private int userid;
    private float price;
    private String goodsName;
    private int houseid;

    public int getHouseid() {
        return houseid;
    }

    public void setHouseid(int houseid) {
        this.houseid = houseid;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

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

    public String getCell(int col){
        if (col==0) return Integer.toString(this.getOrderid());
        else if (col==1) return Integer.toString(this.getGoodsid());
        else if (col==2) return Integer.toString(this.getOrdersnumber());
        else if (col==3) return Float.toString(this.getOrderweight());
        else if (col==4) return Float.toString(this.getOrdersize());
        else if (col==5) return Float.toString(this.getOrderspecifications());
        else if (col==6) return Integer.toString(this.getUserid());
        else if (col==7) return Float.toString(this.getPrice());
        else if (col==8) return this.getGoodsName();
        else if (col==9) return Integer.toString(this.getHouseid());
        else return "";
    }
}
