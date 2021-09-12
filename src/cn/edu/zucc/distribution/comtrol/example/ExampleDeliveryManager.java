package cn.edu.zucc.distribution.comtrol.example;

import cn.edu.zucc.distribution.DistributionUtil;
import cn.edu.zucc.distribution.itf.IDeliveryMannger;
import cn.edu.zucc.distribution.model.*;
import cn.edu.zucc.distribution.util.BaseException;
import cn.edu.zucc.distribution.util.BusinessException;
import cn.edu.zucc.distribution.util.DBUtil;
import cn.edu.zucc.distribution.util.DbException;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExampleDeliveryManager implements IDeliveryMannger {
    public List<car> loadAllCar() throws BaseException{
        List<car> result=new ArrayList<car>();
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="SELECT *\n" +
                    "FROM car\n" +
                    "ORDER BY car_id";
            PreparedStatement pst=conn.prepareStatement(sql);
            ResultSet rs=pst.executeQuery();
            while (rs.next()){
                car p=new car();
                p.setCarid(rs.getInt(1));
                p.setCarnum(rs.getString(2));
                p.setCarweight(rs.getFloat(3));
                p.setCarv(rs.getFloat(4));
                p.setCarphone(rs.getString(5));
                p.setCarcold(rs.getBoolean(6));
                p.setCarstate(rs.getString(7));
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

    public void createNewCar(String num,float weight,float v,String phone,boolean cold) throws BaseException{
        if (num==null || "".equals(num)) throw new BusinessException("车牌号不能为空");
        if (weight==-1) throw new BusinessException("车辆载重不能为空");
        if (v==-1) throw new BusinessException("车辆容积不能为空");
        if (phone==null || "".equals(phone)) throw new BusinessException("联系电话不能为空");
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="select car_id\n" +
                    "from car\n" +
                    "where car_num=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,num);
            ResultSet rs=pst.executeQuery();
            if (rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("车牌号为 "+num+" 的车辆已经存在！");
            }
            rs.close();
            pst.close();

            sql="select max(car_id)\n" +
                    "from car";
            int order=1;
            pst= conn.prepareStatement(sql);
            rs= pst.executeQuery();
            if (rs.next()){
                order=rs.getInt(1)+1;
            }
            rs.close();
            pst.close();

            sql="INSERT INTO car (car_id, car_num, car_weight, car_v, car_phone, car_cold,car_state) VALUES (?, ?, ?, ?, ?, ?,'空闲');";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,order);
            pst.setString(2,num);
            pst.setFloat(3,weight);
            pst.setFloat(4,v);
            pst.setString(5,phone);
            pst.setBoolean(6,cold);
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

    public void changeNewCar(int id,String phone,boolean cold) throws BaseException{
        if (phone==null || "".equals(phone)) throw new BusinessException("联系电话不能为空");
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="UPDATE car SET car_phone=?, car_cold=? WHERE car_id=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,phone);
            pst.setBoolean(2,cold);
            pst.setInt(3,id);
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

    public void DeleteCar(int id) throws BaseException{
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);

            String sql="select car_state from car where car_id=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet rs=pst.executeQuery();
            if (rs.next()){
                if (rs.getString(1).equals("配送")) {
                    rs.close();
                    pst.close();
                    throw new BusinessException("该车辆正在执行配送任务，无法删除！");
                }
            }

            sql="DELETE FROM car WHERE car_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,id);
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

    public void deliveryOK(List<orders> o,float w,float v,car c)throws BaseException{
        if (!c.getCarstate().equals("空闲")) throw new BusinessException("该车辆正在执行配送任务！");
        if (w>c.getCarweight() || v>c.getCarv()) throw new BusinessException("该车辆的载重或容积无法满足本次配送，请选择其他车辆！");
        if (!c.isCarcold()){
            for (int i=0;i<o.size();i++)
                if (o.get(i).isOrderiscold()) throw new BusinessException("本次配送需要冷藏车，当前选择车辆无法满足要求，请选择其他车辆");
        }
        float beginx=0;
        float beginy=0;
        List<Float> x=new ArrayList<Float>();
        List<Float> y=new ArrayList<Float>();
        List<Boolean> flag=new ArrayList<Boolean>();
        List<Integer> orderid=new ArrayList<Integer>();
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);

            String sql="select max(delivery_id)\n" +
                    "from delivery_information";
            int order=1;
            PreparedStatement pst= conn.prepareStatement(sql);
            ResultSet rs= pst.executeQuery();
            if (rs.next()){
                order=rs.getInt(1)+1;
            }
            rs.close();
            pst.close();

            sql="INSERT INTO delivery_information (delivery_id, car_id, delivery_weight, delivery_v,delivery_state) VALUES (?, ?, ?, ?,'配送中')";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,order);
            pst.setInt(2,c.getCarid());
            pst.setFloat(3,w);
            pst.setFloat(4,v);
            pst.execute();
            pst.close();

            sql = "UPDATE car SET car_state='配送' WHERE car_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, c.getCarid());
            pst.execute();
            pst.close();

            sql="select house_longitude,house_latitude from warehouses where house_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,DistributionUtil.orderMannger.getHouseId(o.get(1).getOrderid()));
            rs=pst.executeQuery();
            if (rs.next()){
                beginx=rs.getFloat(1);
                beginy=rs.getFloat(2);
            }
            rs.close();
            pst.close();

            for (int i=0;i<o.size();i++) {
                orderid.add(o.get(i).getOrderid());
                sql = "UPDATE orders SET order_state='配送中' WHERE order_id=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, o.get(i).getOrderid());
                pst.execute();
                pst.close();

                sql="select cus_longitude,cus_latitude from customer where user_id=?";
                pst=conn.prepareStatement(sql);
                pst.setInt(1,o.get(i).getUserid());
                rs=pst.executeQuery();
                if (rs.next()){
                    x.add(rs.getFloat(1));
                    y.add(rs.getFloat(2));
                    flag.add(false);
                }
                rs.close();
                pst.close();
            }

            orderid=TSP(beginx,beginy,x,y,flag,orderid);

            for (int i=0;i<orderid.size();i++){
                sql="UPDATE orders_information SET orders_number=? WHERE order_id=?";
                pst= conn.prepareStatement(sql);
                pst.setInt(1,i+1);
                pst.setInt(2,orderid.get(i));
                pst.execute();
                pst.close();

                sql="INSERT INTO deliver_detail (order_id, delivery_id, delivery_num) VALUES (?, ?, ?)";
                pst=conn.prepareStatement(sql);
                pst.setInt(1,orderid.get(i));
                pst.setInt(2,order);
                pst.setInt(3,i+1);
                pst.execute();
                pst.close();
            }

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

    public List<Integer> TSP(float beginx,float beginy,List<Float> x,List<Float> y,List<Boolean> flag,List<Integer> orderid){
        List<Integer> result=new ArrayList<Integer>();
        String a="本次配送的订单顺序为：";
        double sum=0;
        double L=0;
        for (int i=0;i<x.size();i++){
            double min=999999;
            int minindex=-1;
            for (int j=0;j<x.size();j++){
                if (flag.get(j)) continue;
                L=(x.get(j)-beginx)*(x.get(j)-beginx)+(y.get(j)-beginy)*(y.get(j)-beginy);
                if (L<min) {
                    minindex=j;
                    min=L;
                }
            }
            beginx=x.get(minindex);
            beginy=y.get(minindex);
            sum=sum+Math.sqrt(L);
            flag.set(minindex,true);
            result.add(orderid.get(minindex));
            a=a+orderid.get(minindex)+"，";

        }
        a=a+"预计 配送总距离为："+sum;
        JOptionPane.showMessageDialog(null, a, "配送说明" , JOptionPane.PLAIN_MESSAGE);
        return result;
    }

    public List<delivery_information> loasAlldeliveryInformation() throws BaseException{
        List<delivery_information> result=new ArrayList<delivery_information>();
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="SELECT *\n" +
                    "FROM delivery_information\n" +
                    "ORDER BY delivery_id";
            PreparedStatement pst=conn.prepareStatement(sql);
            ResultSet rs=pst.executeQuery();
            while (rs.next()){
                delivery_information p=new delivery_information();
                p.setDeliveryid(rs.getInt(1));
                p.setCarid(rs.getInt(2));
                p.setDeliveryweight(rs.getFloat(3));
                p.setDeliveryv(rs.getFloat(4));
                p.setDeliveryStatic(rs.getString(5));
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

    public List<deliver_detail> loadAllDeliverDetail(int id) throws BaseException{
        List<deliver_detail> result=new ArrayList<deliver_detail>();
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="SELECT *\n" +
                    "FROM deliver_detail\n" +
                    "where delivery_id=?\n" +
                    "ORDER BY delivery_num";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet rs=pst.executeQuery();
            while (rs.next()){
                deliver_detail p=new deliver_detail();
                p.setOrderid(rs.getInt(1));
                p.setDeliveryid(rs.getInt(2));
                p.setDeliverynum(rs.getInt(3));
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

    public List<delivery_information> FinddeliveryInformation(int id) throws BaseException{
        List<delivery_information> result=new ArrayList<delivery_information>();
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            boolean flag=false;
            String sql="SELECT *\n" +
                    "FROM delivery_information\n" +
                    "where delivery_id=?\n" +
                    "ORDER BY delivery_id";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet rs=pst.executeQuery();
            while (rs.next()){
                flag=true;
                delivery_information p=new delivery_information();
                p.setDeliveryid(rs.getInt(1));
                p.setCarid(rs.getInt(2));
                p.setDeliveryweight(rs.getFloat(3));
                p.setDeliveryv(rs.getFloat(4));
                p.setDeliveryStatic(rs.getString(5));
                result.add(p);
            }
            rs.close();
            pst.close();
            conn.commit();
            if (!flag) result=null;
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

    public void FinishDelivery(delivery_information df) throws BaseException{
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            int carid=0;
            String sql="select car_id from delivery_information where delivery_id=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,df.getDeliveryid());
            ResultSet rs= pst.executeQuery();
            if  (rs.next()){
                carid=rs.getInt(1);
            }
            rs.close();
            pst.close();

            sql="UPDATE car SET car_state='空闲' WHERE car_id=?\n";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,carid);
            pst.execute();
            pst.close();

            sql="UPDATE delivery_information SET delivery_state='已送达' WHERE delivery_id=?\n";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,df.getDeliveryid());
            pst.execute();
            pst.close();

            sql="select order_id from deliver_detail where delivery_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,df.getDeliveryid());
            rs= pst.executeQuery();
            while (rs.next()){
                String sql1="UPDATE orders SET order_state='已送达' WHERE order_id=?";
                PreparedStatement pst1=conn.prepareStatement(sql1);
                pst1.setInt(1,rs.getInt(1));
                pst1.execute();
            }
            rs.close();
            pst.close();

            conn.commit();
            JOptionPane.showMessageDialog(null, "订单状态修改成功! ", "提示" , JOptionPane.PLAIN_MESSAGE);
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

    public List<delivery_information> loadAlldeliveryInformation() throws BaseException{
        List<delivery_information> result=new ArrayList<delivery_information>();
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="SELECT *\n" +
                    "FROM delivery_information\n" +
                    "where delivery_state='配送中'\n" +
                    "ORDER BY delivery_id";
            PreparedStatement pst=conn.prepareStatement(sql);
            ResultSet rs=pst.executeQuery();
            while (rs.next()){
                delivery_information p=new delivery_information();
                p.setDeliveryid(rs.getInt(1));
                p.setCarid(rs.getInt(2));
                p.setDeliveryweight(rs.getFloat(3));
                p.setDeliveryv(rs.getFloat(4));
                p.setDeliveryStatic(rs.getString(5));
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

    public List<delivery_information> FinddeliveryInformations(int id) throws BaseException{
        List<delivery_information> result=new ArrayList<delivery_information>();
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            boolean flag=false;
            String sql="SELECT *\n" +
                    "FROM delivery_information\n" +
                    "where delivery_id=? and delivery_state='配送中'\n" +
                    "ORDER BY delivery_id";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet rs=pst.executeQuery();
            while (rs.next()){
                flag=true;
                delivery_information p=new delivery_information();
                p.setDeliveryid(rs.getInt(1));
                p.setCarid(rs.getInt(2));
                p.setDeliveryweight(rs.getFloat(3));
                p.setDeliveryv(rs.getFloat(4));
                p.setDeliveryStatic(rs.getString(5));
                result.add(p);
            }
            rs.close();
            pst.close();
            conn.commit();
            if (!flag) result=null;
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
}
