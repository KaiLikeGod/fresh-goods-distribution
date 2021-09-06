package cn.edu.zucc.distribution.model;


import java.util.Date;

public class customer {
    public static customer currentLoginUser=null;
    private int userid;
    private String username;
    private String pwd;
    private String usertype;
    private String cusphone;
    private String cusemail;
    private String address;
    private Date custime;
    private float cuslgt;
    private float cuslat;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getCusphone() {
        return cusphone;
    }

    public void setCusphone(String cusphone) {
        this.cusphone = cusphone;
    }

    public String getCusemail() {
        return cusemail;
    }

    public void setCusemail(String cusemail) {
        this.cusemail = cusemail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCustime() {
        return custime;
    }

    public void setCustime(Date custime) {
        this.custime = custime;
    }

    public float getCuslgt() {
        return cuslgt;
    }

    public void setCuslgt(float cuslgt) {
        this.cuslgt = cuslgt;
    }

    public float getCuslat() {
        return cuslat;
    }

    public void setCuslat(float cuslat) {
        this.cuslat = cuslat;
    }
}
