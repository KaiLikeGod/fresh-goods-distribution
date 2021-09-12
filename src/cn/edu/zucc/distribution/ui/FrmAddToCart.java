package cn.edu.zucc.distribution.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import cn.edu.zucc.distribution.model.*;
import cn.edu.zucc.distribution.DistributionUtil;
import cn.edu.zucc.distribution.util.BaseException;
import cn.edu.zucc.distribution.util.BusinessException;

public class FrmAddToCart extends JDialog implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private JButton btnAdd = new JButton("+");
	private JButton btnMinus = new JButton("-");
	private JButton btnConfirm = new JButton("确认");
	private JButton btnCancel = new JButton("取消");
	private JTextField labelNum = null;
	int now = 1;
	int maxN = 0;
	goods bg = null;
	public FrmAddToCart(Frame f, String s, boolean b, goods beanGood) {
		super(f, s, b);
		bg = beanGood;
		labelNum = new JTextField( String.valueOf(now) ,3);
		toolBar.add(btnConfirm);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(btnMinus);
		workPane.add(labelNum);
		workPane.add(btnAdd);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(320, 120);
		this.btnAdd.addActionListener(this);
		this.btnCancel.addActionListener(this);
		this.btnConfirm.addActionListener(this);
		this.btnMinus.addActionListener(this);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int)((width - this.getWidth()) / 2), (int)((height - this.getHeight()) / 2));
		this.validate();
	}

	public FrmAddToCart(JDialog f, String s, boolean b, goods beanGood) {
		super(f, s, b);
		bg = beanGood;
		labelNum = new JTextField( String.valueOf(now) ,3);
		toolBar.add(btnConfirm);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(btnMinus);
		workPane.add(labelNum);
		workPane.add(btnAdd);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(320, 120);
		this.btnAdd.addActionListener(this);
		this.btnCancel.addActionListener(this);
		this.btnConfirm.addActionListener(this);
		this.btnMinus.addActionListener(this);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int)((width - this.getWidth()) / 2), (int)((height - this.getHeight()) / 2));
		this.validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.btnAdd) {
			try {
				if(now == bg.getInventorynumber()) throw new BusinessException("已达最大值");
				else now++;
			}
			catch(BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
			labelNum.setText("    " + String.valueOf(now) + "    ");
		}
		else if(e.getSource() == this.btnMinus) {
			try {
				if(now == 1) throw new BusinessException("已达最小值");
				else now--;
			}
			catch(BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
			labelNum.setText("    " + String.valueOf(now) + "    ");
		}
		else if(e.getSource() == this.btnConfirm) {
			try {
				DistributionUtil.goodsManager.AddToBuyCar(now,bg);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(null, "购物车添加成功! ", "恭喜" , JOptionPane.PLAIN_MESSAGE);
			this.dispose();
		}
		else if(e.getSource() == this.btnCancel) {
			this.dispose();
		}
	}


}
