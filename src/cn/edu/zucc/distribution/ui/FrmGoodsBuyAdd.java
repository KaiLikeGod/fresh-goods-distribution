package cn.edu.zucc.distribution.ui;

import cn.edu.zucc.distribution.DistributionUtil;
import cn.edu.zucc.distribution.model.goods;
import cn.edu.zucc.distribution.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmGoodsBuyAdd extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("采购");
    private JButton btnCancel = new JButton("取消");

    private JLabel labelhouse =new JLabel("存放仓库：");
    private JLabel labelType =new JLabel("类别编号：");
    private JLabel labelName =new JLabel("商品名称：");
    private JLabel labelPrice =new JLabel("商品价格：");
    private JLabel labelsize =new JLabel("商品规格：");
    private JLabel labelWeight =new JLabel("商品重量：");
    private JLabel labelNumber =new JLabel("采购库存：");
    private JLabel labelcold =new JLabel("是否冷藏：");
    private JComboBox<String> boxCarCold=new JComboBox<String>();

    private JTextField edtHouse = new JTextField(20);
    private JTextField edtType = new JTextField(20);
    private JTextField edtName = new JTextField(20);
    private JTextField edtprice = new JTextField(20);
    private JTextField edtsize = new JTextField(20);
    private JTextField edtweight = new JTextField(20);
    private JTextField edtNumber = new JTextField(20);

    public FrmGoodsBuyAdd(Frame f, String s, boolean b) {
        super(f, s, b);

        boxCarCold.addItem("冷藏");
        boxCarCold.addItem("无需冷藏");
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelName);
        workPane.add(edtName);
        workPane.add(labelhouse);
        workPane.add(edtHouse);
        workPane.add(labelType);
        workPane.add(edtType);
        workPane.add(labelPrice);
        workPane.add(edtprice);
        workPane.add(labelsize);
        workPane.add(edtsize);
        workPane.add(labelWeight);
        workPane.add(edtweight);
        workPane.add(labelNumber);
        workPane.add(edtNumber);
        workPane.add(labelcold);
        workPane.add(boxCarCold);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(330, 320);//Adjust the height
        this.btnCancel.addActionListener(this);
        this.btnOk.addActionListener(this);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.validate();
    }

    public FrmGoodsBuyAdd(Dialog f, String s, boolean b) {
        super(f, s, b);

        boxCarCold.addItem("冷藏");
        boxCarCold.addItem("无需冷藏");
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelName);
        workPane.add(edtName);
        workPane.add(labelhouse);
        workPane.add(edtHouse);
        workPane.add(labelType);
        workPane.add(edtType);
        workPane.add(labelPrice);
        workPane.add(edtprice);
        workPane.add(labelsize);
        workPane.add(edtsize);
        workPane.add(labelWeight);
        workPane.add(edtweight);
        workPane.add(labelNumber);
        workPane.add(edtNumber);
        workPane.add(labelcold);
        workPane.add(boxCarCold);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(330, 320);//Adjust the height
        this.btnCancel.addActionListener(this);
        this.btnOk.addActionListener(this);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.validate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnCancel)
            this.setVisible(false);
        else if (e.getSource() == this.btnOk) {
            int house=this.edtHouse.getText()==null || "".equals(this.edtHouse.getText())?-1:Integer.parseInt(this.edtHouse.getText());
            int type=this.edtType.getText()==null || "".equals(this.edtType.getText())?-1:Integer.parseInt(this.edtType.getText());
            String name=this.edtName.getText();
            float price=this.edtprice.getText()==null || "".equals(this.edtprice.getText())?0:Float.parseFloat(this.edtprice.getText());
            float size=this.edtsize.getText()==null || "".equals(this.edtsize.getText())?0:Float.parseFloat(this.edtsize.getText());
            float weight=this.edtweight.getText()==null || "".equals(this.edtweight.getText())?0:Float.parseFloat(this.edtweight.getText());
            int number=this.edtNumber.getText()==null || "".equals(this.edtNumber.getText())?-1:Integer.parseInt(this.edtNumber.getText());
            boolean cold= boxCarCold.getSelectedItem().toString().equals("冷藏");
            try {
                DistributionUtil.goodsManager.CreateNewGood(house,type,name,price,size,weight,cold,number);
                JOptionPane.showMessageDialog(null, "商品采购单生成成功! ", "恭喜" , JOptionPane.PLAIN_MESSAGE);
                JOptionPane.showMessageDialog(null, "商品采购完成！ ", "恭喜" , JOptionPane.PLAIN_MESSAGE);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }

        }


    }
}
