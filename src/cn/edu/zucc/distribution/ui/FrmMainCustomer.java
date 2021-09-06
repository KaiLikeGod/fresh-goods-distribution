package cn.edu.zucc.distribution.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmMainCustomer extends JFrame implements ActionListener {
    public FrmMainCustomer(){
        JOptionPane.showMessageDialog(null, "顾客登录完成 ", "提示" , JOptionPane.PLAIN_MESSAGE);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
