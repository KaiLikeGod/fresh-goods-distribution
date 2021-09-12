package cn.edu.zucc.distribution.model;

import java.text.SimpleDateFormat;

public class commodity_type {
    public static final String [] tableTitles={"类别编号", "类别名称", "类别描述"};

    private int typeid;
    private String typename;
    private String typedescribe;

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getTypedescribe() {
        return typedescribe;
    }

    public void setTypedescribe(String typedescribe) {
        this.typedescribe = typedescribe;
    }

    public String getCell(int col){
        if (col==0) return Integer.toString(this.getTypeid());
        else if (col==1) return this.getTypename();
        else if (col==2) return this.getTypedescribe();
        else return "";
    }
}
