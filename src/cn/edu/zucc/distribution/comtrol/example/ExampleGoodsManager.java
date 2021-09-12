package cn.edu.zucc.distribution.comtrol.example;


import cn.edu.zucc.distribution.itf.IGoodsManager;
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

public class ExampleGoodsManager implements IGoodsManager {

    @Override
    public List<goods> loadAll() throws BaseException{
        List<goods> result=new ArrayList<goods>();
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="SELECT *\n" +
                    "FROM goods\n" +
                    "ORDER BY goods_id";
            PreparedStatement pst=conn.prepareStatement(sql);
            ResultSet rs=pst.executeQuery();
            while (rs.next()){
                goods p=new goods();
                p.setGoodsid(rs.getInt(1));
                p.setHouseid(rs.getInt(2));
                p.setTypeid(rs.getInt(3));
                p.setGoodsname(rs.getString(4));
                p.setGoodsprice(rs.getFloat(5));
                p.setGoodssize(rs.getFloat(6));
                p.setOrdersweight(rs.getFloat(7));
                p.setInventorynumber(rs.getInt(8));
                p.setKeepneedcold(rs.getBoolean(9));
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
    public List<goods> FindGoodsId(int goodId) throws BaseException{
        List<goods> result=new ArrayList<goods>();
        boolean bool=false;
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="SELECT *\n" +
                    "FROM goods\n" +
                    "WHERE goods_id=?\n" +
                    "ORDER BY goods_id";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,goodId);
            ResultSet rs=pst.executeQuery();
            while (rs.next()){
                bool=true;
                goods p=new goods();
                p.setGoodsid(rs.getInt(1));
                p.setHouseid(rs.getInt(2));
                p.setTypeid(rs.getInt(3));
                p.setGoodsname(rs.getString(4));
                p.setGoodsprice(rs.getFloat(5));
                p.setGoodssize(rs.getFloat(6));
                p.setOrdersweight(rs.getFloat(7));
                p.setInventorynumber(rs.getInt(8));
                p.setKeepneedcold(rs.getBoolean(9));
                result.add(p);
            }
            if (!bool){
                result=null;
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
    public List<goods> FindWarehouseIdGoods(int warehouseid) throws BaseException{
        List<goods> result=new ArrayList<goods>();
        boolean bool=false;
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="SELECT *\n" +
                    "FROM goods\n" +
                    "WHERE house_id=?\n" +
                    "ORDER BY goods_id";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,warehouseid);
            ResultSet rs=pst.executeQuery();
            while (rs.next()){
                bool=true;
                goods p=new goods();
                p.setGoodsid(rs.getInt(1));
                p.setHouseid(rs.getInt(2));
                p.setTypeid(rs.getInt(3));
                p.setGoodsname(rs.getString(4));
                p.setGoodsprice(rs.getFloat(5));
                p.setGoodssize(rs.getFloat(6));
                p.setOrdersweight(rs.getFloat(7));
                p.setInventorynumber(rs.getInt(8));
                p.setKeepneedcold(rs.getBoolean(9));
                result.add(p);
            }
            if (!bool){
                result=null;
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
    public List<goods> FindsGoodsType(int Typeid) throws BaseException{
        List<goods> result=new ArrayList<goods>();
        boolean bool=false;
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="SELECT *\n" +
                    "FROM goods\n" +
                    "WHERE type_id=?\n" +
                    "ORDER BY goods_id";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,Typeid);
            ResultSet rs=pst.executeQuery();
            while (rs.next()){
                bool=true;
                goods p=new goods();
                p.setGoodsid(rs.getInt(1));
                p.setHouseid(rs.getInt(2));
                p.setTypeid(rs.getInt(3));
                p.setGoodsname(rs.getString(4));
                p.setGoodsprice(rs.getFloat(5));
                p.setGoodssize(rs.getFloat(6));
                p.setOrdersweight(rs.getFloat(7));
                p.setInventorynumber(rs.getInt(8));
                p.setKeepneedcold(rs.getBoolean(9));
                result.add(p);
            }
            if (!bool){
                result=null;
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
    public List<goods> FindGoodsName(String Goodsname) throws BaseException{
        List<goods> result=new ArrayList<goods>();
        boolean bool=false;
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="SELECT *\n" +
                    "FROM goods\n" +
                    "WHERE goods_name=?\n" +
                    "ORDER BY goods_id";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,Goodsname);
            ResultSet rs=pst.executeQuery();
            while (rs.next()){
                bool=true;
                goods p=new goods();
                p.setGoodsid(rs.getInt(1));
                p.setHouseid(rs.getInt(2));
                p.setTypeid(rs.getInt(3));
                p.setGoodsname(rs.getString(4));
                p.setGoodsprice(rs.getFloat(5));
                p.setGoodssize(rs.getFloat(6));
                p.setOrdersweight(rs.getFloat(7));
                p.setInventorynumber(rs.getInt(8));
                p.setKeepneedcold(rs.getBoolean(9));
                result.add(p);
            }
            if (!bool){
                result=null;
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

    public void AddToBuyCar(int now,goods good) throws BaseException{
        boolean bool=false;
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);

            String sql1="select * from orders_information where order_id=0 and goods_id=? and user_id=?";
            PreparedStatement pst1=conn.prepareStatement(sql1);
            pst1.setInt(1,good.getGoodsid());
            pst1.setInt(2,customer.currentLoginUser.getUserid());
            ResultSet rs=pst1.executeQuery();
            if (rs.next()){
                String sql="update orders_information set orders_weight=?,orders_size=?," +
                        "orders_specifications=?,Price=? where order_id=0 and goods_id=? and user_id=?";
                PreparedStatement pst=conn.prepareStatement(sql);
                pst.setFloat(1,rs.getFloat("orders_weight")+good.getOrdersweight() * now);
                pst.setFloat(2,rs.getFloat("orders_size")+good.getGoodssize() * now);
                pst.setFloat(3,rs.getFloat("orders_specifications")+now);
                pst.setFloat(4,rs.getFloat("Price")+now * good.getGoodsprice());
                pst.setInt(5,good.getGoodsid());
                pst.setInt(6,customer.currentLoginUser.getUserid());
                pst.execute();
                pst.close();
            }
            else {
                String sql = "INSERT INTO orders_information " +
                        "(order_id, goods_id, orders_number, orders_weight, orders_size, orders_specifications, user_id,Price,goods_name,house_id) " +
                        "VALUES (0, ?, 0, ?, ?, ?, ?,?,?,?);";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setInt(1, good.getGoodsid());
                pst.setFloat(2, good.getOrdersweight() * now);
                pst.setFloat(3, good.getGoodssize() * now);
                pst.setFloat(4, now);
                pst.setInt(5, customer.currentLoginUser.getUserid());
                pst.setFloat(6, now * good.getGoodsprice());
                pst.setString(7, good.getGoodsname());
                pst.setInt(8,good.getHouseid());
                pst.execute();
                pst.close();
            }
            rs.close();
            pst1.close();

            String sql="UPDATE goods SET inventory_number=? WHERE (goods_id=?);";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,good.getInventorynumber()-now);
            pst.setInt(2,good.getGoodsid());
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
    public List<order_information> LoadBuyCar() throws BaseException{
        List<order_information> result=new ArrayList<order_information>();
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="select * from orders_information where user_id=? and order_id=0";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,customer.currentLoginUser.getUserid());
            ResultSet rs= pst.executeQuery();
            while (rs.next()){
                order_information p=new order_information();
                p.setOrderid(rs.getInt(1));
                p.setGoodsid(rs.getInt(2));
                p.setOrdersnumber(rs.getInt(3));
                p.setOrderweight(rs.getFloat(4));
                p.setOrdersize(rs.getFloat(5));
                p.setOrderspecifications(rs.getFloat(6));
                p.setUserid(rs.getInt(7));
                p.setPrice(rs.getFloat(8));
                p.setGoodsName(rs.getString(9));
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

    public List<commodity_type> loadAllType() throws BaseException{
        List<commodity_type> result=new ArrayList<commodity_type>();
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="select * from commodity_type order by type_id";
            PreparedStatement pst=conn.prepareStatement(sql);
            ResultSet rs=pst.executeQuery();
            while (rs.next()){
                commodity_type p=new commodity_type();
                p.setTypeid(rs.getInt(1));
                p.setTypename(rs.getString(2));
                p.setTypedescribe(rs.getString(3));
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
    public void createNewType(String name,String describle) throws BaseException{
        if (name==null || "".equals(name)) throw new BusinessException("类别名称不能为空");
        if (describle==null || "".equals(describle)) throw new BusinessException("类别描述不能为空");
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="select type_name\n" +
                    "from commodity_type\n" +
                    "where type_name=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,name);
            ResultSet rs=pst.executeQuery();
            if (rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("类别"+name+"已经存在！");
            }
            rs.close();
            pst.close();

            sql="select max(type_id)\n" +
                    "from commodity_type";
            int order=1;
            pst= conn.prepareStatement(sql);
            rs= pst.executeQuery();
            if (rs.next()){
                order=rs.getInt(1)+1;
            }
            rs.close();
            pst.close();

            sql="INSERT INTO commodity_type (type_id, type_name, type_describe) VALUES (?, ?, ?)";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,order);
            pst.setString(2,name);
            pst.setString(3,describle);
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
    public void changeType(int id,String name,String describle) throws BaseException{
        if (name==null || "".equals(name)) throw new BusinessException("类别名称不能为空");
        if (describle==null || "".equals(describle)) throw new BusinessException("类别描述不能为空");
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="UPDATE commodity_type SET type_name=?, type_describe=? WHERE type_id=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,name);
            pst.setString(2,describle);
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

    public void DeleteType(int id) throws BaseException{
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);

            String sql="select goods_id from goods where type_id=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet rs=pst.executeQuery();
            if (rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("该类别已有商品，无法删除！");
            }

            sql="DELETE FROM commodity_type WHERE type_id=?\n";
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

    public List<warehouses> loadAllWarehouse() throws BaseException{
        List<warehouses> result=new ArrayList<warehouses>();
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="select * from warehouses order by house_id";
            PreparedStatement pst=conn.prepareStatement(sql);
            ResultSet rs=pst.executeQuery();
            while (rs.next()){
                warehouses p=new warehouses();
                p.setHouseid(rs.getInt(1));
                p.setHousename(rs.getString(2));
                p.setHouseaddress(rs.getString(3));
                p.setHousephone(rs.getString(4));
                p.setHouseLgt(rs.getFloat(5));
                p.setHouselat(rs.getFloat(6));
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

    public void createNewWarehouse(String name,String address,String phone,float lgt,float lat) throws BaseException{
        if (name==null || "".equals(name)) throw new BusinessException("仓库名称不能为空");
        if (address==null || "".equals(address)) throw new BusinessException("仓库地址不能为空");
        if (phone==null || "".equals(phone)) throw new BusinessException("联系电话不能为空");
        if (lgt==-1) throw new BusinessException("经度不能为空");
        if (lat==-1) throw new BusinessException("纬度不能为空");
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="select house_id\n" +
                    "from warehouses\n" +
                    "where house_name=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,name);
            ResultSet rs=pst.executeQuery();
            if (rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("仓库 "+name+"已经存在！");
            }
            rs.close();
            pst.close();

            sql="select max(house_id)\n" +
                    "from warehouses";
            int order=1;
            pst= conn.prepareStatement(sql);
            rs= pst.executeQuery();
            if (rs.next()){
                order=rs.getInt(1)+1;
            }
            rs.close();
            pst.close();

            sql="INSERT INTO warehouses (house_id, house_name, house_address, house_phone, house_longitude, house_latitude) VALUES (?, ?, ?, ?, ?, ?)";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,order);
            pst.setString(2,name);
            pst.setString(3,address);
            pst.setString(4,phone);
            pst.setFloat(5,lgt);
            pst.setFloat(6,lat);
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

    public void changeNewWarehouse(int id, String name,String address,String phone,float lgt,float lat) throws BaseException{
        if (name==null || "".equals(name)) throw new BusinessException("仓库名称不能为空");
        if (address==null || "".equals(address)) throw new BusinessException("仓库地址不能为空");
        if (phone==null || "".equals(phone)) throw new BusinessException("联系电话不能为空");
        if (lgt==-1) throw new BusinessException("经度不能为空");
        if (lat==-1) throw new BusinessException("纬度不能为空");
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="UPDATE warehouses SET house_name=?, house_address=?, house_phone=?, house_longitude=?, house_latitude=? WHERE house_id=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,name);
            pst.setString(2,address);
            pst.setString(3,phone);
            pst.setFloat(4,lgt);
            pst.setFloat(5,lat);
            pst.setInt(6,id);
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
    public void DeleteHouse(int id) throws BaseException{
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);

            String sql="select house_id from goods where house_id=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet rs=pst.executeQuery();
            if (rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("该仓库内仍有商品，无法删除！");
            }

            sql="DELETE FROM warehouses WHERE house_id=?";
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
    public void changeGood(int id,int house,int type,String name,float price,float size,float weight,boolean cold) throws BaseException{
        if (name==null || "".equals(name)) throw new BusinessException("商品名称不能为空");
        if (house==-1) throw new BusinessException("仓库不能为空");
        if (type==-1) throw new BusinessException("类型不能为空");
        if (price==-1) throw new BusinessException("价格不能为空");
        if (size==-1) throw new BusinessException("商品规格不能为空");
        if (weight==-1) throw new BusinessException("商品重量不能为空");

        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="UPDATE goods SET house_id=?, type_id=?, goods_name=?, goods_price=?, goods_size=?, orders_weight=?, keep_need_cold=? WHERE goods_id=?\n";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,house);
            pst.setInt(2,type);
            pst.setString(3,name);
            pst.setFloat(4,price);
            pst.setFloat(5,size);
            pst.setFloat(6,weight);
            pst.setBoolean(7,cold);
            pst.setInt(8,id);
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

    public void DeleteGoods(int id) throws BaseException{
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);

            String sql="select goods_id from orders_information where goods_id=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet rs=pst.executeQuery();
            if (rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("该商品仍有订单，无法删除！");
            }

            sql="DELETE FROM goods WHERE goods_id=?\n";
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

    public void CreateNewGood(int house,int type,String name,float price,float size,float weight,boolean cold,int number) throws BaseException{
        if (name==null || "".equals(name)) throw new BusinessException("商品名称不能为空");
        if (house==-1) throw new BusinessException("仓库不能为空");
        if (type==-1) throw new BusinessException("类型不能为空");
        if (price==-1) throw new BusinessException("价格不能为空");
        if (size==-1) throw new BusinessException("商品规格不能为空");
        if (weight==-1) throw new BusinessException("商品重量不能为空");
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="select goods_name\n" +
                    "from goods\n" +
                    "where goods_name=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,name);
            ResultSet rs=pst.executeQuery();
            if (rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("商品"+name+"已经存在，请选择该商品采购！");
            }
            rs.close();
            pst.close();

            sql="select house_id\n" +
                    "from warehouses\n" +
                    "where house_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,house);
            rs=pst.executeQuery();
            if (!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("仓库不存在！");
            }
            rs.close();
            pst.close();

            sql="select type_id\n" +
                    "from commodity_type\n" +
                    "where type_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,type);
            rs=pst.executeQuery();
            if (!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("生鲜类别不存在！");
            }
            rs.close();
            pst.close();

            sql="select max(goods_id)\n" +
                    "from goods";
            int order=1;
            pst= conn.prepareStatement(sql);
            rs= pst.executeQuery();
            if (rs.next()){
                order=rs.getInt(1)+1;
            }
            rs.close();
            pst.close();

            sql="INSERT INTO goods (goods_id, house_id, type_id, goods_name, goods_price, goods_size, orders_weight, inventory_number, keep_need_cold) VALUES (?, ?, ?, ?, ?, ?, ?,?, ?)";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,order);
            pst.setInt(2,house);
            pst.setInt(3,type);
            pst.setString(4,name);
            pst.setFloat(5,price);
            pst.setFloat(6,size);
            pst.setFloat(7,weight);
            pst.setInt(8,number);
            pst.setBoolean(9,cold);
            pst.execute();
            pst.close();

            sql="select max(buy_id)\n" +
                    "from buy";
            int buy=1;
            pst= conn.prepareStatement(sql);
            rs= pst.executeQuery();
            if (rs.next()){
                buy=rs.getInt(1)+1;
            }
            rs.close();
            pst.close();

            sql="INSERT INTO buy (buy_id, goods_id, house_id, buy_number) VALUES (?, ?, ?, ?);";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,buy);
            pst.setInt(2,order);
            pst.setInt(3,house);
            pst.setInt(4,number);
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

    public void buyGoods(int id,int num,int house) throws BaseException{
        if (num==-1) throw new BusinessException("采购数量不能为空！");
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="UPDATE goods SET inventory_number=inventory_number+? WHERE goods_id=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,num);
            pst.setInt(2,id);
            pst.execute();
            pst.close();

            sql="select max(buy_id)\n" +
                    "from buy";
            int buy=1;
            pst= conn.prepareStatement(sql);
            ResultSet rs= pst.executeQuery();
            if (rs.next()){
                buy=rs.getInt(1)+1;
            }
            rs.close();
            pst.close();

            sql="INSERT INTO buy (buy_id, goods_id, house_id, buy_number) VALUES (?, ?, ?, ?);";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,buy);
            pst.setInt(2,id);
            pst.setInt(3,house);
            pst.setInt(4,num);
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
    public List<buy> loadAllbuy() throws BaseException{
        List<buy> result=new ArrayList<buy>();
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="SELECT *\n" +
                    "FROM buy\n" +
                    "ORDER BY buy_id";
            PreparedStatement pst=conn.prepareStatement(sql);
            ResultSet rs=pst.executeQuery();
            while (rs.next()){
                buy p=new buy();
                p.setBuyid(rs.getInt(1));
                p.setGoodsid(rs.getInt(2));
                p.setHouseid(rs.getInt(3));
                p.setBuynumber(rs.getInt(4));
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
    public List<buy> loadAllFindBuyId(int id) throws BaseException{
        List<buy> result=new ArrayList<buy>();
        Connection conn=null;
        try {
            boolean flag=false;
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="SELECT *\n" +
                    "FROM buy\n" +
                    "where buy_id=?" +
                    " order BY buy_id";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet rs=pst.executeQuery();
            while (rs.next()){
                flag=true;
                buy p=new buy();
                p.setBuyid(rs.getInt(1));
                p.setGoodsid(rs.getInt(2));
                p.setHouseid(rs.getInt(3));
                p.setBuynumber(rs.getInt(4));
                result.add(p);
            }
            rs.close();
            pst.close();
            conn.commit();
            if (!flag) return null;
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
    public List<buy> loadAllFindGoodId(int id) throws BaseException{
        List<buy> result=new ArrayList<buy>();
        Connection conn=null;
        try {
            boolean flag=false;
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="SELECT *\n" +
                    "FROM buy\n" +
                    "where goods_id=?" +
                    " order BY buy_id";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet rs=pst.executeQuery();
            while (rs.next()){
                flag=true;
                buy p=new buy();
                p.setBuyid(rs.getInt(1));
                p.setGoodsid(rs.getInt(2));
                p.setHouseid(rs.getInt(3));
                p.setBuynumber(rs.getInt(4));
                result.add(p);
            }
            rs.close();
            pst.close();
            conn.commit();
            if (!flag) return null;
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
    public List<buy> loadAllFindHouseId(int id) throws BaseException{
        List<buy> result=new ArrayList<buy>();
        Connection conn=null;
        try {
            boolean flag=false;
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql="SELECT *\n" +
                    "FROM buy\n" +
                    "where house_id=?" +
                    " order BY buy_id";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet rs=pst.executeQuery();
            while (rs.next()){
                flag=true;
                buy p=new buy();
                p.setBuyid(rs.getInt(1));
                p.setGoodsid(rs.getInt(2));
                p.setHouseid(rs.getInt(3));
                p.setBuynumber(rs.getInt(4));
                result.add(p);
            }
            rs.close();
            pst.close();
            conn.commit();
            if (!flag) return null;
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
