package ui;

import java.awt.BorderLayout;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import _jz.jz_util;
import model.m_budget;
import model.m_info;
import model.s_budget;
import model.s_info;
import model.user;
import util.BaseException;

public class Frm_rec extends JDialog implements ActionListener{
	List<m_info> all_m = new ArrayList<m_info>();
	List<s_info> all_s = new ArrayList<s_info>();
	int[] all_list = new int[10];
	int[] all_s_quantity = new int[10];
	private double total_m = 0;
	private double total_s = 0;
	//材料信息
	private Object tbl_m_title[] = m_info.tableTitles2;
	private Object tbl_m_data[][];
	DefaultTableModel tab_m_model = new DefaultTableModel();
	private JTable data_table_m = new JTable(tab_m_model);
	TableRowSorter<DefaultTableModel> sort_m = new TableRowSorter<DefaultTableModel>(tab_m_model);
	//服务信息
	private Object tbl_s_title[] = s_info.tableTitles2;
	private Object tbl_s_data[][];
	DefaultTableModel tab_s_model = new DefaultTableModel();
	private JTable data_table_s = new JTable(tab_s_model);
	TableRowSorter<DefaultTableModel> sort_s = new TableRowSorter<DefaultTableModel>(tab_s_model);	
	//结构
	private JScrollPane jsp_m;
	private JScrollPane jsp_s;
	private JPanel jp_left = new JPanel(new BorderLayout());
	private JPanel jp_right = new JPanel(new BorderLayout());
	private JPanel jp_btn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	private JPanel jp_left_title = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel jp_right_title = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JLabel label_m = new JLabel("材料信息");
	private JLabel label_s = new JLabel("服务信息");
	private JLabel label_total_m = new JLabel("材料价格合计：");
	private JLabel label_total_s = new JLabel("服务价格合计：");
	private JButton btn_ok = new JButton("应用");
	private JButton btn_cancel = new JButton("取消");
	
	public Frm_rec(FrmMain f, String s, boolean b) {
		// TODO Auto-generated constructor stub
		super(f,s,b);
		
		if("卧室样板".equals(s))
		{
			reload_bedroom();
		}
		else if("客厅样板".equals(s))
		{
			reload_living();
		}
		else if("厨房样板".equals(s))
		{
			reload_kitchen();
		}
		else if("卫生间样板".equals(s))
		{
			reload_toilet();
		}
		
		tbl_m_data =new Object[all_m.size()][m_info.tableTitles2.length];
		for(int i=0;i<all_m.size();i++)
		for(int j=0;j<m_info.tableTitles2.length;j++)
		{
			if(j==0) 
				{tbl_m_data[i][j]=i+1;continue;}//序号
			if(j==m_info.tableTitles2.length-1)
			{
				tbl_m_data[i][j]=this.all_list[i];
				this.total_m+=this.all_m.get(i).getUnit_price()*this.all_list[i];//材料单价*数量
				continue;
			}
			tbl_m_data[i][j]=all_m.get(i).getCell(j);
		}
		tbl_s_data =new Object[all_s.size()][s_info.tableTitles2.length];
		for(int i=0;i<all_s.size();i++)
		for(int j=0;j<s_info.tableTitles2.length;j++)
		{
			if(j==0) 
				{tbl_s_data[i][j]=i+1;continue;}//序号
			if(j==s_info.tableTitles2.length-1)
			{
				if("件".equals(all_s.get(i).getUnit_of_valuation()))
				{
					tbl_s_data[i][j]=1;
					all_s_quantity[i]=1;
					this.total_s+=this.all_s.get(i).getUnit_price()*1;
					continue;
				}
				else
				{
					tbl_s_data[i][j]=0;
					all_s_quantity[i]=0;
					continue;
				}
			}
			tbl_s_data[i][j]=all_s.get(i).getCell(j);
		}
		
		label_total_m.setText("材料价格合计："+this.total_m+" 元");
		label_total_s.setText("服务价格合计："+this.total_s+" 元（按尺寸计算的请添加到预算表后自行修改数量）");
		
		fuyong();
	}
//============================================================================================
	private void reload_bedroom() {//卧室材料
		try {
			int[] list = {1,3,5,8,12,13,14,18,20,22};
			int[] quantity = {1,5,50,2,10,2,1,3,4,1};
			int cnt=0;
			for(int i=0;i<10;i++)//10种材料
			{
				List<m_info> list_m = jz_util.m_info_manager.load_m_info("",0,list[i]);	
				s_info s_inf = new s_info();
				if(list_m.size()!=0)
				{
					this.all_m.add(list_m.get(0));//添加材料
					this.all_list[cnt] = quantity[i];//添加材料的数量
					cnt++;
					
					int s_id = 0;
					s_id = jz_util.m_to_s_manager.m_get_s(list_m.get(0).getM_id());//查看材料的服务
					if(s_id!=0 && jz_util.s_info_manager.load_s_byid(s_id).size()!=0)
					{
						s_inf = jz_util.s_info_manager.load_s_byid(s_id).get(0);
						this.all_s.add(s_inf);//添加服务
					}
				}	
			}
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
	private void reload_living() {//客厅材料
		try {
			int[] list = {1,3,5,12,13,14,18,20};
			int[] quantity = {2,10,100,20,4,2,5,8};
			int cnt=0;
			for(int i=0;i<8;i++)//8种材料
			{
				List<m_info> list_m = jz_util.m_info_manager.load_m_info("",0,list[i]);	
				s_info s_inf = new s_info();
				if(list_m.size()!=0)
				{
					this.all_m.add(list_m.get(0));//添加材料
					this.all_list[cnt] = quantity[i];//添加材料的数量
					cnt++;
					
					int s_id = 0;
					s_id = jz_util.m_to_s_manager.m_get_s(list_m.get(0).getM_id());//查看材料的服务
					if(s_id!=0 && jz_util.s_info_manager.load_s_byid(s_id).size()!=0)
					{
						s_inf = jz_util.s_info_manager.load_s_byid(s_id).get(0);
						this.all_s.add(s_inf);//添加服务
					}
				}	
			}
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
	private void reload_kitchen() {//厨房材料
		try {
			int[] list = {1,3,5,8,13,18,21};
			int[] quantity = {1,3,30,5,1,3,2};
			int cnt=0;
			for(int i=0;i<7;i++)//7种材料
			{
				List<m_info> list_m = jz_util.m_info_manager.load_m_info("",0,list[i]);	
				s_info s_inf = new s_info();
				if(list_m.size()!=0)
				{
					this.all_m.add(list_m.get(0));//添加材料
					this.all_list[cnt] = quantity[i];//添加材料的数量
					cnt++;
					
					int s_id = 0;
					s_id = jz_util.m_to_s_manager.m_get_s(list_m.get(0).getM_id());//查看材料的服务
					if(s_id!=0 && jz_util.s_info_manager.load_s_byid(s_id).size()!=0)
					{
						s_inf = jz_util.s_info_manager.load_s_byid(s_id).get(0);
						this.all_s.add(s_inf);//添加服务
					}
				}	
			}
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
	private void reload_toilet() {//卫生间材料
		try {
			int[] list = {1,3,5,8,13,18,21};
			int[] quantity = {2,2,20,2,1,1,2};
			int cnt=0;
			for(int i=0;i<7;i++)//7种材料
			{
				List<m_info> list_m = jz_util.m_info_manager.load_m_info("",0,list[i]);	
				s_info s_inf = new s_info();
				if(list_m.size()!=0)
				{
					this.all_m.add(list_m.get(0));//添加材料
					this.all_list[cnt] = quantity[i];//添加材料的数量
					cnt++;
					
					int s_id = 0;
					s_id = jz_util.m_to_s_manager.m_get_s(list_m.get(0).getM_id());//查看材料的服务
					if(s_id!=0 && jz_util.s_info_manager.load_s_byid(s_id).size()!=0)
					{
						s_inf = jz_util.s_info_manager.load_s_byid(s_id).get(0);
						this.all_s.add(s_inf);//添加服务
					}
				}	
			}
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
//============================================================================================
	private void fuyong() {
		jsp_m = new JScrollPane(this.data_table_m);
		jsp_s = new JScrollPane(this.data_table_s);
		
		jp_left_title.add(label_m);jp_left.add(jp_left_title, BorderLayout.NORTH);
		jp_left.add(jsp_m, BorderLayout.CENTER);
		jp_left.add(label_total_m, BorderLayout.SOUTH);
		jp_right_title.add(label_s);jp_right.add(jp_right_title, BorderLayout.NORTH);
		jp_right.add(jsp_s, BorderLayout.CENTER);
		jp_right.add(label_total_s, BorderLayout.SOUTH);
		jp_btn.add(btn_ok);jp_btn.add(btn_cancel);
		this.getContentPane().add(jp_left, BorderLayout.CENTER);
		this.getContentPane().add(jp_right, BorderLayout.EAST);
		this.getContentPane().add(jp_btn, BorderLayout.SOUTH);
		
		tab_m_model.setDataVector(tbl_m_data,tbl_m_title);
		this.data_table_m.validate();
		this.data_table_m.repaint();
		tab_s_model.setDataVector(tbl_s_data,tbl_s_title);
		this.data_table_s.validate();
		this.data_table_s.repaint();
		
		this.setSize(1000, 600);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btn_cancel.addActionListener(this);
		this.btn_ok.addActionListener(this);
	}
//============================================================================================
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btn_cancel)
			this.setVisible(false);
		else if(e.getSource()==this.btn_ok)
		{
			for(int i=0; i<this.all_m.size(); i++)
			{
				m_budget m_bgt = new m_budget();
				m_bgt.setQuantity(this.all_list[i]);//数量
				m_bgt.setUnit_price(all_m.get(i).getUnit_price());//单价
				m_bgt.setM(all_m.get(i));//商品
				m_bgt.setUser(user.currentLoginUser);
				try {
					jz_util.m_budget_manager.add_m_budget(m_bgt);
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			for(int i=0;i<this.all_s.size(); i++)
			{
				s_budget s_bgt = new s_budget();
				s_bgt.setS(all_s.get(i));
				s_bgt.setQuantity(all_s_quantity[i]);
				s_bgt.setRequired_time(all_s.get(i).getService_time()*all_s_quantity[i]);
				s_bgt.setRemark("（来自推荐方案）");
				s_bgt.setUser(user.currentLoginUser);
				try {
					jz_util.s_budget_manager.add_s_budget(s_bgt);
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			JOptionPane.showMessageDialog(null, "应用成功");  
			this.setVisible(false);
		}
	}

}
