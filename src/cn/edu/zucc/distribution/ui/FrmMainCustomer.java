package cn.edu.zucc.distribution.ui;

import javax.swing.*;
import java.awt.*;
import cn.edu.zucc.distribution.DistributionUtil;
import cn.edu.zucc.distribution.model.*;
import cn.edu.zucc.distribution.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.awt.event.ActionListener;

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

    private JPanel statusBar = new JPanel();
    ImageIcon icon1=new ImageIcon("src/logo.jpg" );

    public FrmMainCustomer(){
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("生鲜智能配送系统 V1.0.0");
        this.menu_user.add(this.menu_change_pwd);
        this.menu_user.add(this.menu_change_address);
        this.menu_user.add(this.menu_change_lgt);
        this.menu_user.add(this.menu_change_lat);

        this.menu_sousuo.add(menu_orders_find);
        this.menu_sousuo.add(menu_warehouse_find);

        this.menu_buy.add(menu_all_order);
        this.menu_buy.add(menu_buy_car);
        this.menu_buy.add(menu_order);

        menubar.add(menu_user);
        menubar.add(menu_sousuo);
        menubar.add(menu_buy);
        this.setJMenuBar(menubar);

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
    }
}
