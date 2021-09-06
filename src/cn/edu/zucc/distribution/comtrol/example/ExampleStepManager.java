package cn.edu.zucc.distribution.comtrol.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.distribution.itf.IStepManager;
import cn.edu.zucc.distribution.model.BeanPlan;
import cn.edu.zucc.distribution.model.BeanStep;
import cn.edu.zucc.distribution.util.BaseException;
import cn.edu.zucc.distribution.util.BusinessException;
import cn.edu.zucc.distribution.util.DBUtil;
import cn.edu.zucc.distribution.util.DbException;

import javax.swing.*;

public class ExampleStepManager implements IStepManager {

	@Override
	public void add(BeanPlan plan, String name, String planstartdate,
			String planfinishdate) throws BaseException {
		if (name==null || "".equals(name)) throw new BusinessException("�������Ʋ���Ϊ��");
		if (planstartdate==null || "".equals(planstartdate)) throw new BusinessException("�ƻ���ʼʱ�䲻��Ϊ�գ�");
		if (planfinishdate==null || "".equals(planfinishdate)) throw new BusinessException("�ƻ����ʱ�䲻��Ϊ�գ�");
		Connection conn=null;
		try {
			conn= DBUtil.getConnection();
			conn.setAutoCommit(false);

			String sql="select max(step_order) from tbl_step where plan_id=?";
			PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1, plan.getPlanid());
			int order=0;
			ResultSet rs=pst.executeQuery();
			if(rs.next())
			{
				order=rs.getInt(1);
			}

			sql="INSERT INTO tbl_step (plan_id, step_order, step_name, plan_begin_time, plan_end_time)\n" +
					"VALUES (?, ?,?,?,?);\n";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,plan.getPlanid());
			pst.setInt(2,order+1);
			pst.setString(3,name);
			pst.setTimestamp(4,Timestamp.valueOf(planstartdate));
			pst.setTimestamp(5,Timestamp.valueOf(planfinishdate));
			pst.execute();
			pst.close();

			sql="update tbl_plan set step_count=step_count+1 where plan_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,plan.getPlanid());
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

	@Override
	public List<BeanStep> loadSteps(BeanPlan plan) throws BaseException {
		List<BeanStep> result=new ArrayList<BeanStep>();
		Connection conn=null;
		try {
			conn= DBUtil.getConnection();
			String sql="SELECT *\n" +
					"FROM tbl_step\n" +
					"WHERE plan_id=?\n" +
					"ORDER BY step_order";
			PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1, plan.getPlanid());
			ResultSet rs=pst.executeQuery();
			while (rs.next()){
				BeanStep p=new BeanStep();
				p.setStepid(rs.getInt("step_id"));
				p.setPlanid(rs.getInt("plan_id"));
				p.setSteporder(rs.getInt("step_order"));
				p.setStepname(rs.getString("step_name"));
				p.setStepbegin(rs.getTimestamp("plan_begin_time"));
				p.setStepend(rs.getTimestamp("plan_end_time"));
				p.setRealbegin(rs.getTimestamp("real_begin_time"));
				p.setRealend(rs.getTimestamp("real_end_time"));
				result.add(p);
			}
			return result;
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
	public void deleteStep(BeanStep step) throws BaseException {
		Connection conn=null;
		try {
			conn= DBUtil.getConnection();
			conn.setAutoCommit(false);

			String sql="select real_begin_time " +
					"from tbl_step " +
					"where step_id=?";
			PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1,step.getStepid());
			ResultSet rs=pst.executeQuery();
			while(rs.next())
			{
				if(rs.getTimestamp(1)!=null)
				{
					sql="update tbl_plan " +
							"set start_step_count=start_step_count-1 " +
							"where plan_id=?";
					pst=conn.prepareStatement(sql);
					pst.setInt(1,step.getPlanid());
					pst.execute();
				}

			}
			sql="select real_end_time " +
					"from tbl_step " +
					"where step_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,step.getStepid());
			rs =pst.executeQuery();
			while(rs.next())
			{
				if(rs.getTimestamp(1)!=null)
					sql="update tbl_plan set finished_step_count=finished_step_count-1 where plan_id=?";
				pst=conn.prepareStatement(sql);
				pst.setInt(1,step.getPlanid());
				pst.execute();
			}

			rs.close();
			sql="delete from tbl_step where step_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,step.getStepid());
			pst.execute();

			sql="update tbl_plan set step_count=step_count-1 where plan_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,step.getPlanid());
			pst.execute();


			sql="update tbl_step set step_order=step_order-1 where plan_id=? and step_order>?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,step.getPlanid());
			pst.setInt(2,step.getSteporder());
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

	@Override
	public void startStep(BeanStep step) throws BaseException {
		Connection conn=null;
		try {
			conn= DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql="select real_begin_time from tbl_step where step_id=?";
			PreparedStatement pst= conn.prepareStatement(sql);
			pst.setInt(1,step.getStepid());
			ResultSet rs=pst.executeQuery();
			if (rs.next()){
				if (rs.getTimestamp(1)!=null)
					throw new BusinessException("�ò����Ѿ���ʼ�ˣ������ظ���ʼ��");
			}else {
				throw new BusinessException("�ò��費���ڣ�");
			}
			rs.close();
			pst.close();
			sql="update tbl_step set real_begin_time=now() where step_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,step.getStepid());
			pst.execute();
			pst.close();

			sql="update tbl_plan set start_step_count=start_step_count+1 where plan_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,step.getPlanid());
			pst.execute();
			pst.close();

			conn.commit();
			JOptionPane.showMessageDialog(null, "���迪ʼ�� ", "��ʾ" , JOptionPane.PLAIN_MESSAGE);
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
	public void finishStep(BeanStep step) throws BaseException {
		Connection conn=null;
		try {
			conn= DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql="select real_end_time from tbl_step where step_id=?";
			PreparedStatement pst= conn.prepareStatement(sql);
			pst.setInt(1,step.getStepid());
			ResultSet rs=pst.executeQuery();
			if (rs.next()){
				if (rs.getTimestamp(1)!=null)
					throw new BusinessException("�ò����Ѿ�������");
			}else {
				throw new BusinessException("�ò��費���ڣ�");
			}
			rs.close();
			pst.close();
			sql="update tbl_step set real_end_time=now() where step_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,step.getStepid());
			pst.execute();
			pst.close();

			sql="update tbl_plan set finished_step_count=finished_step_count+1 where plan_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,step.getPlanid());
			pst.execute();
			pst.close();

			conn.commit();
			JOptionPane.showMessageDialog(null, "������ɣ� ", "��ʾ" , JOptionPane.PLAIN_MESSAGE);
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
	public void moveUp(BeanStep step) throws BaseException {
		Connection conn=null;
		try {
			int order=0;
			conn= DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql="select step_order\n" +
					"from tbl_step\n" +
					"where step_id=?";
			PreparedStatement pst= conn.prepareStatement(sql);
			pst.setInt(1,step.getStepid());
			ResultSet rs= pst.executeQuery();
			if (rs.next()){
				order=rs.getInt(1);
				if (order==1) throw new BusinessException("�ò����Ѿ��ǵ�һ����");
			}else {
				rs.close();
				pst.close();
				throw new BusinessException("���費���ڣ�");
			}
			rs.close();
			pst.close();

			int upid=0;
			sql="select step_id\n" +
					"from tbl_step\n" +
					"where plan_id=? and step_order=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,step.getPlanid());
			pst.setInt(2,step.getSteporder()-1);
			rs= pst.executeQuery();
			if (rs.next()){
				upid=rs.getInt(1);
				System.out.println(upid);
			}
			rs.close();
			pst.close();

			sql="update tbl_step set step_order=? where step_order=? and plan_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,order-1);
			pst.setInt(2,order);
			pst.setInt(3,step.getPlanid());
			pst.execute();
			pst.close();

			sql="update tbl_step set step_order=? where step_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,order);
			pst.setInt(2,upid);
			pst.execute();
			pst.close();

			conn.commit();
			JOptionPane.showMessageDialog(null, "��������ɣ� ", "��ʾ" , JOptionPane.PLAIN_MESSAGE);
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
	public void moveDown(BeanStep step) throws BaseException {
		Connection conn=null;
		try {
			int order=0;
			conn= DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql="select step_order\n" +
					"from tbl_step\n" +
					"where step_id=?";
			PreparedStatement pst= conn.prepareStatement(sql);
			pst.setInt(1,step.getStepid());
			ResultSet rs= pst.executeQuery();
			if (rs.next()){
				order=rs.getInt(1);
			}else {
				rs.close();
				pst.close();
				throw new BusinessException("���費���ڣ�");
			}
			rs.close();
			pst.close();

			int maxorder=0;
			sql="select max(step_order)\n" +
					"from tbl_step\n" +
					"where plan_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,step.getPlanid());
			rs= pst.executeQuery();
			if (rs.next()) {
				maxorder = rs.getInt(1);
				if (maxorder==order) throw new BusinessException("�Ѿ������һ�����裡");
			}
			rs.close();
			pst.close();

			int upid=0;
			sql="select step_id\n" +
					"from tbl_step\n" +
					"where plan_id=? and step_order=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,step.getPlanid());
			pst.setInt(2,step.getSteporder()+1);
			rs= pst.executeQuery();
			if (rs.next()){
				upid=rs.getInt(1);
				System.out.println(upid);
			}
			rs.close();
			pst.close();

			sql="update tbl_step set step_order=? where step_order=? and plan_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,order+1);
			pst.setInt(2,order);
			pst.setInt(3,step.getPlanid());
			pst.execute();
			pst.close();

			sql="update tbl_step set step_order=? where step_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,order);
			pst.setInt(2,upid);
			pst.execute();
			pst.close();

			conn.commit();
			JOptionPane.showMessageDialog(null, "��������ɣ� ", "��ʾ" , JOptionPane.PLAIN_MESSAGE);
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
