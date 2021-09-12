package cn.edu.zucc.distribution.ui;

import cn.edu.zucc.distribution.DistributionUtil;
import cn.edu.zucc.distribution.model.goods;
import cn.edu.zucc.distribution.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmGoodsBuyHas extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private JButton btnOk = new JButton("采购");
	private JButton btnCancel = new JButton("取消");

	private JLabel labelId = new JLabel("商品编号：");
	private JLabel labelhouse =new JLabel("存放仓库：");
	private JLabel labelType =new JLabel("类别编号：");
	private JLabel labelName =new JLabel("商品名称：");
	private JLabel labelPrice =new JLabel("商品价格：");
	private JLabel labelsize =new JLabel("商品规格：");
	private JLabel labelWeight =new JLabel("商品重量：");
	private JLabel labelNumber =new JLabel("采购数量：");

	private JTextField edtId =null;
	private JTextField edtHouse = new JTextField(20);
	private JTextField edtType = new JTextField(20);
	private JTextField edtName = new JTextField(20);
	private JTextField edtprice = new JTextField(20);
	private JTextField edtsize = new JTextField(20);
	private JTextField edtweight = new JTextField(20);
	private JTextField edtNumber = new JTextField(20);

	public FrmGoodsBuyHas(Frame f, String s, boolean b, goods c) {
		super(f, s, b);
		edtId =new JTextField(Integer.toString(c.getGoodsid()),20);
		edtId.setEditable(false);
		edtHouse = new JTextField(Integer.toString(c.getHouseid()),20);
		edtHouse.setEditable(false);
		edtType = new JTextField(Integer.toString(c.getTypeid()),20);
		edtType.setEditable(false);
		edtName = new JTextField(c.getGoodsname(),20);
		edtName.setEditable(false);
		edtprice = new JTextField(Float.toString(c.getGoodsprice()),20);
		edtprice.setEditable(false);
		edtsize = new JTextField(Float.toString(c.getGoodssize()),20);
		edtsize.setEditable(false);
		edtweight = new JTextField(Float.toString(c.getOrdersweight()),20);
		edtweight.setEditable(false);


		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelId);
		workPane.add(edtId);
		workPane.add(labelhouse);
		workPane.add(edtHouse);
		workPane.add(labelType);
		workPane.add(edtType);
		workPane.add(labelName);
		workPane.add(edtName);
		workPane.add(labelPrice);
		workPane.add(edtprice);
		workPane.add(labelWeight);
		workPane.add(edtweight);
		workPane.add(labelsize);
		workPane.add(edtsize);
		workPane.add(labelNumber);
		workPane.add(edtNumber);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(330, 350);//Adjust the height
		this.btnCancel.addActionListener(this);
		this.btnOk.addActionListener(this);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);
		this.validate();
	}

	public FrmGoodsBuyHas(Dialog f, String s, boolean b, goods c) {
		super(f, s, b);
		edtId =new JTextField(Integer.toString(c.getGoodsid()),20);
		edtId.setEditable(false);
		edtHouse = new JTextField(Integer.toString(c.getHouseid()),20);
		edtHouse.setEditable(false);
		edtType = new JTextField(Integer.toString(c.getTypeid()),20);
		edtType.setEditable(false);
		edtName = new JTextField(c.getGoodsname(),20);
		edtName.setEditable(false);
		edtprice = new JTextField(Float.toString(c.getGoodsprice()),20);
		edtprice.setEditable(false);
		edtsize = new JTextField(Float.toString(c.getGoodssize()),20);
		edtsize.setEditable(false);
		edtweight = new JTextField(Float.toString(c.getOrdersweight()),20);
		edtweight.setEditable(false);


		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelId);
		workPane.add(edtId);
		workPane.add(labelhouse);
		workPane.add(edtHouse);
		workPane.add(labelType);
		workPane.add(edtType);
		workPane.add(labelName);
		workPane.add(edtName);
		workPane.add(labelPrice);
		workPane.add(edtprice);
		workPane.add(labelWeight);
		workPane.add(edtweight);
		workPane.add(labelsize);
		workPane.add(edtsize);
		workPane.add(labelNumber);
		workPane.add(edtNumber);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(330, 350);//Adjust the height
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
			try {
				int number=this.edtNumber.getText()==null || "".equals(this.edtNumber.getText())?-1:Integer.parseInt(this.edtNumber.getText());
				DistributionUtil.goodsManager.buyGoods(Integer.parseInt(this.edtId.getText()),number,Integer.parseInt(this.edtHouse.getText()));
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
