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

public class FrmCar extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JPanel LowPane=new JPanel();

    private JButton jbtaddCar =new JButton("添加车辆");
    private JButton jbtChangeCar =new JButton("修改车辆信息");
    private JButton jbtDeleteCar =new JButton("删除车辆");

    List<car> allCar =new ArrayList<car>();
    private Object tblCarTitle[]=car.tableTitles;
    private Object tblCarData[][];
    DefaultTableModel tabCarModel =new DefaultTableModel();
    private JTable dataTableCar =new JTable(tabCarModel);
    private void reloadCar(){
        try {
            allCar = DistributionUtil.deliveryMannger.loadAllCar();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblCarData =new Object[allCar.size()][car.tableTitles.length];
        for (int i = 0; i< allCar.size(); i++){
            for (int j=0;j<car.tableTitles.length;j++){
                tblCarData[i][j]= allCar.get(i).getCell(j);
            }
        }
        tabCarModel.setDataVector(tblCarData, tblCarTitle);
        this.dataTableCar.validate();
        this.dataTableCar.repaint();

    }
    public FrmCar(Frame f, String s, boolean b) {
        super(f, s, b);
        this.getContentPane().add(new JScrollPane(this.dataTableCar), BorderLayout.CENTER);
        this.reloadCar();


        LowPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        LowPane.add(jbtaddCar);
        jbtaddCar.addActionListener(this);
        LowPane.add(jbtChangeCar);
        jbtChangeCar.addActionListener(this);
        LowPane.add(jbtDeleteCar);
        jbtDeleteCar.addActionListener(this);
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
        if (e.getSource()==this.jbtaddCar){
            FrmAddCar dlg= new FrmAddCar(this,"添加车辆",true);
            dlg.setVisible(true);
            this.reloadCar();
        }else if (e.getSource()==this.jbtChangeCar){
            int i= FrmCar.this.dataTableCar.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null, "请选择车辆！", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmChangeCar dlg=new FrmChangeCar(this,"修改车辆信息",true,this.allCar.get(i));
            dlg.setVisible(true);
            this.reloadCar();
        }else if (e.getSource()==this.jbtDeleteCar){
            int i= FrmCar.this.dataTableCar.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null, "请选择车辆", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(JOptionPane.showConfirmDialog(this,"确认删除该车辆？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                try {
                    DistributionUtil.deliveryMannger.DeleteCar(allCar.get(i).getCarid());
                    JOptionPane.showMessageDialog(null, "删除成功！ ", "提示" , JOptionPane.PLAIN_MESSAGE);
                    this.reloadCar();
                } catch (BaseException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                }

            }

        }


    }


}
