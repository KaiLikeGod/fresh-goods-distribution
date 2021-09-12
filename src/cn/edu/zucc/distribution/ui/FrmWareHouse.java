package cn.edu.zucc.distribution.ui;

import cn.edu.zucc.distribution.DistributionUtil;
import cn.edu.zucc.distribution.model.*;
import cn.edu.zucc.distribution.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class FrmWareHouse extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JPanel LowPane=new JPanel();

    private JButton jbtaddWarehouse =new JButton("ÐÂ½¨²Ö¿â");
    private JButton jbtChangeWarehouse =new JButton("ÐÞ¸Ä²Ö¿âÐÅÏ¢");
    private JButton jbtDeleteWarehouse =new JButton("É¾³ý²Ö¿â");

    List<warehouses> allWarehouse =new ArrayList<warehouses>();
    private Object tblwarehouseTitle[]=warehouses.tableTitles;
    private Object tblWarehouseData[][];
    DefaultTableModel tabWarehouseModel =new DefaultTableModel();
    private JTable dataTableWarehouse =new JTable(tabWarehouseModel);
    private void reloadType(){
        try {
            allWarehouse = DistributionUtil.goodsManager.loadAllWarehouse();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "´íÎó",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblWarehouseData =new Object[allWarehouse.size()][warehouses.tableTitles.length];
        for (int i = 0; i< allWarehouse.size(); i++){
            for (int j=0;j<warehouses.tableTitles.length;j++){
                tblWarehouseData[i][j]= allWarehouse.get(i).getCell(j);
            }
        }
        tabWarehouseModel.setDataVector(tblWarehouseData, tblwarehouseTitle);
        this.dataTableWarehouse.validate();
        this.dataTableWarehouse.repaint();

    }
    public FrmWareHouse(Frame f, String s, boolean b) {
        super(f, s, b);
        this.getContentPane().add(new JScrollPane(this.dataTableWarehouse), BorderLayout.CENTER);
        this.reloadType();


        LowPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        LowPane.add(jbtaddWarehouse);
        jbtaddWarehouse.addActionListener(this);
        LowPane.add(jbtChangeWarehouse);
        jbtChangeWarehouse.addActionListener(this);
        LowPane.add(jbtDeleteWarehouse);
        jbtDeleteWarehouse.addActionListener(this);
        this.getContentPane().add(LowPane,BorderLayout.SOUTH);

        this.setSize(600, 300);

        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==this.jbtaddWarehouse){
            FrmAddWarehouse dlg= new FrmAddWarehouse(this,"ÐÂ½¨²Ö¿â",true);
            dlg.setVisible(true);
            this.reloadType();
        }else if (e.getSource()==this.jbtChangeWarehouse){
            int i= FrmWareHouse.this.dataTableWarehouse.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null, "ÇëÑ¡Ôñ²Ö¿â£¡", "´íÎó",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmChangeWarehouse dlg=new FrmChangeWarehouse(this,"ÐÞ¸ÄÀà±ð",true,this.allWarehouse.get(i));
            dlg.setVisible(true);
            this.reloadType();
        }else if (e.getSource()==this.jbtDeleteWarehouse){
            int i= FrmWareHouse.this.dataTableWarehouse.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null, "ÇëÑ¡Ôñ²Ö¿â", "´íÎó",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(JOptionPane.showConfirmDialog(this,"È·ÈÏÉ¾³ý¸Ã²Ö¿â£¿","È·ÈÏ",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                try {
                    DistributionUtil.goodsManager.DeleteHouse(allWarehouse.get(i).getHouseid());
                    JOptionPane.showMessageDialog(null, "É¾³ý³É¹¦£¡ ", "ÌáÊ¾" , JOptionPane.PLAIN_MESSAGE);
                    this.reloadType();
                } catch (BaseException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(),"´íÎó",JOptionPane.ERROR_MESSAGE);
                }

            }

        }


    }


}
