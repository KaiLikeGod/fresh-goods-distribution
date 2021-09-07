package cn.edu.zucc.distribution.itf;

import cn.edu.zucc.distribution.model.*;
import cn.edu.zucc.distribution.util.BaseException;

public interface IUserManager {
	public void regadmin(String username, String pwd,String pwd2) throws BaseException;

	public void regcustom(String username, String pwd,String pwd2,String phone,String email,String address,float lgt,float lat) throws BaseException;

	public admin loginadmin(String username,String pwd)throws BaseException;

	public customer logincustomer(String username,String pwd)throws BaseException;

	public void changePwdCustomer(customer user, String oldPwd,String newPwd, String newPwd2)throws BaseException;

	public void changePwdAdmin(admin user, String oldPwd,String newPwd, String newPwd2)throws BaseException;

	public void changeAddressCustomer(customer user,String newAddress) throws BaseException;

	public void changeLgtCustomer(customer user,float newLgt) throws BaseException;

	public void changeLatCustomer(customer user,float newLat) throws BaseException;
}
