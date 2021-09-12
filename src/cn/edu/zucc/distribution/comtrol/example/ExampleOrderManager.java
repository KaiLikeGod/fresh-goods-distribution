package cn.edu.zucc.distribution.comtrol.example;

import cn.edu.zucc.distribution.itf.IOrderMannger;
import cn.edu.zucc.distribution.model.*;
import cn.edu.zucc.distribution.util.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ExampleOrderManager implements IOrderMannger {
    public static Date strToDate(String strDate) {//时间转换
        String str = strDate;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date d = null;
        try {
            d = format.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Date date = new Date(d.getTime());
        return date;
    }

    public void CreateOrder(String time) throws BaseException{
        if (time==null||"".equals(time)) throw new BusinessException("约定送达时间不能为空！");
        Date date=strToDate(time);
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="SELECT house_id,count(*) num FROM orders_information WHERE user_id=? AND order_id=0 GROUP BY house_id";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,customer.currentLoginUser.getUserid());
            ResultSet rs= pst.executeQuery();
            while (rs.next()){
                int order=1;
                int houseid=rs.getInt(1);
                int num=rs.getInt(2);
                String sql1="select max(order_id) from orders";
                PreparedStatement pst1=conn.prepareStatement(sql1);
                ResultSet rs1=pst1.executeQuery();
                if (rs1.next()){
                    order=rs1.getInt(1)+1;
                }
                rs1.close();
                pst1.close();

                float weight=0;
                boolean cold=false;
                float v=0;
                sql1="SELECT SUM(ors.orders_weight) as weight,SUM(ors.orders_size) as v,MAX(g.keep_need_cold) as cold " +
                        "FROM orders_information as ors,goods as g " +
                        "WHERE (ors.goods_id=g.goods_id AND ors.user_id=? AND ors.house_id=? AND ors.order_id=0)";
//                System.out.println(sql1);
                pst1=conn.prepareStatement(sql1);
                pst1.setInt(1,customer.currentLoginUser.getUserid());
                pst1.setInt(2,houseid);
                rs1=pst1.executeQuery();
                if (rs1.next()){
                    weight=rs1.getFloat("weight");
                    v=rs1.getInt("v");
                    if (rs1.getInt("cold")>0) cold=true;
                }
                rs1.close();
                pst1.close();

                sql1="INSERT INTO " +
                        "orders (order_id, user_id,order_weight, order_number," +
                        " order_volume, order_time, order_is_cold, order_state) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, '已下单');\n";
                System.out.println(sql1);
                pst1=conn.prepareStatement(sql1);
                pst1.setInt(1,order);
                pst1.setInt(2,customer.currentLoginUser.getUserid());
                pst1.setFloat(3,weight);
                pst1.setInt(4,num);
                pst1.setFloat(5,v);
                pst1.setDate(6,date);
                pst1.setBoolean(7,cold);
                pst1.execute();

                sql1="UPDATE orders_information SET order_id=? WHERE (order_id=0) AND (user_id=?) and house_id=?;";
                pst1= conn.prepareStatement(sql1);
                pst1.setInt(1,order);
                pst1.setInt(2,customer.currentLoginUser.getUserid());
                pst1.setInt(3,houseid);
                pst1.execute();
                pst1.close();

            }
            rs.close();
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
    public void DeleteBuyCar(order_information car) throws BaseException{
        List<goods> result=new ArrayList<goods>();
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="delete from orders_information where goods_id=? and order_id = 0 and user_id=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,car.getGoodsid());
            pst.setInt(2,customer.currentLoginUser.getUserid());
            pst.execute();
            pst.close();

            int num=0;
            sql="select inventory_number from goods where goods_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,car.getGoodsid());
            ResultSet rs= pst.executeQuery();
            if (rs.next()){
                num=rs.getInt(1);
            }else throw new BusinessException("商品不存在！");
            rs.close();
            pst.close();

            sql="UPDATE goods SET inventory_number=? WHERE (goods_id=?);";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,num+(int) car.getOrderspecifications());
            pst.setInt(2,car.getGoodsid());
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

    public List<orders> loadAllOrdersById(int id) throws BaseException{
        List<orders> result=new  ArrayList<orders>();
        Connection conn=null;
        try {
            boolean flag=false;
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="SELECT *\n" +
                    "FROM orders\n" +
                    "WHERE order_id=? and order_id>0\n" +
                    "ORDER BY order_id";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs=pst.executeQuery();
            while (rs.next()){
                flag=true;
                orders p=new orders();
                p.setOrderid(rs.getInt(1));
                p.setUserid(rs.getInt(2));
                p.setOrderweight(rs.getFloat(3));
                p.setOrdernumber(rs.getInt(4));
                p.setOrderv(rs.getFloat(5));
                p.setOrdertime(rs.getDate(6));
                p.setOrderiscold(rs.getBoolean(7));
                p.setOrderstate(rs.getString(8));
                result.add(p);
            }
            if (!flag) result=null;
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

    public List<orders> loadAllOrdersAdmin() throws BaseException{
        List<orders> result=new  ArrayList<orders>();
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="SELECT *\n" +
                    "FROM orders\n" +
                    "WHERE order_id>0\n" +
                    "ORDER BY order_id";
            PreparedStatement pst=conn.prepareStatement(sql);
            ResultSet rs=pst.executeQuery();
            while (rs.next()){
                orders p=new orders();
                p.setOrderid(rs.getInt(1));
                p.setUserid(rs.getInt(2));
                p.setOrderweight(rs.getFloat(3));
                p.setOrdernumber(rs.getInt(4));
                p.setOrderv(rs.getFloat(5));
                p.setOrdertime(rs.getDate(6));
                p.setOrderiscold(rs.getBoolean(7));
                p.setOrderstate(rs.getString(8));
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

    public List<orders> loadAllOrders() throws BaseException{
        List<orders> result=new  ArrayList<orders>();
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="SELECT *\n" +
                    "FROM orders\n" +
                    "WHERE user_id=? and order_id>0\n" +
                    "ORDER BY order_id";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1, customer.currentLoginUser.getUserid());
            ResultSet rs=pst.executeQuery();
            while (rs.next()){
                orders p=new orders();
                p.setOrderid(rs.getInt(1));
                p.setUserid(rs.getInt(2));
                p.setOrderweight(rs.getFloat(3));
                p.setOrdernumber(rs.getInt(4));
                p.setOrderv(rs.getFloat(5));
                p.setOrdertime(rs.getDate(6));
                p.setOrderiscold(rs.getBoolean(7));
                p.setOrderstate(rs.getString(8));
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
    public List<order_information> loadAllorder_information(int orderid) throws BaseException{
        List<order_information> result=new  ArrayList<order_information>();
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="SELECT *\n" +
                    "FROM orders_information\n" +
                    "WHERE order_id=? and order_id>0\n" +
                    "ORDER BY goods_id";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1, orderid);
            ResultSet rs=pst.executeQuery();
            while (rs.next()){
                order_information p=new order_information();
                p.setOrderid(rs.getInt(1));
                p.setGoodsid(rs.getInt(2));
                p.setOrdersnumber(rs.getInt(3));
                p.setOrderweight(rs.getFloat(4));
                p.setOrdersize(rs.getFloat(5));
                p.setOrderspecifications(rs.getFloat(6));
                p.setUserid(rs.getInt(7));
                p.setPrice(rs.getInt(8));
                p.setGoodsName(rs.getString(9));
                p.setHouseid(rs.getInt(10));
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
    public void OrderReturn(int orderid) throws BaseException{
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="SELECT order_state\n" +
                    "FROM orders\n" +
                    "WHERE order_id=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1, orderid);
            ResultSet rs=pst.executeQuery();
            if (rs.next()){
                if (rs.getString(1).equals("配送中")) throw new BusinessException("订单配送中，无法退货，请等待订单送达！");
            }else throw new BusinessException("订单不存在！");
            rs.close();
            pst.close();

            sql="UPDATE orders SET  order_state='已退货' WHERE (order_id=?)";
            pst= conn.prepareStatement(sql);
            pst.setInt(1,orderid);
            pst.execute();
            pst.close();

            sql="select goods_id,orders_specifications from orders_information where order_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1, orderid);
            rs=pst.executeQuery();
            while (rs.next()){
                int num=0;
                String sql1="select inventory_number from goods where goods_id=?";
                PreparedStatement pst1=conn.prepareStatement(sql1);
                pst1.setInt(1,rs.getInt(1));
                ResultSet rs1= pst1.executeQuery();
                if (rs1.next()){
                    num=rs1.getInt(1);
                }else throw new BusinessException("包含商品已下架，无法退货！");
                rs1.close();
                pst1.close();

                sql1="UPDATE goods SET inventory_number=? WHERE (goods_id=?);";
                pst1=conn.prepareStatement(sql1);
                pst1.setInt(1,num+(int)rs.getFloat(2));
                pst1.setInt(2,rs.getInt(1));
                pst1.execute();
                pst1.close();
            }
            rs.close();
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
    public FiveTuple<Integer,Float,Float,Float,Boolean> customerBuyFind() throws BaseException{
        Connection conn=null;
        try {
            String name="";
            int num=0;
            float price=0;
            float weight=0;
            float v=0;
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="select customer.user_name from customer ,orders_information where orders_information.user_id=customer.user_id and orders_information.user_id=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,customer.currentLoginUser.getUserid());
            ResultSet rs= pst.executeQuery();
            if (rs.next()){
                customer.currentLoginUser.setUsername(rs.getString(1));
            }else {
                rs.close();
                pst.close();
                return new FiveTuple<Integer,Float,Float,Float,Boolean>(0, (float)0, (float)0, (float)0,false);
            }
            rs.close();
            pst.close();


            sql="SELECT COUNT(*) number,SUM(price) price,SUM(orders_weight) weight,SUM(orders_size) v\n" +
                    "FROM orders_information\n" +
                    "WHERE user_id=? AND order_id>0";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,customer.currentLoginUser.getUserid());
            rs= pst.executeQuery();
            if (rs.next()){
                num=rs.getInt(1);
                price=rs.getFloat(2);
                weight=rs.getFloat(3);
                v=rs.getFloat(4);
            }
            rs.close();
            pst.close();
            conn.commit();
            return new FiveTuple<>(num, price, weight, v,true);
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

    public List<orders> loadAllOrdersNotDelivery() throws BaseException{
        List<orders> result=new  ArrayList<orders>();
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="SELECT *\n" +
                    "FROM orders\n" +
                    "WHERE order_id>0 and orders.order_state ='已下单'\n" +
                    "ORDER BY order_id";
            PreparedStatement pst=conn.prepareStatement(sql);
            ResultSet rs=pst.executeQuery();
            while (rs.next()){
                orders p=new orders();
                p.setOrderid(rs.getInt(1));
                p.setUserid(rs.getInt(2));
                p.setOrderweight(rs.getFloat(3));
                p.setOrdernumber(rs.getInt(4));
                p.setOrderv(rs.getFloat(5));
                p.setOrdertime(rs.getDate(6));
                p.setOrderiscold(rs.getBoolean(7));
                p.setOrderstate(rs.getString(8));
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
    public int getHouseId(int orderid) throws BaseException{
        int result=0;
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="SELECT house_id\n" +
                    "FROM orders_information\n" +
                    "WHERE order_id=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,orderid);
            ResultSet rs=pst.executeQuery();
            if (rs.next()){
                result=rs.getInt("house_id");
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

}
