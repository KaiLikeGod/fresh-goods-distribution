package cn.edu.zucc.distribution.model;

public class commodity_type {
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
}
