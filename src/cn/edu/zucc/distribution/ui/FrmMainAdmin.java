package cn.edu.zucc.distribution.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmMainAdmin extends JFrame implements ActionListener {
    public FrmMainAdmin(){
        JOptionPane.showMessageDialog(null, "管理员登录完成 ", "提示" , JOptionPane.PLAIN_MESSAGE);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
