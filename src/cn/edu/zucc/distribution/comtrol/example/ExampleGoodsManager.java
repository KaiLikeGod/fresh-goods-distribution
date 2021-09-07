package cn.edu.zucc.distribution.comtrol.example;


import cn.edu.zucc.distribution.itf.IGoodsManager;
import cn.edu.zucc.distribution.model.BeanPlan;
import cn.edu.zucc.distribution.model.BeanUser;
import cn.edu.zucc.distribution.model.goods;
import cn.edu.zucc.distribution.util.BaseException;
import cn.edu.zucc.distribution.util.DBUtil;
import cn.edu.zucc.distribution.util.DbException;

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
                    "WHERE inventory_number>0\n" +
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
}
