package cn.edu.zucc.distribution.itf;

import cn.edu.zucc.distribution.model.*;
import cn.edu.zucc.distribution.util.BaseException;

import java.util.List;

public interface IGoodsManager{
    public List<goods> loadAll() throws BaseException;
    public List<goods> FindGoodsId(int goodId) throws BaseException;
    public List<goods> FindWarehouseIdGoods(int warehouseid) throws BaseException;
    public List<goods> FindsGoodsType(int Typeid) throws BaseException;
    public List<goods> FindGoodsName(String Goodsname) throws BaseException;
    public void AddToBuyCar(int now,goods good) throws BaseException;
    public List<order_information> LoadBuyCar() throws BaseException;
    public List<commodity_type> loadAllType() throws BaseException;
    public void createNewType(String name,String describle) throws BaseException;
    public void changeType(int id,String name,String describle) throws BaseException;
    public void DeleteType(int id) throws BaseException;

    public List<warehouses> loadAllWarehouse() throws BaseException;
    public void createNewWarehouse(String name,String address,String phone,float lgt,float lat) throws BaseException;
    public void changeNewWarehouse(int id, String name,String address,String phone,float lgt,float lat) throws BaseException;
    public void DeleteHouse(int id) throws BaseException;

    public void CreateNewGood(int house,int type,String name,float price,float size,float weight,boolean cold,int number) throws BaseException;
    public void changeGood(int id,int house,int type,String name,float price,float size,float weight,boolean cold) throws BaseException;
    public void DeleteGoods(int id) throws BaseException;
    public void buyGoods(int id,int num,int house) throws BaseException;

    public List<buy> loadAllbuy() throws BaseException;
    public List<buy> loadAllFindBuyId(int id) throws BaseException;
    public List<buy> loadAllFindGoodId(int id) throws BaseException;
    public List<buy> loadAllFindHouseId(int id) throws BaseException;

}
