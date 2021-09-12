package cn.edu.zucc.distribution.ui;

import cn.edu.zucc.distribution.DistributionUtil;
import cn.edu.zucc.distribution.model.*;
import cn.edu.zucc.distribution.model.goods;
import cn.edu.zucc.distribution.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class FrmMainAdmin extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private JMenuBar menubar=new JMenuBar();
    private JMenu menu_user=new JMenu("用户管理");
    private JMenuItem menu_customer_manage =new JMenuItem("客户信息管理");
    private JMenuItem menu_change_pwd =new JMenuItem("修改密码");

    private JMenu menu_ziyuan_manage =new JMenu("资源管理");
    private JMenuItem menu_GoodsType_manage =new JMenuItem("生鲜类别管理");
    private JMenuItem menu_Goods_manage=new JMenuItem("商品管理");
    private JMenuItem menu_house_manage=new JMenuItem("仓库管理");
    private JMenuItem menu_Car_manage=new JMenuItem("车辆管理");


    private JMenu menu_Order_Done =new JMenu("订单处理");
    private JMenuItem menu_Not_deliery_Orders =new JMenuItem("未配送订单");
    private JMenuItem menu_Orders_get =new JMenuItem("订单送达");

    private JMenu menu_Find =new JMenu("信息查询");
    private JMenuItem menu_buy_Find =new JMenuItem("采购单查询");
    private JMenuItem menu_Orders_Find =new JMenuItem("订单查询");
    private JMenuItem menu_deliery_Find =new JMenuItem("配送单查询");
    private JMenuItem menu_Goods_Find=new JMenuItem("商品查询");
    private JMenuItem munu_Customer_Buy_Find=new JMenuItem("顾客消费情况查询");

    private JButton jbtBuy =new JButton("采购");


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

    public FrmMainAdmin(){
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("生鲜智能配送系统(管理员版) V1.0.0 ");
        this.menu_user.add(this.menu_customer_manage);
        this.menu_customer_manage.addActionListener(this);
        this.menu_user.add(this.menu_change_pwd);
        this.menu_change_pwd.addActionListener(this);

        this.menu_ziyuan_manage.add(menu_GoodsType_manage);
        this.menu_GoodsType_manage.addActionListener(this);
        this.menu_ziyuan_manage.add(menu_Goods_manage);
        this.menu_Goods_manage.addActionListener(this);
        this.menu_ziyuan_manage.add(menu_house_manage);
        this.menu_house_manage.addActionListener(this);
        this.menu_ziyuan_manage.add(menu_Car_manage);
        this.menu_Car_manage.addActionListener(this);

        this.menu_Order_Done.add(menu_Not_deliery_Orders);
        this.menu_Order_Done.add(menu_Orders_get);
        this.menu_Not_deliery_Orders.addActionListener(this);
        this.menu_Orders_get.addActionListener(this);

        this.menu_Find.add(menu_buy_Find);
        this.menu_buy_Find.addActionListener(this);
        this.menu_Find.add(menu_Orders_Find);
        this.menu_Orders_Find.addActionListener(this);
        this.menu_Find.add(menu_deliery_Find);
        this.menu_deliery_Find.addActionListener(this);
        this.menu_Find.add(menu_Goods_Find);
        this.menu_Goods_Find.addActionListener(this);
        this.menu_Find.add(munu_Customer_Buy_Find);
        this.munu_Customer_Buy_Find.addActionListener(this);

        menubar.add(menu_user);
        menubar.add(menu_ziyuan_manage);
        menubar.add(menu_Order_Done);
        menubar.add(menu_Find);
        this.setJMenuBar(menubar);

        this.getContentPane().add(new JScrollPane(this.dataTableGoods), BorderLayout.CENTER);
        this.reloadgoodsinform();

        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label=new JLabel("尊敬的管理员 " + admin.currentLoginUser.getUsername()+", 您好! ");//修改成   您好！+登陆用户名
        statusBar.add(label);
        statusBar.add(jbtBuy);
        jbtBuy.addActionListener(this);

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
            FrmChangePwd dlg=new FrmChangePwd(this,"修改密码",true,"admin");
            dlg.setVisible(true);
        }else if (e.getSource()==this.menu_Goods_Find){
            FrmDemandGoods dlg=new FrmDemandGoods(this,"商品信息查询",true,"admin");
            dlg.setVisible(true);
        }else if (e.getSource()==this.menu_customer_manage){
            FrmCustomerManage dlg=new FrmCustomerManage(this,"客户信息管理",true);
            dlg.setVisible(true);
        }else if (e.getSource()==this.munu_Customer_Buy_Find){
            FrmCustomerBuyFind dlg=new FrmCustomerBuyFind(this,"客户消费情况查询",true);
            dlg.setVisible(true);
        }else if (e.getSource()==this.menu_Orders_Find){
            FrmCustomerOrderFind dlg=new FrmCustomerOrderFind(this,"订单查询",true);
            dlg.setVisible(true);
        }else if (e.getSource()==this.menu_GoodsType_manage){
            FrmCommodityTypeManage dlg=new FrmCommodityTypeManage(this,"生鲜类别管理",true);
            dlg.setVisible(true);
        }else if (e.getSource()==this.menu_house_manage){
            FrmWareHouse dlg=new FrmWareHouse(this,"仓库管理",true);
            dlg.setVisible(true);
        }else if (e.getSource()==this.menu_Car_manage){
            FrmCar dlg=new FrmCar(this,"车辆管理",true);
            dlg.setVisible(true);
        }else if (e.getSource()==this.menu_Goods_manage){
            FrmGoodsManage dlg=new FrmGoodsManage(this,"商品管理",true);
            dlg.setVisible(true);
            this.reloadgoodsinform();
        }else if (e.getSource()==this.jbtBuy){
            int i= FrmMainAdmin.this.dataTableGoods.getSelectedRow();
            if(i<0) {
                FrmGoodsBuyAdd dlg=new FrmGoodsBuyAdd(this,"新商品采购",true);
                dlg.setVisible(true);
                this.reloadgoodsinform();
            }else {
                FrmGoodsBuyHas dlg=new FrmGoodsBuyHas(this,"商品采购",true,this.allgoods.get(i));
                dlg.setVisible(true);
                this.reloadgoodsinform();
            }
        }else if (e.getSource()==this.menu_buy_Find){
            FrmBuyFind dlg=new FrmBuyFind(this,"采购单查询",true);
            dlg.setVisible(true);
        }else if (e.getSource()==this.menu_Not_deliery_Orders){
            FrmOrderNotDelivery dlg=new FrmOrderNotDelivery(this,"未配送订单处理",true);
            dlg.setVisible(true);
        }else if (e.getSource()==this.menu_deliery_Find){
            FrmDeliveryFind dlg=new FrmDeliveryFind(this,"配送单查询",true);
            dlg.setVisible(true);
        }else if (e.getSource()==this.menu_Orders_get){
            FrmDeliveryFinish dlg=new FrmDeliveryFinish(this,"配送中的配送单",true);
            dlg.setVisible(true);
        }
    }

}
