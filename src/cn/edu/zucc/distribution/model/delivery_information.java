package cn.edu.zucc.distribution.model;

public class delivery_information {
    private int deliveryid;
    private int carid;
    private float deliveryweight;
    private float deliveryv;

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
}
