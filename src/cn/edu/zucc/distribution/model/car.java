package cn.edu.zucc.distribution.model;

import java.text.SimpleDateFormat;

public class car {
    public static final String [] tableTitles={"车辆编号", "车牌号", "车辆载重", "车辆容积", "联系电话", "是否为冷藏车","车辆状态"};
    private int carid;
    private String carnum;
    private float carweight;
    private float carv;
    private String carphone;
    private boolean carcold;

    public String getCarstate() {
        return carstate;
    }

    public void setCarstate(String carstate) {
        this.carstate = carstate;
    }

    private String carstate;

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

    public String getCell(int col){
        if (col==0) return Integer.toString(this.getCarid());
        else if (col==1) return this.getCarnum();
        else if (col==2) return Float.toString(this.getCarweight());
        else if (col==3) return Float.toString(this.getCarv());
        else if (col==4) return this.getCarphone();
        else if (col==5) return this.isCarcold()? "冷藏车":"非冷藏车";
        else if (col==6) return this.getCarstate();
        else return "";
    }
}
