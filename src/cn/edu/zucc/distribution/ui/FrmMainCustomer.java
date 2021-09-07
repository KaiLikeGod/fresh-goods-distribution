package cn.edu.zucc.distribution.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import cn.edu.zucc.distribution.DistributionUtil;
import cn.edu.zucc.distribution.model.*;
import cn.edu.zucc.distribution.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmMainCustomer extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar();
    private JMenu menu_user=new JMenu("用户管理");
    private JMenuItem menu_change_pwd=new JMenuItem("修改密码");
    private JMenuItem menu_change_address=new JMenuItem("修改地址");
    private JMenuItem menu_change_lgt=new JMenuItem("修改经度");
    private JMenuItem menu_change_lat=new JMenuItem("修改纬度");

    private JMenu menu_sousuo=new JMenu("信息查询");
    private JMenuItem menu_orders_find=new JMenuItem("商品信息查询");
    private JMenuItem menu_warehouse_find =new JMenuItem("仓库信息查询");

    private JMenu menu_buy=new JMenu("购买");
    private JMenuItem menu_all_order=new JMenuItem("全部商品");
    private JMenuItem menu_buy_car=new JMenuItem("购物车");
    private JMenuItem menu_order=new JMenuItem("查看历史订单");

    DefaultTableModel tabGoodsModel=new DefaultTableModel();
    private JTable dataTableGoods=new JTable(tabGoodsModel);

    private JPanel statusBar = new JPanel();
    ImageIcon icon1=new ImageIcon("src/logo.jpg" );

    List<goods> allgoods=null;
    private Object tblGoodsTitle[]=goods.tableTitlesCustomer;
    private Object tblGoodData[][];
    private void reloadgoodsinform(){
        try {
            allgoods= DistributionUtil.goodsManager.loadAll();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblGoodData=new Object[allgoods.size()][goods.tableTitlesCustomer.length];
        for (int i=0;i<allgoods.size();i++){
            for (int j=0;j<goods.tableTitlesCustomer.length;j++){
                tblGoodData[i][j]=allgoods.get(i).getCell(j);
            }
        }
        tabGoodsModel.setDataVector(tblGoodData,tblGoodsTitle);
        this.dataTableGoods.validate();
        this.dataTableGoods.repaint();

    }

    public FrmMainCustomer(){
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("生鲜智能配送系统 V1.0.0");
        this.menu_user.add(this.menu_change_pwd);
        this.menu_change_pwd.addActionListener(this);
        this.menu_user.add(this.menu_change_address);
        this.menu_user.add(this.menu_change_lgt);
        this.menu_user.add(this.menu_change_lat);
        this.menu_change_address.addActionListener(this);
        this.menu_change_lgt.addActionListener(this);
        this.menu_change_lat.addActionListener(this);

        this.menu_sousuo.add(menu_orders_find);
        this.menu_sousuo.add(menu_warehouse_find);
        this.menu_orders_find.addActionListener(this);
        this.menu_warehouse_find.addActionListener(this);

        this.menu_buy.add(menu_all_order);
        this.menu_buy.add(menu_buy_car);
        this.menu_buy.add(menu_order);
        this.menu_all_order.addActionListener(this);
        this.menu_buy_car.addActionListener(this);
        this.menu_order.addActionListener(this);

        menubar.add(menu_user);
        menubar.add(menu_sousuo);
        menubar.add(menu_buy);
        this.setJMenuBar(menubar);

        this.getContentPane().add(new JScrollPane(this.dataTableGoods), BorderLayout.CENTER);
//        this.dataTablePlan.addMouseListener(new MouseAdapter (){
//
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                int i= FrmMain_old.this.dataTablePlan.getSelectedRow();
//                if(i<0) {
//                    return;
//                }
//                FrmMainCustomer.this.reloadPlanStepTabel(i);
//            }
//
//        });

        this.reloadgoodsinform();

        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label=new JLabel("尊敬的 " + customer.currentLoginUser.getUsername()+", 您好! ");//修改成   您好！+登陆用户名
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });


        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==this.menu_change_pwd){
            FrmChangePwd dlg=new FrmChangePwd(this,"修改密码",true,"customer");
            dlg.setVisible(true);
        }
        else if (e.getSource()==this.menu_change_address){
            FrmChangeAddress dlg=new FrmChangeAddress(this,"修改地址",true);
            dlg.setVisible(true);
        }else if (e.getSource()==this.menu_change_lgt){
            FrmChangeLgt dlg=new FrmChangeLgt(this,"修改经度",true);
            dlg.setVisible(true);
        }else if (e.getSource()==this.menu_change_lat){
            FrmChangeLat dlg=new FrmChangeLat(this,"修改纬度",true);
            dlg.setVisible(true);
        }
    }
}
