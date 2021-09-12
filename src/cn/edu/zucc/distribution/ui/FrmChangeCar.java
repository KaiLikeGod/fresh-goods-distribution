package cn.edu.zucc.distribution.ui;

import cn.edu.zucc.distribution.DistributionUtil;
import cn.edu.zucc.distribution.model.car;
import cn.edu.zucc.distribution.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmChangeCar extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private JButton btnOk = new JButton("修改");
	private JButton btnCancel = new JButton("取消");

	private JLabel labelCarnum = new JLabel("车牌号：  ");
	private JLabel labelCarWeght =new JLabel("车辆载重：");
	private JLabel labelCarV =new JLabel("车辆容积：");
	private JLabel labelCarPhone =new JLabel("联系电话：");
	private JLabel labelCarCold =new JLabel("是否冷藏：");
	private JLabel labelCarId=new JLabel("车辆编号：");
	private JComboBox<String> boxCarCold=new JComboBox<String>();

	private JTextField edtCarId=null;
	private JTextField edtCarnum = new JTextField(20);
	private JTextField edtCarWeight = new JTextField(20);
	private JTextField edtCarV = new JTextField(20);
	private JTextField edtCarphone = new JTextField(20);
	public FrmChangeCar(Dialog f, String s, boolean b, car c) {
		super(f, s, b);
		edtCarId=new JTextField(Integer.toString(c.getCarid()),20);
		edtCarId.setEditable(false);
		edtCarnum = new JTextField(c.getCarnum(),20);
		edtCarnum.setEditable(false);
		edtCarWeight = new JTextField(Float.toString(c.getCarweight()),20);
		edtCarWeight.setEditable(false);
		edtCarV = new JTextField(Float.toString(c.getCarv()),20);
		edtCarV.setEditable(false);
		edtCarphone = new JTextField(c.getCarphone(),20);

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
			int num	= Integer.parseInt(this.edtCarId.getText());
			String phone=this.edtCarphone.getText();
			boolean cold= boxCarCold.getSelectedItem().toString().equals("冷藏车");
			try {
				DistributionUtil.deliveryMannger.changeNewCar(num,phone,cold);
				JOptionPane.showMessageDialog(null, "车辆信息修改成功! ", "恭喜" , JOptionPane.PLAIN_MESSAGE);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				return;
			}

		}
			
		
	}


}
