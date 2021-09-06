package cn.edu.zucc.distribution.model;

import java.util.Date;

public class BeanUser {
	public static BeanUser currentLoginUser=null;
	private String user_id;
	private Date register_time;
	private String pwd;

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public void setRegister_time(Date register_time) {
		this.register_time = register_time;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getUser_id() {
		return user_id;
	}

	public String getPwd() {
		return pwd;
	}

	public Date getRegister_time() {
		return register_time;
	}
}
