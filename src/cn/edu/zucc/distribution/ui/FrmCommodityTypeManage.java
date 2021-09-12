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

public class FrmCommodityTypeManage extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JPanel LowPane=new JPanel();

    private JButton jbtaddType =new JButton("添加类别");
    private JButton jbtChangeType =new JButton("修改类别");
    private JButton jbtDeleteType =new JButton("删除类别");

    List<commodity_type> allType =new ArrayList<commodity_type>();
    private Object tblTypeTitle[]=commodity_type.tableTitles;
    private Object tblTypeData[][];
    DefaultTableModel tabTypeModel =new DefaultTableModel();
    private JTable dataTableType =new JTable(tabTypeModel);
    private void reloadType(){
        try {
            allType = DistributionUtil.goodsManager.loadAllType();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblTypeData =new Object[allType.size()][commodity_type.tableTitles.length];
        for (int i = 0; i< allType.size(); i++){
            for (int j=0;j<commodity_type.tableTitles.length;j++){
                tblTypeData[i][j]= allType.get(i).getCell(j);
            }
        }
        tabTypeModel.setDataVector(tblTypeData, tblTypeTitle);
        this.dataTableType.validate();
        this.dataTableType.repaint();

    }
    public FrmCommodityTypeManage(Frame f, String s, boolean b) {
        super(f, s, b);



        this.getContentPane().add(new JScrollPane(this.dataTableType), BorderLayout.CENTER);
        this.reloadType();


        LowPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        LowPane.add(jbtaddType);
        jbtaddType.addActionListener(this);
        LowPane.add(jbtChangeType);
        jbtChangeType.addActionListener(this);
        LowPane.add(jbtDeleteType);
        jbtDeleteType.addActionListener(this);
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
        if (e.getSource()==this.jbtaddType){
            FrmAddType dlg= new FrmAddType(this,"新增类别",true);
            dlg.setVisible(true);
            this.reloadType();
        }else if (e.getSource()==this.jbtChangeType){
            int i= FrmCommodityTypeManage.this.dataTableType.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null, "请选择类别！", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmChangeType dlg=new FrmChangeType(this,"修改类别",true,this.allType.get(i));
            dlg.setVisible(true);
            this.reloadType();
        }else if (e.getSource()==this.jbtDeleteType){
            int i= FrmCommodityTypeManage.this.dataTableType.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null, "请选择类别", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(JOptionPane.showConfirmDialog(this,"确认删除该类别？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                try {
                    DistributionUtil.goodsManager.DeleteType(allType.get(i).getTypeid());
                    JOptionPane.showMessageDialog(null, "删除成功！ ", "提示" , JOptionPane.PLAIN_MESSAGE);
                    this.reloadType();
                } catch (BaseException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                }

            }

        }


    }


}
