package cn.edu.zucc.distribution.ui;

import cn.edu.zucc.distribution.DistributionUtil;
import cn.edu.zucc.distribution.model.*;
import cn.edu.zucc.distribution.model.deliver_detail;
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

public class FrmDeliveryFind extends JDialog implements ActionListener {
    private JPanel workPane=new JPanel();
    private JPanel toolBar=new JPanel();
    private JPanel LowPane=new JPanel();
    private JLabel labelInputId =new JLabel("请输入所要查询得配送单编号：");
    private JTextField jtInputId=new JTextField(20);
    private JButton jbtFind=new JButton("查询");
    private JButton jbtFinish=new JButton("送达");

    List<deliver_detail> DeliveryDetailAll =null;
    private Object tblDeliveryDetailTitle[]=deliver_detail.tableTitles;
    private Object tblDeliveryDetailData[][];
    DefaultTableModel tabDeliveryDetailModel =new DefaultTableModel();
    private JTable dataTableDeliveryDetail =new JTable(tabDeliveryDetailModel);
    private void reloadDeliveryDetail(int delivery_id){
        try {
            DeliveryDetailAll = DistributionUtil.deliveryMannger.loadAllDeliverDetail(delivery_id);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return ;
        }
        tblDeliveryDetailData =new Object[DeliveryDetailAll.size()][deliver_detail.tableTitles.length];
        for (int i = 0; i< DeliveryDetailAll.size(); i++){
            for (int j=0;j<deliver_detail.tableTitles.length;j++){
                tblDeliveryDetailData[i][j]= DeliveryDetailAll.get(i).getCell(j);
            }
        }

        tabDeliveryDetailModel.setDataVector(tblDeliveryDetailData, tblDeliveryDetailTitle);
        this.dataTableDeliveryDetail.validate();
        this.dataTableDeliveryDetail.repaint();

    }

    List<delivery_information> AlldeliveryInformation =null;
    private Object deliveryInformationTitles[]=delivery_information.tableTitles;
    private Object tbldeliveryInformationData[][];
    DefaultTableModel tabdeliveryInformationModel =new DefaultTableModel();
    private JTable dataTabledeliveryInformation =new JTable(tabdeliveryInformationModel);

    private void reloaddeliveryInformation(){

        tbldeliveryInformationData =new Object[AlldeliveryInformation.size()][delivery_information.tableTitles.length];
        for (int i = 0; i< AlldeliveryInformation.size(); i++){
            for (int j=0;j<delivery_information.tableTitles.length;j++){
                tbldeliveryInformationData[i][j]= AlldeliveryInformation.get(i).getCell(j);
            }
        }

        tabdeliveryInformationModel.setDataVector(tbldeliveryInformationData, deliveryInformationTitles);
        this.dataTabledeliveryInformation.validate();
        this.dataTabledeliveryInformation.repaint();

    }

    public FrmDeliveryFind(Frame f, String s, boolean b){
        super(f, s, b);
        customer.currentLoginUser=new customer();
        customer.currentLoginUser.setUserid(0);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        toolBar.add(labelInputId);
        toolBar.add(jtInputId);
        toolBar.add(jbtFind);
        jbtFind.addActionListener(this);

        workPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.getContentPane().add(workPane, BorderLayout.CENTER);

        try {
            AlldeliveryInformation = DistributionUtil.deliveryMannger.loasAlldeliveryInformation();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return ;
        }

        this.getContentPane().add(new JScrollPane(this.dataTabledeliveryInformation), BorderLayout.CENTER);
        this.dataTabledeliveryInformation.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                int i= FrmDeliveryFind.this.dataTabledeliveryInformation.getSelectedRow();
                if(i<0) {
                    return;
                }
                FrmDeliveryFind.this.reloadDeliveryDetail(AlldeliveryInformation.get(i).getDeliveryid());
            }

        });
        this.getContentPane().add(new JScrollPane(this.dataTableDeliveryDetail), BorderLayout.EAST);

        this.reloaddeliveryInformation();
        this.reloadDeliveryDetail(0);

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
                if (jtInputId==null || "".equals(jtInputId.getText())) {
                    AlldeliveryInformation =DistributionUtil.deliveryMannger.loasAlldeliveryInformation();
                    this.reloaddeliveryInformation();
                    this.reloadDeliveryDetail(0);
                    throw new BusinessException("查询条件不能为空！");
                }
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                AlldeliveryInformation = DistributionUtil.deliveryMannger.FinddeliveryInformation(Integer.parseInt(jtInputId.getText()));
                if (AlldeliveryInformation ==null){
                    AlldeliveryInformation =DistributionUtil.deliveryMannger.loasAlldeliveryInformation();
                    this.reloaddeliveryInformation();
                    this.reloadDeliveryDetail(0);
                    throw new BaseException("配送单不存在");
                }
                this.reloaddeliveryInformation();
                this.reloadDeliveryDetail(0);
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            }

        }


    }
}
