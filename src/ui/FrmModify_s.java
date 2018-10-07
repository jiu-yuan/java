package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import _jz.is_digit;
import _jz.jz_util;
import model.s_info;
import util.BaseException;

public class FrmModify_s extends JDialog implements ActionListener{
	private s_info s = new s_info();
	
	private JPanel toolBar = new JPanel();
	private JPanel jp = new JPanel();
	private JPanel jp1 = new JPanel();private JPanel jp2 = new JPanel();
	private JPanel jp3 = new JPanel();private JPanel jp4 = new JPanel();
	private JPanel jp5 = new JPanel();

	private JButton btnOk = new JButton("确定");
	private JButton btnCancel = new JButton("取消");
	private JLabel labelName = new JLabel("服务内容：");
	private JLabel label_level = new JLabel("服务等级：");
	private JLabel label_price = new JLabel("单价        ：");
	private JLabel label_valuation = new JLabel("计价单位：");
	private JLabel label_time = new JLabel("服务时间：");

	private JTextField edtName = new JTextField(25);
	private JTextField edt_price = new JTextField(25);
	private JTextField edt_valuation = new JTextField(25);
	private JTextField edt_time = new JTextField(25);
	
	private JComboBox<String> jcb_level = new JComboBox<String>();

	public FrmModify_s(Frmload_s frmload_s, String s, boolean b) {
		super(frmload_s, s, b);
	}
//================================================================================
	private int flag=0;//用于判断服务选项,add_s=1,modify_s=2
	//添加服务
	protected void add_s() {
		flag=1;
		fuyong();
	}
	//修改服务
	protected void modify_s(s_info super_s) {
		flag=2;
		this.s = super_s;		
		fuyong();
		
		edtName.setText(s.getService_content());
		edt_price.setText(String.valueOf(s.getUnit_price()));
		edt_valuation.setText(s.getUnit_of_valuation());
		edt_time.setText(String.valueOf(s.getService_time()));	
	}
	//删除服务
	protected void del_s(s_info super_s) {
		this.s = super_s;
		try {
			jz_util.s_info_manager.del_s(s);
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
//============================================================================================
	private void fuyong() {	//公用部分
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		jp.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp1.setLayout(new FlowLayout(FlowLayout.LEFT));jp2.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp3.setLayout(new FlowLayout(FlowLayout.LEFT));jp4.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp5.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		int s_level = 0;
		if(s.getService_level()!=0)	{
			s_level = s.getService_level();
			jcb_level.addItem(String.valueOf(s_level));
		}
		for(int i=1; i<=5; i++) 
		{
			if(i!=s_level)	jcb_level.addItem(String.valueOf(i));
		}
		
		jp1.add(labelName);
		jp1.add(edtName);
		jp2.add(label_price);
		jp2.add(edt_price);
		jp3.add(label_valuation);
		jp3.add(edt_valuation);
		jp4.add(label_time);
		jp4.add(edt_time);
		jp5.add(label_level);
		jp5.add(jcb_level);
		
		jp.add(jp1);jp.add(jp2);
		jp.add(jp3);jp.add(jp4);
		jp.add(jp5);
		
		this.getContentPane().add(jp);
		
		this.setSize(400, 300);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
	}
//=================================================================================================
	@Override//响应
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnCancel)
			this.setVisible(false);
		else if(e.getSource()==this.btnOk){
			try {
				if("".equals(this.edt_price.getText()))
					s.setUnit_price(0);
				else if(!is_digit.isDouble(this.edt_price.getText()))
					throw new BaseException("价格 请输入纯数字");
				else if(Double.parseDouble(this.edt_price.getText())<0)
					throw new BaseException("价格 不能小于0");
				else
					s.setUnit_price(Double.parseDouble(edt_price.getText()));
					
				
				if("".equals(this.edt_time.getText()))
					s.setService_time(0);
				else if(!is_digit.isInteger(this.edt_time.getText()))
					throw new BaseException("时间 请输入纯数字");
				else if(Integer.parseInt(this.edt_time.getText())<0)
					throw new BaseException("时间 不能小于0");
				else
					s.setService_time(Integer.parseInt(edt_time.getText()));
				
				s.setService_content(edtName.getText());
				s.setService_level(Integer.parseInt((String) jcb_level.getSelectedItem()));
				s.setUnit_of_valuation(edt_valuation.getText());
				//调用功能
				if(flag==1)
					jz_util.s_info_manager.add_s(s);
				else if(flag==2)
					jz_util.s_info_manager.change_s(s);
					
				else 
				{
					System.out.println("出错！flag = "+flag);
				}
				
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}
}
