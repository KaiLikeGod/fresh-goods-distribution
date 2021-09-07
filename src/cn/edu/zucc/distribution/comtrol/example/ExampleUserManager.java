package cn.edu.zucc.distribution.comtrol.example;

import cn.edu.zucc.distribution.itf.IUserManager;
import cn.edu.zucc.distribution.model.*;
import cn.edu.zucc.distribution.util.BaseException;
import cn.edu.zucc.distribution.util.BusinessException;
import cn.edu.zucc.distribution.util.DBUtil;
import cn.edu.zucc.distribution.util.DbException;
import javax.swing.JOptionPane;

import java.util.Date;
import java.sql.*;

public class ExampleUserManager implements IUserManager {

	@Override
	public void regadmin(String username, String pwd,String pwd2) throws BaseException {
		if (username==null || "".equals(username)) throw new BusinessException("姓名不能为空");
		if (pwd==null || "".equals(pwd)) throw new BusinessException("密码不能为空");
		if (!pwd.equals(pwd2)) throw new BusinessException("两次输入的密码不一致");
		Connection conn=null;
		try {
			conn= DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql="select user_name\n" +
					"from user\n" +
					"where user_name=?";
			PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,username);
			ResultSet rs=pst.executeQuery();
			if (rs.next()){
				rs.close();
				pst.close();
				throw new BusinessException("当前姓名已经被注册！");
			}
			rs.close();
			pst.close();

			sql="select max(user_id)\n" +
					"from user";
			int order=1;
			pst= conn.prepareStatement(sql);
			rs= pst.executeQuery();
			if (rs.next()){
				order=rs.getInt(1)+1;
			}
			rs.close();
			pst.close();

			sql="INSERT INTO user(user_id,user_name,password,user_type)\n" +
					"VALUES (?,?,?,'管理员')\n";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,order);
			pst.setString(2,username);
			pst.setString(3,pwd);
			pst.execute();
			pst.close();

			sql="INSERT INTO admin(user_id,user_name,password,user_type)\n" +
					"VALUES (?,?,?,'管理员')\n";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,order);
			pst.setString(2,username);
			pst.setString(3,pwd);
			pst.execute();
			pst.close();

			conn.commit();
			JOptionPane.showMessageDialog(null, "用户注册成功! ", "恭喜" , JOptionPane.PLAIN_MESSAGE);
		}catch (SQLException ex){
			throw new DbException(ex);
		}finally {
			if (conn!=null){
				try {
					conn.rollback();
					conn.close();
				}catch (SQLException e){
					e.printStackTrace();
				}
			}
		}
	}


	public void regcustom(String username, String pwd,String pwd2,String phone,String email,String address,float lgt,float lat) throws BaseException {
		if (username==null || "".equals(username)) throw new BusinessException("姓名不能为空");
		if (pwd==null || "".equals(pwd)) throw new BusinessException("密码不能为空");
		if (!pwd.equals(pwd2)) throw new BusinessException("两次输入的密码不一致");
		if (phone==null || "".equals(phone)) throw new BusinessException("联系方式不能为空");
		if (address==null || "".equals(address)) throw new BusinessException("居住地址不能为空");
		if (lgt==-1) throw new BusinessException("坐标经度不能为空");
		if (lat==-1) throw new BusinessException("坐标纬度不能为空");
		Connection conn=null;
		try {
			conn= DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql="select user_name\n" +
					"from user\n" +
					"where user_name=?";
			PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,username);
			ResultSet rs=pst.executeQuery();
			if (rs.next()){
				rs.close();
				pst.close();
				throw new BusinessException("当前姓名已经被注册！");
			}
			rs.close();
			pst.close();

			sql="select max(user_id)\n" +
					"from user";
			int order=1;
			pst= conn.prepareStatement(sql);
			rs= pst.executeQuery();
			if (rs.next()){
				order=rs.getInt(1)+1;
			}
			rs.close();
			pst.close();

			sql="INSERT INTO user(user_id,user_name,password,user_type)\n" +
					"VALUES (?,?,?,'客户')\n";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,order);
			pst.setString(2,username);
			pst.setString(3,pwd);
			pst.execute();
			pst.close();

			sql="INSERT INTO customer(user_id,user_name,password,user_type,cus_phone,cus_email,cus_address,cus_time,cus_longitude,cus_latitude)\n" +
					"VALUES (?,?,?,'客户',?,?,?,now(),?,?)\n";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,order);
			pst.setString(2,username);
			pst.setString(3,pwd);
			pst.setString(4,phone);
			pst.setString(5,email);
			pst.setString(6,address);
			pst.setFloat(7,lgt);
			pst.setFloat(8,lat);
			pst.execute();
			pst.close();

			conn.commit();
		}catch (SQLException ex){
			throw new DbException(ex);
		}finally {
			if (conn!=null){
				try {
					conn.rollback();
					conn.close();
				}catch (SQLException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public admin loginadmin(String username, String pwd) throws BaseException {
		if (username==null || "".equals(username)) throw new BusinessException("姓名不能为空");
		if (pwd==null || "".equals(pwd)) throw new BusinessException("密码不能为空");
		Connection conn=null;
		admin user=null;
		try {
			conn= DBUtil.getConnection();
			String sql="select user_id from admin where user_name=? and password=?";
			PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,username);
			pst.setString(2,pwd);
			ResultSet rs=pst.executeQuery();
			if (rs.next()){
				user=new admin();
				user.setUserid(rs.getInt(1));
				user.setUsername(username);
				user.setPwd(pwd);
				user.setUsertype("管理员");
				return user;
			}else {
				throw new BusinessException("姓名或密码错误！");
			}
		}catch (SQLException ex){
			throw new DbException(ex);
		}finally {
			if (conn!=null){
				try {
					conn.close();
				}catch (SQLException e){
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public customer logincustomer(String username, String pwd) throws BaseException {
		if (username==null || "".equals(username)) throw new BusinessException("姓名不能为空");
		if (pwd==null || "".equals(pwd)) throw new BusinessException("密码不能为空");
		Connection conn=null;
		customer user=null;
		try {
			conn= DBUtil.getConnection();
			String sql="select user_id,cus_phone,cus_email,cus_address,cus_time,cus_longitude,cus_latitude\n" +
					"from customer where user_name=? and password=?";
			PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,username);
			pst.setString(2,pwd);
			ResultSet rs=pst.executeQuery();
			if (rs.next()){
				user=new customer();
				user.setUserid(rs.getInt(1));
				user.setUsername(username);
				user.setPwd(pwd);
				user.setUsertype("客户");
				user.setCusphone(rs.getString(2));
				user.setCusemail(rs.getString(3));
				user.setAddress(rs.getString(4));
				user.setCustime(rs.getDate(5));
				user.setCuslgt(rs.getFloat(6));
				user.setCuslat(rs.getFloat(7));
				return user;
			}else {
				throw new BusinessException("姓名或密码错误！");
			}
		}catch (SQLException ex){
			throw new DbException(ex);
		}finally {
			if (conn!=null){
				try {
					conn.close();
				}catch (SQLException e){
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void changePwdCustomer(customer user, String oldPwd, String newPwd,
			String newPwd2) throws BaseException {
		if (oldPwd==null || "".equals(oldPwd)) throw new BusinessException("旧密码不能为空！");
		Connection conn=null;
		try {
			conn= DBUtil.getConnection();
			String sql="SELECT password\n" +
					"FROM user \n" +
					"WHERE user_name=?";
			PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,user.getUsername());
			ResultSet rs=pst.executeQuery();
			if (rs.next()){
				if (!oldPwd.equals(rs.getString(1))) {
					rs.close();
					pst.close();
					throw new BusinessException("旧密码输入错误！");
				}
			}
			rs.close();
			pst.close();
			if (newPwd==null || "".equals(newPwd)) throw new BusinessException("新密码不能为空");
			if (!newPwd.equals(newPwd2)) throw new BusinessException("两次输入得密码不一致！");
			sql="UPDATE user SET password=? WHERE user_name=?\n";
			pst=conn.prepareStatement(sql);
			pst.setString(1,newPwd);
			pst.setString(2,user.getUsername());
			pst.execute();
			pst.close();

			sql="UPDATE customer SET password=? WHERE user_name=?\n";
			pst=conn.prepareStatement(sql);
			pst.setString(1,newPwd);
			pst.setString(2,user.getUsername());
			pst.execute();
			pst.close();

			JOptionPane.showMessageDialog(null, "密码修改成功! ", "提示" , JOptionPane.PLAIN_MESSAGE);
		}catch (SQLException ex){
			throw new DbException(ex);
		}finally {
			if (conn!=null){
				try {
					conn.close();
				}catch (SQLException e){
					e.printStackTrace();
				}
			}
		}
	}

	public void changePwdAdmin(admin user, String oldPwd, String newPwd,
								  String newPwd2) throws BaseException {
		if (oldPwd==null || "".equals(oldPwd)) throw new BusinessException("旧密码不能为空！");
		Connection conn=null;
		try {
			conn= DBUtil.getConnection();
			String sql="SELECT password\n" +
					"FROM user \n" +
					"WHERE user_name=?";
			PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,user.getUsername());
			ResultSet rs=pst.executeQuery();
			if (rs.next()){
				if (!oldPwd.equals(rs.getString(1))) {
					rs.close();
					pst.close();
					throw new BusinessException("旧密码输入错误！");
				}
			}
			rs.close();
			pst.close();
			if (newPwd==null || "".equals(newPwd)) throw new BusinessException("新密码不能为空");
			if (!newPwd.equals(newPwd2)) throw new BusinessException("两次输入得密码不一致！");
			sql="UPDATE user SET password=? WHERE user_name=?\n";
			pst=conn.prepareStatement(sql);
			pst.setString(1,newPwd);
			pst.setString(2,user.getUsername());
			pst.execute();
			pst.close();

			sql="UPDATE admin SET password=? WHERE user_name=?\n";
			pst=conn.prepareStatement(sql);
			pst.setString(1,newPwd);
			pst.setString(2,user.getUsername());
			pst.execute();
			pst.close();

			JOptionPane.showMessageDialog(null, "密码修改成功! ", "提示" , JOptionPane.PLAIN_MESSAGE);
		}catch (SQLException ex){
			throw new DbException(ex);
		}finally {
			if (conn!=null){
				try {
					conn.close();
				}catch (SQLException e){
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void changeAddressCustomer(customer user,String newAddress) throws BaseException{
		if (newAddress==null || "".equals(newAddress)) throw new BusinessException("新地址不能为空！");
		Connection conn=null;
		try {
			conn= DBUtil.getConnection();
			String sql="UPDATE customer SET cus_address=? WHERE user_id=?\n";
			PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,newAddress);
			pst.setInt(2,user.getUserid());
			pst.execute();
			pst.close();

			JOptionPane.showMessageDialog(null, "地址修改成功! ", "提示" , JOptionPane.PLAIN_MESSAGE);
		}catch (SQLException ex){
			throw new DbException(ex);
		}finally {
			if (conn!=null){
				try {
					conn.close();
				}catch (SQLException e){
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void changeLgtCustomer(customer user,float newLgt) throws BaseException{
		if (newLgt==-1) throw new BusinessException("坐标经度不能为空");
		Connection conn=null;
		try {
			conn= DBUtil.getConnection();
			String sql="UPDATE customer SET cus_longitude=? WHERE user_id=?\n";
			PreparedStatement pst=conn.prepareStatement(sql);
			pst.setFloat(1,newLgt);
			pst.setInt(2,user.getUserid());
			pst.execute();
			pst.close();

			JOptionPane.showMessageDialog(null, "经度修改成功! ", "提示" , JOptionPane.PLAIN_MESSAGE);
		}catch (SQLException ex){
			throw new DbException(ex);
		}finally {
			if (conn!=null){
				try {
					conn.close();
				}catch (SQLException e){
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void changeLatCustomer(customer user,float newLat) throws BaseException{
		if (newLat==-1) throw new BusinessException("坐标纬度不能为空");
		Connection conn=null;
		try {
			conn= DBUtil.getConnection();
			String sql="UPDATE customer SET cus_latitude=? WHERE user_id=?\n";
			PreparedStatement pst=conn.prepareStatement(sql);
			pst.setFloat(1,newLat);
			pst.setInt(2,user.getUserid());
			pst.execute();
			pst.close();

			JOptionPane.showMessageDialog(null, "纬度修改成功! ", "提示" , JOptionPane.PLAIN_MESSAGE);
		}catch (SQLException ex){
			throw new DbException(ex);
		}finally {
			if (conn!=null){
				try {
					conn.close();
				}catch (SQLException e){
					e.printStackTrace();
				}
			}
		}
	}

}





