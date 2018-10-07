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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import _jz.is_digit;
import _jz.jz_util;
import model.m_budget;
import model.m_info;
import model.s_info;
import model.user;
import model.s_budget;
import model.s_info;
import util.BaseException;

public class FrmModify_s_budget extends JDialog implements ActionListener{
	private s_info s_inf = new s_info();
	private List<s_info> all_s = new ArrayList<s_info>();
	private s_budget s_bgt = new s_budget();
	private int required_time = 0;
	private int quantity = 0;
	private String remark = null;
	
	private JPanel toolBar = new JPanel();
	private JPanel jp = new JPanel();
	private JPanel jp1 = new JPanel();private JPanel jp2 = new JPanel();
	private JPanel jp3 = new JPanel();private JPanel jp4 = new JPanel();
	private JPanel jp5 = new JPanel();private JPanel jp6 = new JPanel();
	private JPanel jp7 = new JPanel();

	private JButton btnOk = new JButton("确定");
	private JButton btnCancel = new JButton("取消");
	private JButton btnDel = new JButton("删除");
	private JLabel labelName = new JLabel("服务内容：");
	private JLabel label_level = new JLabel("服务等级：");
	private JLabel label_price = new JLabel("单价        ：");
	private JLabel label_valuation = new JLabel("计价单位：");
	private JLabel label_time = new JLabel("服务时间：");
	private JLabel label_quantity = new JLabel("数量        ：");
	private JLabel label_remark = new JLabel("备注        ：");

	private JComboBox<String> jcb_level = new JComboBox<String>();
	private JTextField edt_quantity = new JTextField(25);
	private JTextField edt_remark = new JTextField(25);
	
	public FrmModify_s_budget(Frmload_s frmload_s, String s, boolean b) {
		super(frmload_s,s,b);	
	}
public FrmModify_s_budget(FrmMain frmMain, String s, boolean b) {
		super(frmMain, s, b);
		// TODO Auto-generated constructor stub
	}
	//====================================================================================
	private int flg=0;//用于判断选项,add_s=1,modify_s=2
	protected void add_s_budget(s_info super_s) {					//添加服务预算
		flg=1;
		this.s_inf = super_s;
		this.required_time = super_s.getService_time();//一件商品时的时间
		this.quantity = 1;
		this.remark = "";
		fuyong();
	}
	protected void modify_s_budget(s_budget super_s_budget) {		//修改服务预算
		flg=2;
		this.s_bgt = super_s_budget;
		this.s_inf = super_s_budget.getS();
		this.required_time = s_inf.getService_time()*s_bgt.getQuantity();//所需时间==服务时间*数量
		this.quantity = s_bgt.getQuantity();
		this.remark = s_bgt.getRemark();
		jcb_level.addItem(String.valueOf(s_inf.getService_level()));System.out.println("等级a："+s_inf.getService_level());
		fuyong();
	}														//删除服务预算,在btnDel中
//公用部分============================================================================
	private void fuyong() {
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		if(flg==2)
			toolBar.add(btnDel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		labelName.setText("服务内容："+s_inf.getService_content());
		label_price.setText("单价        ："+s_inf.getUnit_price());
		label_valuation.setText("计价单位："+s_inf.getUnit_of_valuation());
		label_time.setText("单次服务时间："+s_inf.getService_time()+"小时");
		edt_quantity.setText(String.valueOf(this.quantity));
		edt_remark.setText(this.remark);
		
		try {	//提取所有该服务条目
			all_s = jz_util.s_info_manager.load_s_info(this.s_inf.getService_content(),this.s_inf.getService_time());
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for(int i=0; i<all_s.size(); i++)//将未加入下拉框的服务等级加入
		{
			int flag=0;
			for(int j=0; j<jcb_level.getItemCount(); j++)
			{
				if(jcb_level.getItemAt(j).equals(String.valueOf(all_s.get(i).getService_level())))
					{flag=1;break;}
			}
			if(flag==0)
			{
				jcb_level.addItem(String.valueOf(all_s.get(i).getService_level()));
			}
		}
		
		jp.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp1.setLayout(new FlowLayout(FlowLayout.LEFT));jp2.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp3.setLayout(new FlowLayout(FlowLayout.LEFT));jp4.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp5.setLayout(new FlowLayout(FlowLayout.LEFT));jp6.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp7.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		jp1.setPreferredSize(new Dimension(350, 30));jp2.setPreferredSize(new Dimension(350, 30));
		jp3.setPreferredSize(new Dimension(350, 30));jp4.setPreferredSize(new Dimension(350, 30));
		jp5.setPreferredSize(new Dimension(350, 30));jp6.setPreferredSize(new Dimension(400, 30));
		jp7.setPreferredSize(new Dimension(400, 30));
		
		jp1.add(labelName);
		jp2.add(label_price);
		jp3.add(label_valuation);
		jp4.add(label_time);
		jp5.add(label_level);
		jp5.add(jcb_level);
		jp6.add(label_quantity);
		jp6.add(edt_quantity);
		jp7.add(label_remark);
		jp7.add(edt_remark);
		
		jp.add(jp1);jp.add(jp2);
		jp.add(jp3);jp.add(jp4);
		jp.add(jp5);jp.add(jp6);jp.add(jp7);
		
		this.getContentPane().add(jp);
		
		this.setSize(400, 400);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);
		
		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		this.btnDel.addActionListener(this);
		this.jcb_level.addActionListener(this);
	}
//响应设置============================================================================	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.jcb_level)//等级下拉框
		{
			for(int i=0; i<all_s.size(); i++)
			{
				if(jcb_level.getSelectedItem().equals(String.valueOf(all_s.get(i).getService_level())))
				{
					this.s_inf = all_s.get(i);
					break;
				}
			}
		}
		else if(e.getSource()==this.btnCancel)
			this.setVisible(false);
		else if(e.getSource()==this.btnDel) {
			try {
				jz_util.s_budget_manager.del_s_budget(this.s_bgt);
				this.setVisible(false);
			} catch (BaseException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.btnOk){
			try {
				if("".equals(this.edt_quantity.getText()))
					throw new BaseException("数量不能为空");
				if(!is_digit.isInteger(this.edt_quantity.getText()))
					throw new BaseException("数量请输入纯数字");
				if(Integer.parseInt(this.edt_quantity.getText())<1||Integer.parseInt(this.edt_quantity.getText())>1000)
					throw new BaseException("数量必须在1~1000");
				if(this.edt_remark.getText().length()>30)
					throw new BaseException("备注长度不能超过30字");
				
				this.quantity = Integer.parseInt(this.edt_quantity.getText());
				this.required_time = this.quantity*this.s_inf.getService_time();
				s_bgt.setS(this.s_inf);
				s_bgt.setUser(user.currentLoginUser);
				s_bgt.setQuantity(this.quantity);
				s_bgt.setRequired_time(this.required_time);
				s_bgt.setRemark(this.edt_remark.getText());
				
				if(flg==1)
					jz_util.s_budget_manager.add_s_budget(s_bgt);
				else if(flg==2)
					jz_util.s_budget_manager.change_s_budget(s_bgt);
				else throw new BaseException("服务预算操作错误，flg="+flg);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}
}
