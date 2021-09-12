package cn.edu.zucc.distribution.ui;

import cn.edu.zucc.distribution.DistributionUtil;
import cn.edu.zucc.distribution.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmAddCar extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private JButton btnOk = new JButton("添加");
	private JButton btnCancel = new JButton("取消");

	private JLabel labelCarnum = new JLabel("车牌号：  ");
	private JLabel labelCarWeght =new JLabel("车辆载重：");
	private JLabel labelCarV =new JLabel("车辆容积：");
	private JLabel labelCarPhone =new JLabel("联系电话：");
	private JLabel labelCarCold =new JLabel("是否冷藏：");
	private JComboBox<String> boxCarCold=new JComboBox<String>();

	private JTextField edtCarnum = new JTextField(20);
	private JTextField edtCarWeight = new JTextField(20);
	private JTextField edtCarV = new JTextField(20);
	private JTextField edtCarphone = new JTextField(20);
	private JTextField edtCarCold = new JTextField(20);
	public FrmAddCar(Dialog f, String s, boolean b) {
		super(f, s, b);
		boxCarCold.addItem("冷藏车");
		boxCarCold.addItem("非冷藏车");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelCarnum);
		workPane.add(edtCarnum);
		workPane.add(labelCarWeght);
		workPane.add(edtCarWeight);
		workPane.add(labelCarV);
		workPane.add(edtCarV);
		workPane.add(labelCarPhone);
		workPane.add(edtCarphone);
		workPane.add(labelCarCold);
		workPane.add(boxCarCold);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(330, 250);//Adjust the height
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
			String num	=this.edtCarnum.getText();
			String phone=this.edtCarphone.getText();
			boolean cold= boxCarCold.getSelectedItem().toString().equals("冷藏车");
			float weight=-1;
			if (this.edtCarWeight.getText()!=null && !"".equals(this.edtCarWeight.getText())) weight=new Float(this.edtCarWeight.getText());
			float v=-1;
			if (this.edtCarV.getText()!=null && !"".equals(this.edtCarV.getText())) v=new Float(this.edtCarV.getText());
			try {
				DistributionUtil.deliveryMannger.createNewCar(num,weight,v,phone,cold);
				JOptionPane.showMessageDialog(null, "车辆添加成功! ", "恭喜" , JOptionPane.PLAIN_MESSAGE);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				return;
			}

		}
			
		
	}


}
