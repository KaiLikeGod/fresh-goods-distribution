package cn.edu.zucc.distribution.ui;

import cn.edu.zucc.distribution.DistributionUtil;
import cn.edu.zucc.distribution.model.customer;
import cn.edu.zucc.distribution.model.order_information;
import cn.edu.zucc.distribution.model.orders;
import cn.edu.zucc.distribution.util.BaseException;
import cn.edu.zucc.distribution.util.BusinessException;
import cn.edu.zucc.distribution.util.FiveTuple;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FrmCustomerOrderFind extends JDialog implements ActionListener {
    private JPanel workPane=new JPanel();
    private JPanel toolBar=new JPanel();
    private JPanel LowPane=new JPanel();

    private JLabel labelInputId =new JLabel("请输入所要查询得订单编号：");
    private JTextField jtInputId=new JTextField(20);
    private JButton jbtFind=new JButton("查询");

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
    private Object OrdersTitles[]=orders.tableTitlesAdmin;
    private Object tblOrdersData[][];
    DefaultTableModel tabOrdersModel =new DefaultTableModel();
    private JTable dataTableorder =new JTable(tabOrdersModel);

    private void reloadOrders(){

        tblOrdersData =new Object[AllOrders.size()][orders.tableTitlesAdmin.length];
        for (int i = 0; i< AllOrders.size(); i++){
            tblOrdersData[i][0]= AllOrders.get(i).getCell(0);
            tblOrdersData[i][1]= AllOrders.get(i).getCell(1);
            tblOrdersData[i][2]= AllOrders.get(i).getCell(2);
            tblOrdersData[i][3]= AllOrders.get(i).getCell(3);
            tblOrdersData[i][4]= AllOrders.get(i).getCell(4);
            tblOrdersData[i][5]= AllOrders.get(i).getCell(5);
            tblOrdersData[i][6]= AllOrders.get(i).getCell(7);
        }

        tabOrdersModel.setDataVector(tblOrdersData, OrdersTitles);
        this.dataTableorder.validate();
        this.dataTableorder.repaint();

    }

    public FrmCustomerOrderFind(Frame f, String s, boolean b){
        super(f, s, b);
        customer.currentLoginUser=new customer();
        customer.currentLoginUser.setUserid(0);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        toolBar.add(labelInputId);
        toolBar.add(jtInputId);
        toolBar.add(jbtFind);
        jbtFind.addActionListener(this);

        workPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.getContentPane().add(workPane, BorderLayout.CENTER);

        try {
            AllOrders = DistributionUtil.orderMannger.loadAllOrdersAdmin();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return ;
        }

        this.getContentPane().add(new JScrollPane(this.dataTableorder), BorderLayout.CENTER);
        this.dataTableorder.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                int i= FrmCustomerOrderFind.this.dataTableorder.getSelectedRow();
                if(i<0) {
                    return;
                }
                FrmCustomerOrderFind.this.reloadorderInformation(AllOrders.get(i).getOrderid());
            }

        });
        this.getContentPane().add(new JScrollPane(this.dataTableOrderInformation), BorderLayout.EAST);

        this.reloadOrders();

        LowPane.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.getContentPane().add(LowPane, BorderLayout.SOUTH);
        this.setSize(1300, 500);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==this.jbtFind){
            try {
                if (jtInputId==null || "".equals(jtInputId.getText()))
                    throw new BusinessException("查询条件不能为空！");
            } catch (BusinessException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            }
            try {
                AllOrders = DistributionUtil.orderMannger.loadAllOrdersById(Integer.parseInt(jtInputId.getText()));
                if (AllOrders==null){
                    AllOrders=new ArrayList<orders>();
                    this.reloadOrders();
                    this.reloadorderInformation(0);
                    throw new BaseException("订单不存在");
                }
                this.reloadOrders();
                this.reloadorderInformation(0);
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            }

        }


    }
}
