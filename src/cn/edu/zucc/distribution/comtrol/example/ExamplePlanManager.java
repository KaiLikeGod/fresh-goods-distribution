package cn.edu.zucc.distribution.comtrol.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.distribution.itf.IPlanManager;
import cn.edu.zucc.distribution.model.BeanPlan;
import cn.edu.zucc.distribution.model.BeanUser;
import cn.edu.zucc.distribution.util.*;

import javax.swing.*;

public class ExamplePlanManager implements IPlanManager {

	@Override
	public BeanPlan addPlan(String name) throws BaseException {
		if (name==null || "".equals(name)) throw new BusinessException("�ƻ����Ʋ���Ϊ��");
		Connection conn=null;
		try {
			conn= DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql="INSERT INTO tbl_plan(user_id, plan_order, plan_name, create_time, step_count, start_step_count, finished_step_count) \n" +
					"VALUES (?, (SELECT dd.num FROM (SELECT max(plan_order)+1 num FROM tbl_plan WHERE user_id=?) dd), ?, ?, 0, 0, 0);";
			PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,BeanUser.currentLoginUser.getUser_id());
			pst.setString(2,BeanUser.currentLoginUser.getUser_id());
			pst.setString(3,name);
			pst.setTimestamp(4,new Timestamp(System.currentTimeMillis()));
			pst.execute();
			pst.close();
			conn.commit();
			JOptionPane.showMessageDialog(null, "success! ", "��ʾ" , JOptionPane.PLAIN_MESSAGE);
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

		return null;
	}

	@Override
	public List<BeanPlan> loadAll() throws BaseException {
		List<BeanPlan> result=new ArrayList<BeanPlan>();
		Connection conn=null;
		try {
			conn= DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql="SELECT *\n" +
					"FROM tbl_plan\n" +
					"WHERE user_id=?\n" +
					"ORDER BY plan_order";
			PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,BeanUser.currentLoginUser.getUser_id());
			ResultSet rs=pst.executeQuery();
			while (rs.next()){
				BeanPlan p=new BeanPlan();
				p.setPlanid(rs.getInt("plan_id"));
				p.setUserid(rs.getString("user_id"));
				p.setPlanorder(rs.getInt("plan_order"));
				p.setPlanname(rs.getString("plan_name"));
				p.setCreatetime(rs.getTimestamp("create_time"));
				p.setStepcount(rs.getInt("step_count"));
				p.setStart(rs.getInt("start_step_count"));
				p.setFinish(rs.getInt("finished_step_count"));
				result.add(p);
			}
			rs.close();
			pst.close();
			conn.commit();
			return result;
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
	public void deletePlan(BeanPlan plan) throws BaseException {
		Connection conn=null;
		try {
			conn= DBUtil.getConnection();
			conn.setAutoCommit(false);

			String sql="SELECT * FROM tbl_step WHERE plan_id=?";
			PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1,plan.getPlanid());
			ResultSet rs =pst.executeQuery();
			if (rs.next()) throw new BusinessException("�Ѵ��ڲ��裬�޷�ɾ��");
			rs.close();
			pst.close();

			sql="SELECT * FROM tbl_plan WHERE plan_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,plan.getPlanid());
			rs =pst.executeQuery();
			int order;
			String usrid="";
			if (rs.next()) {
				order=rs.getInt("plan_order");
				usrid=rs.getString("user_id");
			}
			else throw new BusinessException("�üƻ������ڣ�");
			if (!BeanUser.currentLoginUser.getUser_id().equals(usrid)){
				throw new BusinessException("Cannot delete others plans");
			}
			rs.close();
			pst.close();
//			`plan_id`, `user_id`, `plan_order`,
//			`plan_name`, `create_time`, `step_count`,
//			`start_step_count`, `finished_step_count`

			sql="DELETE FROM tbl_plan WHERE plan_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,plan.getPlanid());
			pst.execute();
			pst.close();

			sql="update  tbl_plan set plan_order=plan_order-1 where user_id=? and plan_order>?";
			pst=conn.prepareStatement(sql);
			pst.setString(1,plan.getUserid());
			pst.setInt(2,plan.getPlanorder());
			pst.execute();
			pst.close();
			conn.commit();
			JOptionPane.showMessageDialog(null, "success! ", "��ʾ" , JOptionPane.PLAIN_MESSAGE);
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

}
