package cn.edu.zucc.distribution.model;

public class car {
    private int carid;
    private String carnum;
    private float carweight;
    private float carv;
    private String carphone;
    private boolean carcold;

    public int getCarid() {
        return carid;
    }

    public void setCarid(int carid) {
        this.carid = carid;
    }

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public float getCarweight() {
        return carweight;
    }

    public void setCarweight(float carweight) {
        this.carweight = carweight;
    }

    public float getCarv() {
        return carv;
    }

    public void setCarv(float carv) {
        this.carv = carv;
    }

    public String getCarphone() {
        return carphone;
    }

    public void setCarphone(String carphone) {
        this.carphone = carphone;
    }

    public boolean isCarcold() {
        return carcold;
    }

    public void setCarcold(boolean carcold) {
        this.carcold = carcold;
    }
}
