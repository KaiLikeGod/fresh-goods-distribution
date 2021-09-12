package cn.edu.zucc.distribution.itf;

import cn.edu.zucc.distribution.model.*;
import cn.edu.zucc.distribution.model.warehouses;
import cn.edu.zucc.distribution.util.BaseException;

import java.util.List;

public interface IDeliveryMannger {
    public List<car> loadAllCar() throws BaseException;
    public void createNewCar(String num,float weight,float v,String phone,boolean cold) throws BaseException;
    public void changeNewCar(int id,String phone,boolean cold) throws BaseException;
    public void DeleteCar(int id) throws BaseException;
    public void deliveryOK(List<orders> o,float w,float v,car c)throws BaseException;

    public List<delivery_information> loasAlldeliveryInformation() throws BaseException;
    public List<deliver_detail> loadAllDeliverDetail(int id) throws BaseException;
    public List<delivery_information> FinddeliveryInformation(int id) throws BaseException;
    public void FinishDelivery(delivery_information id) throws BaseException;
    public List<delivery_information> loadAlldeliveryInformation() throws BaseException;
    public List<delivery_information> FinddeliveryInformations(int id) throws BaseException;
}
