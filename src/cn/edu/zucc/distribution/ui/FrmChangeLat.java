package cn.edu.zucc.distribution.ui;

import cn.edu.zucc.distribution.DistributionUtil;
import cn.edu.zucc.distribution.model.customer;
import cn.edu.zucc.distribution.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmChangeLat extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JPanel title=new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");

    private JLabel labelOldAddress=null;
    private JLabel labelAddress = new JLabel("新纬度：");
    private JTextField edtAddress = new JPasswordField(20);
    public FrmChangeLat(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        labelOldAddress=new JLabel("原纬度为："+ customer.currentLoginUser.getCuslat());
        title.add(labelOldAddress);
        this.getContentPane().add(title,BorderLayout.NORTH);
        workPane.add(labelAddress);
        workPane.add(edtAddress);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(330, 150);

        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        this.btnCancel.addActionListener(this);
        this.btnOk.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel)
            this.setVisible(false);
        else if(e.getSource()==this.btnOk){
            try {
                float lat=-1;
                if (edtAddress.getText()!=null && !"".equals(edtAddress.getText())) lat=new Float(edtAddress.getText());
                DistributionUtil.userManager.changeLatCustomer(customer.currentLoginUser, lat);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }


    }


}
