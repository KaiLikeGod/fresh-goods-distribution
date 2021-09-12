package cn.edu.zucc.distribution.model;

import java.text.SimpleDateFormat;

public class delivery_information {
    public static final String [] tableTitles={"配送单编号", "车辆编号", "配送总重", "配送总体积","订单状态"};

    private int deliveryid;
    private int carid;
    private float deliveryweight;
    private float deliveryv;
    private String deliveryStatic;

    public String getDeliveryStatic() {
        return deliveryStatic;
    }

    public void setDeliveryStatic(String deliveryStatic) {
        this.deliveryStatic = deliveryStatic;
    }

    public int getDeliveryid() {
        return deliveryid;
    }

    public void setDeliveryid(int deliveryid) {
        this.deliveryid = deliveryid;
    }

    public int getCarid() {
        return carid;
    }

    public void setCarid(int carid) {
        this.carid = carid;
    }

    public float getDeliveryweight() {
        return deliveryweight;
    }

    public void setDeliveryweight(float deliveryweight) {
        this.deliveryweight = deliveryweight;
    }

    public float getDeliveryv() {
        return deliveryv;
    }

    public void setDeliveryv(float deliveryv) {
        this.deliveryv = deliveryv;
    }

    public String getCell(int col){
        if (col==0) return Integer.toString(this.getDeliveryid());
        else if (col==1) return Integer.toString(this.getCarid());
        else if (col==2) return Float.toString(this.getDeliveryweight());
        else if (col==3) return Float.toString(this.getDeliveryv());
        else if (col==4) return this.getDeliveryStatic();
        else return "";
    }
}
