package cn.edu.zucc.distribution.ui;

import cn.edu.zucc.distribution.DistributionUtil;
import cn.edu.zucc.distribution.model.admin;
import cn.edu.zucc.distribution.model.customer;
import cn.edu.zucc.distribution.model.*;
import cn.edu.zucc.distribution.util.BaseException;
import cn.edu.zucc.distribution.util.BusinessException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class FrmDemandGoods extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JPanel LowPane=new JPanel();

    private JLabel labelType=new JLabel("查询类型");
    private JComboBox<String> labelDemandType=new JComboBox<String>();
    private JTextField labelDemandDate=new JTextField(20);
    private JButton jbtDemand=new JButton("查询");
    private JButton jbtaddBuyCar=new JButton("加入购物车");

    List<goods> allgoods_s=new ArrayList<goods>();
    private Object tblGoodsTitle[]=goods.tableTitlesCustomer;
    private Object tblGoodData[][];
    DefaultTableModel tabGoodsModel=new DefaultTableModel();
    private JTable dataTableGoods=new JTable(tabGoodsModel);
    private void reloadgoodsinform(){
        tblGoodData=new Object[allgoods_s.size()][goods.tableTitlesCustomer.length];
        for (int i=0;i<allgoods_s.size();i++){
            for (int j=0;j<goods.tableTitlesCustomer.length;j++){
                tblGoodData[i][j]=allgoods_s.get(i).getCell(j);
            }
        }
        tabGoodsModel.setDataVector(tblGoodData,tblGoodsTitle);
        this.dataTableGoods.validate();
        this.dataTableGoods.repaint();

    }
    String type_s;
    public FrmDemandGoods(Frame f, String s, boolean b, String type_s) {
        super(f, s, b);
        this.type_s=type_s;
        labelDemandType.addItem("商品编号");       // 创建4个下拉选项
        labelDemandType.addItem("仓库编号");
        labelDemandType.addItem("商品类别");
        labelDemandType.addItem("商品名称");
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        toolBar.add(labelType);
        toolBar.add(labelDemandType);
        toolBar.add(labelDemandDate);
        toolBar.add(jbtDemand);
        jbtDemand.addActionListener(this);

        try {
            allgoods_s= DistributionUtil.goodsManager.loadAll();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.getContentPane().add(new JScrollPane(this.dataTableGoods), BorderLayout.CENTER);
        this.reloadgoodsinform();

        if (type_s=="customer"){
            LowPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            LowPane.add(jbtaddBuyCar);
            jbtaddBuyCar.addActionListener(this);

            this.getContentPane().add(LowPane,BorderLayout.SOUTH);
        }

        this.setSize(1300, 500);

        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==jbtDemand){
            try {
                if (labelDemandDate.getText()==null || "".equals(labelDemandDate.getText()))
                    throw new BusinessException("查询内容不得为空！");
                if (labelDemandType.getSelectedItem().toString().equals("商品编号")){
                    try {
                        allgoods_s=DistributionUtil.goodsManager.FindGoodsId(Integer.parseInt(labelDemandDate.getText()));
                        if  (allgoods_s==null) {
                            allgoods_s=new ArrayList<goods>();
                            this.reloadgoodsinform();
                            throw new BaseException("未找到商品");
                        }
                        else
                            this.reloadgoodsinform();
                    } catch (BaseException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                    }
                }else if (labelDemandType.getSelectedItem().toString().equals("仓库编号")){
                    try {
                        allgoods_s=DistributionUtil.goodsManager.FindWarehouseIdGoods(Integer.parseInt(labelDemandDate.getText()));
                        if  (allgoods_s==null) {
                            allgoods_s=new ArrayList<goods>();
                            this.reloadgoodsinform();
                            throw new BaseException("未找到商品");
                        }
                        else
                            this.reloadgoodsinform();
                    } catch (BaseException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                    }
                }else if (labelDemandType.getSelectedItem().toString().equals("商品类别")){
                    try {
                        allgoods_s=DistributionUtil.goodsManager.FindsGoodsType(Integer.parseInt(labelDemandDate.getText()));
                        if  (allgoods_s==null) {
                            allgoods_s=new ArrayList<goods>();
                            this.reloadgoodsinform();
                            throw new BaseException("未找到商品");
                        }
                        else
                            this.reloadgoodsinform();
                    } catch (BaseException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                    }
                }else if (labelDemandType.getSelectedItem().toString().equals("商品名称")){
                    try {
                        allgoods_s=DistributionUtil.goodsManager.FindGoodsName(labelDemandDate.getText());
                        if  (allgoods_s==null) {
                            allgoods_s=new ArrayList<goods>();
                            this.reloadgoodsinform();
                            throw new BaseException("未找到商品");
                        }
                        else
                            this.reloadgoodsinform();
                    } catch (BaseException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                    }
                }

            } catch (BusinessException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            }
        }else if (e.getSource()==this.jbtaddBuyCar){
            int i= FrmDemandGoods.this.dataTableGoods.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null, "请选择商品", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmAddToCart dig=new FrmAddToCart(this,"选择商品数量",true,this.allgoods_s.get(i));
            dig.setVisible(true);
        }


    }


}
