package cn.edu.zucc.distribution.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class FrmRegisterType extends JDialog implements ActionListener {
    private JPanel toolBar=new JPanel();
    private JPanel workPane=new JPanel();
    private JLabel labelType = new JLabel("注册类型");
    private JRadioButton rbcustomer=new JRadioButton("客户",true);
    private JRadioButton rbadmin=new JRadioButton("管理员");
    private JButton btnok =new JButton("确定");
    private JButton btnreturn=new JButton("取消");
    public FrmRegisterType(Dialog f, String s, boolean b){
        super(f,s,b);
        ButtonGroup group = new ButtonGroup();
        group.add(rbcustomer);
        group.add(rbadmin);
        toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        toolBar.add(btnok);
        toolBar.add(btnreturn);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelType);
        workPane.add(rbadmin);
        workPane.add(rbcustomer);
        this.getContentPane().add(workPane,BorderLayout.CENTER);
        this.setSize(250,130);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.validate();

        btnok.addActionListener(this);
        btnreturn.addActionListener(this);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnreturn) {
            this.setVisible(false);
        }else if (e.getSource()==this.btnok){
            if (this.rbadmin.isSelected()==true){
                FrmRegisterAdmin dlg=new FrmRegisterAdmin(this,"管理员注册",true);
                this.setVisible(false);
                dlg.setVisible(true);
            }else {
                FrmRegisterCustomer dlg= new FrmRegisterCustomer(this,"顾客注册",true);
                this.setVisible(false);
                dlg.setVisible(true);
            }
        }

    }


}
