package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import _jz.is_digit;
import _jz.jz_util;
import model.category;
import model.m_budget;
import model.brand;
import model.m_info;
import model.m_to_s;
import model.s_info;
import util.BaseException;

public class FrmModify_m extends JDialog implements ActionListener{
//	public m_info m2;
	private m_info m = new m_info();
	private brand brd = new brand();
	private category cate = new category();
	private s_info s_inf = new s_info();
	private int s_id=0;
	
	private JPanel toolBar = new JPanel();
	private JPanel jp = new JPanel();
	private JPanel jp1 = new JPanel();private JPanel jp2 = new JPanel();
	private JPanel jp3 = new JPanel();private JPanel jp4 = new JPanel();
	private JPanel jp5 = new JPanel();private JPanel jp6 = new JPanel();
	private JPanel jp7 = new JPanel();private JPanel jp8 = new JPanel();
	private JPanel jp9 = new JPanel();

	private JButton btnOk = new JButton("确定");
	private JButton btnCancel = new JButton("取消");
	private JLabel labelName = new JLabel("材料名：");
	private JLabel label_brand = new JLabel("品牌号：");
	private JLabel label_category = new JLabel("类别号：");
	private JLabel label_standard = new JLabel("规格    ：");
	private JLabel label_model_number = new JLabel("型号    ：");
	private JLabel label_flower_color = new JLabel("花色    ：");
	private JLabel label_unit_price = new JLabel("单价    ：");
	private JLabel label_unit_of_valuation = new JLabel("计价单位：");
	private JLabel label_s_inf = new JLabel("服务    ：");
	
	private JTextField edtName = new JTextField(25);
	private JTextField edt_brand = new JTextField(25);
	private JTextField edt_category = new JTextField(25);
	private JTextField edt_standard = new JTextField(25);
	private JTextField edt_model_number = new JTextField(25);
	private JTextField edt_flower_color = new JTextField(25);
	private JTextField edt_unit_price = new JTextField(25);
	private JTextField edt_unit_of_valuation = new JTextField(24);
	private JTextField edt_s_inf = new JTextField(25);
	
	private JButton btn_brand= new JButton("查看现有品牌");
	private JButton btn_category= new JButton("查看现有类别");
	private JButton btn_s_inf= new JButton("查看现有服务");
	
	public FrmModify_m(Frame f, String s, boolean b) {
		super(f, s, b);
	}
	public FrmModify_m(MouseAdapter mouseAdapter, String s, boolean b) {
		// TODO Auto-generated constructor stub
		super();
	}
	//=================================================================================
	private int flg=0;//用于判断选项,add_s=1,modify_s=2
	protected void add_m() {								//添加材料
		flg=1;
		fuyong();
	}
	protected void modify_m(m_info super_m) {			//修改材料
		flg=2;
		this.m = super_m;
		fuyong();
		
		edtName.setText(m.getM_name());//赋值当前材料名
		edt_brand.setText(String.valueOf(m.getBrand().getBrand_id()));//赋值当前品牌号
		edt_category.setText(String.valueOf(m.getCategory().getCategory_id()));//赋值当前类别号
		edt_standard.setText(m.getStandard());
		edt_model_number.setText(m.getModel_number());
		edt_flower_color.setText(m.getFlower_color());
		edt_unit_price.setText(String.valueOf(m.getUnit_price()));
		edt_unit_of_valuation.setText(m.getUnit_of_valuation());
		
		try {
			s_id = jz_util.m_to_s_manager.m_get_s(m.getM_id());
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(s_id!=0)
			edt_s_inf.setText(String.valueOf(s_id));
	}
	protected void del_m(m_info super_m) {				//删除材料
		try {
			m_to_s mts = new m_to_s();
			mts.setM_id(super_m.getM_id());
			jz_util.m_to_s_manager.del_m_to_s(mts);
			jz_util.m_info_manager.del_m(super_m);
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
//公用部分=========================================================================
	private void fuyong() {

		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		jp.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp1.setLayout(new FlowLayout(FlowLayout.LEFT));jp2.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp3.setLayout(new FlowLayout(FlowLayout.LEFT));jp4.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp5.setLayout(new FlowLayout(FlowLayout.LEFT));jp6.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp7.setLayout(new FlowLayout(FlowLayout.LEFT));jp8.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp9.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		jp1.add(labelName);
		jp1.add(edtName);
		jp2.add(label_brand);
		jp2.add(edt_brand);
		jp2.add(btn_brand);
		jp3.add(label_category);
		jp3.add(edt_category);
		jp3.add(btn_category);
		jp4.add(label_standard);
		jp4.add(edt_standard);
		jp5.add(label_model_number);
		jp5.add(edt_model_number);
		jp6.add(label_flower_color);
		jp6.add(edt_flower_color);
		jp7.add(label_unit_price);
		jp7.add(edt_unit_price);
		jp8.add(label_unit_of_valuation);
		jp8.add(edt_unit_of_valuation);
		jp9.add(label_s_inf);
		jp9.add(edt_s_inf);
		jp9.add(btn_s_inf);
		
		jp.add(jp1);jp.add(jp2);
		jp.add(jp3);jp.add(jp4);
		jp.add(jp5);jp.add(jp6);
		jp.add(jp7);jp.add(jp8);
		if(flg==2)
			jp.add(jp9);
		
		this.getContentPane().add(jp);
		
		this.setSize(500, 430);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		
		this.btn_brand.addActionListener(this);
		this.btn_category.addActionListener(this);
		this.btn_s_inf.addActionListener(this);
	}
//响应设置==========================================================================
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btn_s_inf)
		{
			Frmload_s fs = new Frmload_s(this,"现有服务信息", true); 
			fs.setVisible(true);
			if(fs.s_inf.getS_id()!=0)
			{
				edt_s_inf.setText(String.valueOf(fs.s_inf.getS_id()));
				this.s_inf = fs.s_inf;
			}
		}
		else if(e.getSource()==this.btn_brand)
		{
			Frmload_brand fb = new Frmload_brand(this,"现有品牌信息", true); 
			if(fb.brand.getBrand_id()!=0) 
			{
				edt_brand.setText(String.valueOf(fb.brand.getBrand_id()));
				this.brd = fb.brand;
			}
		}
		else if(e.getSource()==this.btn_category)
		{
			Frmload_category fc = new Frmload_category(this,"现有类别信息", true);
			if(fc.category.getCategory_id()!=0) 
			{
				edt_category.setText(String.valueOf(fc.category.getCategory_id()));
				this.cate = fc.category;
			}	
		}
		else if(e.getSource()==this.btnCancel)
			this.setVisible(false);
		else if(e.getSource()==this.btnOk){
			try {
				double unit_price;
				
				if("".equals(this.edt_brand.getText()))
					throw new BaseException("品牌号不能为空");
				else if(!is_digit.isInteger(this.edt_brand.getText()))
					throw new BaseException("品牌号 请输入纯数字");
				if("".equals(this.edt_category.getText()))
					throw new BaseException("类别号不能为空");
				else if(!is_digit.isInteger(this.edt_category.getText()))
					throw new BaseException("类别号 请输入纯数字");
				if("".equals(this.edt_s_inf.getText()))
					this.s_id=0;
				else if(!is_digit.isInteger(this.edt_s_inf.getText()))
					throw new BaseException("服务号 请输入纯数字");
				else 
					{this.s_id=(Integer.parseInt(this.edt_s_inf.getText()));}
				
				if("".equals(this.edt_unit_price.getText()))
					{unit_price = 0.0;}
				else if(!is_digit.isDouble(this.edt_unit_price.getText()))
					throw new BaseException("单价 请输入纯数字");
				else if(Double.parseDouble(this.edt_unit_price.getText())<0)
					throw new BaseException("单价 不能小于0");
				else
					{unit_price = Double.parseDouble(this.edt_unit_price.getText());}	
				
				m.setM_name(edtName.getText());
				m.setBrand(jz_util.brand_manager.load_brand(Integer.parseInt(this.edt_brand.getText())));
				m.setCategory(jz_util.category_manager.load_category(Integer.parseInt(this.edt_category.getText())));
				m.setStandard(this.edt_standard.getText());
				m.setModel_number(this.edt_model_number.getText());
				m.setFlower_color(this.edt_flower_color.getText());
				m.setUnit_price(unit_price);
				m.setUnit_of_valuation(this.edt_unit_of_valuation.getText());
				m.setS_id(s_id);
				m_to_s mts = new m_to_s(m.getM_id(),s_id,1);
				if(flg==1)
				{
					jz_util.m_info_manager.add_m(m);
				}
				else if(flg==2)
				{
					jz_util.m_info_manager.change_m(m);
					jz_util.m_to_s_manager.change_m_to_s(mts);
				}
					
				else throw new BaseException("材料信息操作错误，flg="+flg);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}

}
