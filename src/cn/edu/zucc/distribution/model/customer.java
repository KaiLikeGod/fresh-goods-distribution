package cn.edu.zucc.distribution.model;


import java.text.SimpleDateFormat;
import java.util.Date;

public class customer {
    public static final String [] tableTitles={"用户编号", "用户姓名", "密码", "用户类型", "联系方式", "电子邮箱", "居住地址", "注册时间", "经度", "纬度"};
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

    public String getCell(int col){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        if (col==0) return Integer.toString(this.getUserid());
        else if (col==1) return this.getUsername();
        else if (col==2) return this.getPwd();
        else if (col==3) return this.getUsertype();
        else if (col==4) return this.getCusphone();
        else if (col==5) return this.getCusemail();
        else if (col==6) return this.getAddress();
        else if (col==7) return sf.format(this.getCustime());
        else if (col==8) return Float.toString(this.getCuslgt());
        else if (col==9) return Float.toString(this.getCuslat());
        else return "";
    }
}
