package cn.edu.zucc.distribution.model;

import java.text.SimpleDateFormat;

public class warehouses {
    public static final String [] tableTitles={"仓库编号", "仓库名称", "仓库地址", "联系方式", "经度", "纬度"};
    private int houseid;
    private String housename;
    private String houseaddress;
    private String housephone;
    private float houseLgt;
    private float houselat;

    public int getHouseid() {
        return houseid;
    }

    public void setHouseid(int houseid) {
        this.houseid = houseid;
    }

    public String getHousename() {
        return housename;
    }

    public void setHousename(String housename) {
        this.housename = housename;
    }

    public String getHouseaddress() {
        return houseaddress;
    }

    public void setHouseaddress(String houseaddress) {
        this.houseaddress = houseaddress;
    }

    public String getHousephone() {
        return housephone;
    }

    public void setHousephone(String housephone) {
        this.housephone = housephone;
    }

    public float getHouseLgt() {
        return houseLgt;
    }

    public void setHouseLgt(float houseLgt) {
        this.houseLgt = houseLgt;
    }

    public float getHouselat() {
        return houselat;
    }

    public void setHouselat(float houselat) {
        this.houselat = houselat;
    }

    public String getCell(int col){
        if (col==0) return Integer.toString(this.getHouseid());
        else if (col==1) return this.getHousename();
        else if (col==2) return this.getHouseaddress();
        else if (col==3) return this.getHousephone();
        else if (col==4) return Float.toString(this.getHouseLgt());
        else if (col==5) return Float.toString(this.getHouselat());
        else return "";
    }
}
