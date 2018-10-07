package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import _jz.is_digit;
import _jz.jz_util;
import model.category;
import model.m_budget;
import model.m_info;
import model.s_budget;
import model.s_info;
import model.user;
import util.BaseException;

public class FrmModify_m_budget extends JDialog implements ActionListener{
	private m_info m = new m_info();
	private s_info s_inf = new s_info();
	private m_budget m_budget = new m_budget();
	private List<m_info> all_m = new ArrayList<m_info>();
	private int s_id = 0;
	private int s_quantity=0;
	private JPanel toolBar = new JPanel();
	private JPanel jp = new JPanel();
	private JPanel jp1 = new JPanel();private JPanel jp2 = new JPanel();
	private JPanel jp3 = new JPanel();private JPanel jp4 = new JPanel();
	private JPanel jp5 = new JPanel();private JPanel jp6 = new JPanel();
	private JPanel jp7 = new JPanel();private JPanel jp8 = new JPanel();
	private JPanel jp9 = new JPanel();

	private JButton btnOk = new JButton("确定");
	private JButton btnCancel = new JButton("取消");
	private JButton btnDel = new JButton("删除");
	private JButton btn_s_inf= new JButton("查看现有服务");
	private JLabel labelName = new JLabel();//"商品名："
	private JLabel label_brand = new JLabel();//"品牌名："
	private JLabel label_category = new JLabel();//"类别名："
	private JLabel label_model_number = new JLabel();//"型号    ："
	private JLabel label_unit_price = new JLabel();//"单价    ："
	private JLabel label_standard = new JLabel("规格    ：");
	private JLabel label_flower_color = new JLabel("花色    ：");
	private JLabel label_quantity = new JLabel("数量     ：");
	private JLabel label_s_quantity = new JLabel("服务数量    ：");
	
	
	private JComboBox<String> jcb_standard = new JComboBox<String>();
	private JComboBox<String> jcb_flower_color = new JComboBox<String>();
	
	private JTextField edt_quantity = new JTextField(10);
	private JTextField edt_s_quantity = new JTextField(8);
	
	public FrmModify_m_budget(FrmMain f, String s, boolean b) {
		super(f,s,b);	
	}
//=======================================================================	
	private int flg=0;//用于判断选项,add_s=1,modify_s=2
	protected void add_m_budget(m_info super_m) {				//添加材料预算
		flg=1;
		this.m = super_m;
		fuyong();
	}
	protected void modify_m_budget(m_budget super_m_budget) {	//修改材料预算
		flg=2;
		this.m_budget = super_m_budget;
		this.m = super_m_budget.getM();
		jcb_standard.addItem(m_budget.getM().getStandard());
		jcb_flower_color.addItem(m_budget.getM().getFlower_color());
		fuyong();

	}								//删除材料预算,在btnDel按钮中
//公用部分================================================================
	private void fuyong() {
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		if(flg==2)
			toolBar.add(btnDel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		labelName.setText("商品名："+m.getM_name());
		label_brand.setText("品牌名："+m.getBrand().getBrand_name());
		label_category.setText("类别名："+m.getCategory().getCategory_name());
		label_model_number.setText("型号    ："+m.getModel_number());
		label_unit_price.setText("单价    ：");
		//提取所有该商品条目
		try {	
			all_m = jz_util.m_info_manager.load_m_info(
					m.getM_name(),
					m.getBrand().getBrand_id(), 
					m.getCategory().getCategory_id());
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			s_id = jz_util.m_to_s_manager.m_get_s(m.getM_id());
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//向下拉框添加数据
		for(int i=0; i<all_m.size(); i++)
		{
			int flag_standard = 0; int flag_fcolor = 0;
			for(int j=0; j<jcb_standard.getItemCount(); j++)//判断下拉框内是否已有此规格
			{
				if(jcb_standard.getItemAt(j).equals(all_m.get(i).getStandard()))
					{flag_standard = 1;break;}
			}
			for(int j=0; j<jcb_flower_color.getItemCount(); j++)
			{
				if(jcb_flower_color.getItemAt(j).equals(all_m.get(i).getFlower_color()))
					{flag_fcolor = 1;break;}
			}
			if(flag_standard==0) 
				jcb_standard.addItem(all_m.get(i).getStandard());
			if(flag_fcolor==0)
				jcb_flower_color.addItem(all_m.get(i).getFlower_color());		
		}
		
		jp.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp1.setLayout(new FlowLayout(FlowLayout.LEFT));jp2.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp3.setLayout(new FlowLayout(FlowLayout.LEFT));jp4.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp5.setLayout(new FlowLayout(FlowLayout.LEFT));jp6.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp7.setLayout(new FlowLayout(FlowLayout.LEFT));jp8.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp9.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		jp1.setPreferredSize(new Dimension(350, 30));jp2.setPreferredSize(new Dimension(350, 30));
		jp3.setPreferredSize(new Dimension(350, 30));jp4.setPreferredSize(new Dimension(350, 30));
		jp5.setPreferredSize(new Dimension(350, 30));jp6.setPreferredSize(new Dimension(350, 30));
		jp7.setPreferredSize(new Dimension(350, 30));jp8.setPreferredSize(new Dimension(350, 30));
		jp9.setPreferredSize(new Dimension(350, 30));
		
		jp1.add(labelName);
		jp2.add(label_brand);
		jp3.add(label_category);
		jp4.add(label_model_number);
		jp5.add(label_unit_price);
		jp6.add(label_standard);
		jp6.add(jcb_standard);
		jp7.add(label_flower_color);
		jp7.add(jcb_flower_color);
		jp8.add(label_quantity);
		jp8.add(edt_quantity);
		jp9.add(label_s_quantity);
		jp9.add(edt_s_quantity);
		jp9.add(btn_s_inf);
		
		jp.add(jp1);jp.add(jp2);
		jp.add(jp3);jp.add(jp4);
		jp.add(jp5);jp.add(jp6);
		jp.add(jp7);jp.add(jp8);
		if(flg==1)
			jp.add(jp9);
		
		this.getContentPane().add(jp);
		
		this.setSize(400, 410);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		this.btnDel.addActionListener(this);
		this.jcb_standard.addActionListener(this);
		this.jcb_flower_color.addActionListener(this);
		this.btn_s_inf.addActionListener(this);
	}
//响应设置================================================================
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btn_s_inf)
		{
			Frmload_s fs = new Frmload_s(this,"服务信息", true,s_id); 
			fs.setVisible(true);
		}
		else if(e.getSource()==this.jcb_standard)//规格下拉框
		{
			for(int i=0; i<all_m.size(); i++)
			{
				if(jcb_standard.getSelectedItem().equals(all_m.get(i).getStandard()) 
						&& jcb_flower_color.getSelectedItem().equals(all_m.get(i).getFlower_color()))
				{
					m = all_m.get(i);
					break;
				}
			}
				label_unit_price.setText("单价    ："+m.getUnit_price());
		}
		else if(e.getSource()==this.jcb_flower_color)//花色下拉框
		{
			for(int i=0; i<all_m.size(); i++)
			{
				if(jcb_standard.getSelectedItem().equals(all_m.get(i).getStandard()) 
						&& jcb_flower_color.getSelectedItem().equals(all_m.get(i).getFlower_color()))
				{
					m = all_m.get(i);
					break;
				}
			}
				label_unit_price.setText("单价    ："+m.getUnit_price());
		}
		else if(e.getSource()==this.btnCancel)
			this.setVisible(false);
		else if(e.getSource()==this.btnDel) {
			try {
				jz_util.m_budget_manager.del_m_budget(this.m_budget);
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
					throw new BaseException("请输入纯数字");
				if(Integer.parseInt(this.edt_quantity.getText())<1||Integer.parseInt(this.edt_quantity.getText())>1000)
					throw new BaseException("数量必须在1~1000");
				if("".equals(this.edt_s_quantity.getText()))
					s_quantity=0;
				else if(!is_digit.isInteger(this.edt_s_quantity.getText()))
					throw new BaseException("请输入纯数字");
				else if(Integer.parseInt(this.edt_s_quantity.getText())<0||Integer.parseInt(this.edt_s_quantity.getText())>1000)
					throw new BaseException("服务数量必须在0~1000");
				else
					s_quantity = Integer.parseInt(this.edt_s_quantity.getText());
				
				m_budget.setQuantity(Integer.parseInt(this.edt_quantity.getText()));
				m_budget.setUnit_price(m.getUnit_price());
				m_budget.setM(m);
				m_budget.setUser(user.currentLoginUser);
				
				if(flg==1)
				{
					jz_util.m_budget_manager.add_m_budget(this.m_budget);
					if(jz_util.s_info_manager.load_s_byid(s_id).size()!=0 && s_quantity!=0)
					{
						s_inf = jz_util.s_info_manager.load_s_byid(s_id).get(0);
						s_budget s_bgt = new s_budget();
						s_bgt.setS(s_inf);
						s_bgt.setQuantity(s_quantity);
						s_bgt.setRequired_time(s_inf.getService_time()*s_quantity);
						s_bgt.setRemark("(自动添加)商品名："+m_budget.getM().getM_name());
						s_bgt.setUser(m_budget.getUser());
						jz_util.s_budget_manager.add_s_budget(s_bgt);
					}
				}
				else if(flg==2)
					jz_util.m_budget_manager.change_m_budget(this.m_budget);
				else throw new BaseException("材料预算操作错误，flg="+flg);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}

}
