package cn.edu.zucc.distribution.itf;

import cn.edu.zucc.distribution.util.BaseException;
import cn.edu.zucc.distribution.model.*;
import cn.edu.zucc.distribution.util.*;

import java.util.List;

public interface IOrderMannger {
    public void CreateOrder(String time) throws BaseException;
    public void DeleteBuyCar(order_information car) throws BaseException;
    public List<orders> loadAllOrders() throws BaseException;
    public List<order_information> loadAllorder_information(int orderid) throws BaseException;
    public void OrderReturn(int orderid) throws BaseException;
    public FiveTuple<Integer,Float,Float,Float,Boolean> customerBuyFind() throws BaseException;
    public List<orders> loadAllOrdersAdmin() throws BaseException;
    public List<orders> loadAllOrdersById(int id) throws BaseException;
    public List<orders> loadAllOrdersNotDelivery() throws BaseException;
    public int getHouseId(int orderid) throws BaseException;


}
