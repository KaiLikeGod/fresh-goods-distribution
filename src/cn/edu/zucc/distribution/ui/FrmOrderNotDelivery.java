package cn.edu.zucc.distribution.ui;

import cn.edu.zucc.distribution.DistributionUtil;
import cn.edu.zucc.distribution.model.*;
import cn.edu.zucc.distribution.util.BaseException;
import cn.edu.zucc.distribution.util.BusinessException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class FrmOrderNotDelivery extends JDialog implements ActionListener {
    private JPanel workPane=new JPanel();
    private JPanel toolBar=new JPanel();
    private JPanel LowPane=new JPanel();

    List<orders> deliveryList=new ArrayList<orders>();
    private JButton jbtAddList=new JButton("选择");
    private JButton jbtDeleteList=new JButton("取消选择");
    private JButton jbtDelivery=new JButton("配送");
    private JLabel labelTips=new JLabel("当前已选择订单总重 0 ，总体积 0");
    float w=0;
    float v=0;

    List<car> CarAll =new ArrayList<car>();
    private Object tblCarTitle[]=car.tableTitles;
    private Object tblCarData[][];
    DefaultTableModel tabCarModel =new DefaultTableModel();
    private JTable dataTableCar =new JTable(tabCarModel);
    private void reloadCar(){
        try {
            CarAll = DistributionUtil.deliveryMannger.loadAllCar();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return ;
        }
        tblCarData =new Object[CarAll.size()][car.tableTitles.length];
        for (int i = 0; i< CarAll.size(); i++){
            for (int j=0;j<car.tableTitles.length;j++)
                tblCarData[i][j]= CarAll.get(i).getCell(j);
        }

        tabCarModel.setDataVector(tblCarData, tblCarTitle);
        this.dataTableCar.validate();
        this.dataTableCar.repaint();

    }

    List<orders> AllOrders =null;
    private Object OrdersTitles[]=orders.tableTitlesdelivery;
    private Object tblOrdersData[][];
    DefaultTableModel tabOrdersModel =new DefaultTableModel();
    private JTable dataTableorder =new JTable(tabOrdersModel);

    private void reloadOrders(){

        tblOrdersData =new Object[AllOrders.size()][orders.tableTitlesdelivery.length];
        for (int i = 0; i< AllOrders.size(); i++){
            tblOrdersData[i][0]= AllOrders.get(i).getCell(0);
            tblOrdersData[i][1]= AllOrders.get(i).getCell(1);
            tblOrdersData[i][2]= AllOrders.get(i).getCell(2);
            tblOrdersData[i][3]= AllOrders.get(i).getCell(3);
            tblOrdersData[i][4]= AllOrders.get(i).getCell(4);
            tblOrdersData[i][5]= AllOrders.get(i).getCell(5);
            tblOrdersData[i][6]= AllOrders.get(i).getCell(6);
            tblOrdersData[i][7]= AllOrders.get(i).getCell(7);

        }

        tabOrdersModel.setDataVector(tblOrdersData, OrdersTitles);
        this.dataTableorder.validate();
        this.dataTableorder.repaint();

    }

    public FrmOrderNotDelivery(Frame f, String s, boolean b){
        super(f, s, b);
        customer.currentLoginUser=new customer();
        customer.currentLoginUser.setUserid(0);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.getContentPane().add(toolBar, BorderLayout.NORTH);

        workPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.getContentPane().add(workPane, BorderLayout.CENTER);

        try {
            AllOrders = DistributionUtil.orderMannger.loadAllOrdersNotDelivery();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return ;
        }

        this.getContentPane().add(new JScrollPane(this.dataTableorder), BorderLayout.CENTER);
        this.getContentPane().add(new JScrollPane(this.dataTableCar), BorderLayout.EAST);
        this.reloadCar();
        this.reloadOrders();

        LowPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.getContentPane().add(LowPane, BorderLayout.SOUTH);
        LowPane.add(labelTips);
        LowPane.add(jbtAddList);
        LowPane.add(jbtDeleteList);
        LowPane.add(jbtDelivery);
        jbtAddList.addActionListener(this);
        jbtDeleteList.addActionListener(this);
        jbtDelivery.addActionListener(this);

        this.setSize(1500, 700);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==this.jbtAddList){
            int i= FrmOrderNotDelivery.this.dataTableorder.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null, "请选择订单", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (deliveryList.size()>0){
                for (int j=0;j<deliveryList.size();j++){
                    if (deliveryList.get(j).getOrderid()==this.AllOrders.get(i).getOrderid()){
                        JOptionPane.showMessageDialog(null, "该订单已选择！", "错误",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    try {
                        if (DistributionUtil.orderMannger.getHouseId(this.AllOrders.get(i).getOrderid())!=DistributionUtil.orderMannger.getHouseId(deliveryList.get(j).getOrderid())){
                            throw new BusinessException("该订单于已选择订单不在同一仓库，无法共同配送！");
                        }
                    } catch (BaseException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

            }
            deliveryList.add(this.AllOrders.get(i));
            w=w+this.AllOrders.get(i).getOrderweight();
            v=v+this.AllOrders.get(i).getOrderv();
            labelTips.setText("当前已选择订单总重 "+w+" ，总体积 "+v);
            JOptionPane.showMessageDialog(null, "添加成功！ ", "提示" , JOptionPane.PLAIN_MESSAGE);
        }
        else if (e.getSource()==this.jbtDeleteList){
            int i= FrmOrderNotDelivery.this.dataTableorder.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null, "请选择订单", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (deliveryList.size()>0){
                boolean flag=false;
                for (int j=0;j<deliveryList.size();j++){
                    if (deliveryList.get(j).getOrderid()==this.AllOrders.get(i).getOrderid()){
                        flag=true;
                        break;
                    }
                }
                if (!flag) {
                    JOptionPane.showMessageDialog(null, "订单未选择！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                deliveryList.remove(this.AllOrders.get(i));
                JOptionPane.showMessageDialog(null, "取消选择成功！ ", "提示" , JOptionPane.PLAIN_MESSAGE);
                w=w-this.AllOrders.get(i).getOrderweight();
                v=v-this.AllOrders.get(i).getOrderv();
                labelTips.setText("当前已选择订单总重 "+w+" ，总体积 "+v);
                return;
            }
            JOptionPane.showMessageDialog(null, "无选择的订单", "错误",JOptionPane.ERROR_MESSAGE);
        }
        else if (e.getSource()==this.jbtDelivery){
            if (deliveryList.size()==0) {
                JOptionPane.showMessageDialog(null, "请选择订单", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            int j= FrmOrderNotDelivery.this.dataTableCar.getSelectedRow();
            if(j<0) {
                JOptionPane.showMessageDialog(null, "请选择配送车辆", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                DistributionUtil.deliveryMannger.deliveryOK(deliveryList,w,v,this.CarAll.get(j));
                labelTips.setText("当前已选择订单总重 0 ，总体积 0");
                float w=0;
                float v=0;
                List<car> CarAll =new ArrayList<car>();
                AllOrders = DistributionUtil.orderMannger.loadAllOrdersNotDelivery();
                this.reloadCar();
                this.reloadOrders();
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            }
        }

    }
}
