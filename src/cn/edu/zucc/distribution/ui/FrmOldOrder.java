package cn.edu.zucc.distribution.ui;

import cn.edu.zucc.distribution.DistributionUtil;
import cn.edu.zucc.distribution.model.*;
import cn.edu.zucc.distribution.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FrmOldOrder extends JDialog implements ActionListener {
    private JPanel workPane=new JPanel();
    private JPanel toolBar=new JPanel();
    private JPanel LowPane=new JPanel();
    private JLabel labelUser=new JLabel("当前用户："+customer.currentLoginUser.getUsername());


    List<order_information> OrderInformationAll =null;
    private Object tblOrderInformationTitle[]=order_information.tableTitlesCustomer;
    private Object tblOrderInformationData[][];
    DefaultTableModel tabOrderInformationModel =new DefaultTableModel();
    private JTable dataTableOrderInformation =new JTable(tabOrderInformationModel);
    private void reloadorderInformation(int orderid){
        try {
            OrderInformationAll = DistributionUtil.orderMannger.loadAllorder_information(orderid);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return ;
        }
        tblOrderInformationData =new Object[OrderInformationAll.size()][order_information.tableTitlesCustomer.length];
        for (int i = 0; i< OrderInformationAll.size(); i++){
            tblOrderInformationData[i][0]= OrderInformationAll.get(i).getCell(1);
            tblOrderInformationData[i][1]= OrderInformationAll.get(i).getCell(8);
            tblOrderInformationData[i][2]= OrderInformationAll.get(i).getCell(3);
            tblOrderInformationData[i][3]= OrderInformationAll.get(i).getCell(5);
            tblOrderInformationData[i][4]= OrderInformationAll.get(i).getCell(7);
        }

        tabOrderInformationModel.setDataVector(tblOrderInformationData, tblOrderInformationTitle);
        this.dataTableOrderInformation.validate();
        this.dataTableOrderInformation.repaint();

    }

    List<orders> AllOrders =null;
    private Object OrdersTitles[]=orders.tableTitlesCustomer;
    private Object tblOrdersData[][];
    DefaultTableModel tabOrdersModel =new DefaultTableModel();
    private JTable dataTableorder =new JTable(tabOrdersModel);

    private JButton jbtReturn=new JButton("退货");
    private void reloadOrders(){
        try {
            AllOrders = DistributionUtil.orderMannger.loadAllOrders();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return ;
        }
        tblOrdersData =new Object[AllOrders.size()][orders.tableTitlesCustomer.length];
        for (int i = 0; i< AllOrders.size(); i++){
            tblOrdersData[i][0]= AllOrders.get(i).getCell(0);
            tblOrdersData[i][1]= AllOrders.get(i).getCell(2);
            tblOrdersData[i][2]= AllOrders.get(i).getCell(3);
            tblOrdersData[i][3]= AllOrders.get(i).getCell(4);
            tblOrdersData[i][4]= AllOrders.get(i).getCell(5);
            tblOrdersData[i][5]= AllOrders.get(i).getCell(7);
        }

        tabOrdersModel.setDataVector(tblOrdersData, OrdersTitles);
        this.dataTableorder.validate();
        this.dataTableorder.repaint();

    }

    public FrmOldOrder(Frame f, String s, boolean b){
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        toolBar.add(labelUser);

        workPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.getContentPane().add(workPane, BorderLayout.CENTER);

        this.getContentPane().add(new JScrollPane(this.dataTableorder), BorderLayout.CENTER);
        this.dataTableorder.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                int i= FrmOldOrder.this.dataTableorder.getSelectedRow();
                if(i<0) {
                    return;
                }
                FrmOldOrder.this.reloadorderInformation(AllOrders.get(i).getOrderid());
            }

        });
        this.getContentPane().add(new JScrollPane(this.dataTableOrderInformation), BorderLayout.EAST);

        this.reloadOrders();

        LowPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.getContentPane().add(LowPane, BorderLayout.SOUTH);
        LowPane.add(jbtReturn);
        jbtReturn.addActionListener(this);
        this.setSize(1300, 500);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==this.jbtReturn){
            int i= FrmOldOrder.this.dataTableorder.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null, "请选择订单", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(JOptionPane.showConfirmDialog(this,"确定退货？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                try {
                    //(new ParkingManager()).delete(p.getParkNo(),);
                    DistributionUtil.orderMannger.OrderReturn(AllOrders.get(i).getOrderid());
                    JOptionPane.showMessageDialog(null, "退货成功！ ", "提示" , JOptionPane.PLAIN_MESSAGE);
                    //ParkingManager.delete(p.getParkNo());
                    this.reloadOrders();
                } catch (BaseException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                }

            }
        }


    }
}
