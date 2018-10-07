package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import _jz.jz_util;
import model.m_budget;
import model.s_budget;
import model.user;
import util.BaseException;

public class Frmtotal extends JDialog implements ActionListener{
	private double cnt_m = 0;
	private double cnt_s = 0;
	private double total = 0;
	private user usr = new user();
	private List<m_budget> list_m = new ArrayList<m_budget>();
	private List<s_budget> list_s = new ArrayList<s_budget>();
	
	private JPanel jp = new JPanel();
	private JPanel jp1 = new JPanel();private JPanel jp2 = new JPanel();
	private JPanel jp3 = new JPanel();private JPanel jp4 = new JPanel();
	
	private JButton btnOk = new JButton("确定");
	private JLabel label_cnt_m = new JLabel("材料合计：");
	private JLabel label_cnt_s = new JLabel("服务合计：");
	private JLabel label_total = new JLabel("合计：");
	
	public Frmtotal(FrmMain f, String s, boolean b) {
		// TODO Auto-generated constructor stub
		super(f,s,b);
		try {
			list_m = jz_util.m_budget_manager.load_m_budget(user.currentLoginUser);
			list_s = jz_util.s_budget_manager.load_s_budget(user.currentLoginUser);
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		fuyong();
	}
	public Frmtotal(Frmload_bgt f, String s, boolean b, user super_usr) {
		// TODO Auto-generated constructor stub
		super(f,s,b);
		this.usr = super_usr;
		try {
			list_m = jz_util.m_budget_manager.load_m_budget(this.usr);
			list_s = jz_util.s_budget_manager.load_s_budget(this.usr);
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		fuyong();
	}
	private void fuyong() {
		
		for(int i=0; i<list_m.size(); i++)
		{
			cnt_m+=list_m.get(i).getUnit_price()*list_m.get(i).getQuantity();
		}
		for(int i=0; i<list_s.size(); i++)
		{
			double unit_price = list_s.get(i).getS().getUnit_price();
			cnt_s+=list_s.get(i).getQuantity()*unit_price;
		}
		total = cnt_m+cnt_s;
		
		label_cnt_m.setText("材料合计："+cnt_m);
		label_cnt_s.setText("服务合计："+cnt_s);
		label_total.setText("合计         ："+total);
		
		
		jp.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp1.setLayout(new FlowLayout(FlowLayout.LEFT));jp2.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp3.setLayout(new FlowLayout(FlowLayout.LEFT));jp4.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		jp4.add(this.btnOk);
		this.getContentPane().add(jp4, BorderLayout.SOUTH);
		jp1.setPreferredSize(new Dimension(350, 30));jp2.setPreferredSize(new Dimension(350, 30));
		jp3.setPreferredSize(new Dimension(350, 30));
		
		jp1.add(label_cnt_m);
		jp2.add(label_cnt_s);
		jp3.add(label_total);

		jp.add(jp1);jp.add(jp2);
		jp.add(jp3);
		this.getContentPane().add(jp);
		
		this.setSize(300, 200);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnOk)
			this.setVisible(false);
	}

}
