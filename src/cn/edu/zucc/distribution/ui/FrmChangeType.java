package cn.edu.zucc.distribution.ui;

import cn.edu.zucc.distribution.DistributionUtil;
import cn.edu.zucc.distribution.util.BaseException;
import cn.edu.zucc.distribution.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmChangeType extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private JButton btnOk = new JButton("修改");
	private JButton btnCancel = new JButton("取消");

	private JLabel labelTypeId=new JLabel("类别编号：");
	private JLabel labelTypeName = new JLabel("类别名称：");
	private JLabel labelTypeDescrible = new JLabel("类别描述：");
	private JTextField edtTypeId = new JTextField(20);
	private JTextField edtTypeName = new JTextField(20);
	private JTextField edtTypeDescrible = new JTextField(20);
	int id=0;
	public FrmChangeType(Dialog f, String s, boolean b,commodity_type tp) {
		super(f, s, b);
		this.id=tp.getTypeid();
		edtTypeId = new JTextField(Integer.toString(tp.getTypeid()),20);
		edtTypeId.setEditable(false);
		edtTypeName = new JTextField(tp.getTypename(),20);
		edtTypeDescrible = new JTextField(tp.getTypedescribe(),20);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelTypeId);
		workPane.add(edtTypeId);
		workPane.add(labelTypeName);
		workPane.add(edtTypeName);
		workPane.add(labelTypeDescrible);
		workPane.add(edtTypeDescrible);

		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(330, 180);//Adjust the height
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
		if(e.getSource()==this.btnCancel)
			this.setVisible(false);
		else if(e.getSource()==this.btnOk){
			String TypeName	=this.edtTypeName.getText();
			String TypeDescrible=this.edtTypeDescrible.getText();
			try {
				DistributionUtil.goodsManager.changeType(id,TypeName, TypeDescrible);
				JOptionPane.showMessageDialog(null, "类别修改成功! ", "恭喜" , JOptionPane.PLAIN_MESSAGE);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				return;
			}

		}
			
		
	}


}
