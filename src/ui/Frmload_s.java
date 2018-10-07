package ui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import _jz.jz_util;
import model.s_budget;
import model.s_info;
import model.user;
import util.BaseException;

public class Frmload_s extends JDialog implements ActionListener{
	protected s_info s_inf = new s_info();
	private List<s_info> all_s = null;
	private int flg=0;
	private int s_id=0;
	
	private JPanel jp_btn = new JPanel();
	private JScrollPane jsp_s;
	
	private JButton btnOk = new JButton("确定");
	
	private JButton btn_add_s_bgt = new JButton("购买服务");
	
	private JButton btn_add_s = new JButton("新增服务");
	private JButton btn_modify_s = new JButton("修改服务");
	private JButton btn_del_s = new JButton("删除服务");
	//服务信息
	private Object tbl_s_title[] = s_info.tableTitles;
	private Object tbl_s_data[][];
	DefaultTableModel tab_s_model = new DefaultTableModel();
	private JTable data_table_s = new JTable(tab_s_model);
	TableRowSorter<DefaultTableModel> sort_s = new TableRowSorter<DefaultTableModel>(tab_s_model);	
	
	public Frmload_s(JFrame f, String s, boolean b) {
		super(f, s, b);
		fuyong();
		
	}
	public Frmload_s(FrmModify_m f, String s, boolean b) {
		// TODO Auto-generated constructor stub
		super(f, s, b);
		flg=1;//若为1，只显示确定按钮
		fuyong();
	}
	public Frmload_s(FrmModify_m_budget f, String s, boolean b,int s_id) {
		// TODO Auto-generated constructor stub
		super(f, s, b);
		this.s_id = s_id;
		flg=3;
		fuyong();
	}
	//公用部分===================================================================================================
	private void fuyong() {
		//添加按钮
		if(flg==1||flg==3)
			{jp_btn.add(btnOk);}
		else
		{
			if(user.currentLoginUser.getLevel()>3) {//管理员按钮
				jp_btn.add(btn_add_s); jp_btn.add(btn_modify_s); jp_btn.add(btn_del_s);
			}
			else {
				jp_btn.add(btn_add_s_bgt);//用户购买服务按钮
			}
		}
		
		jsp_s = new JScrollPane(data_table_s);
		//添加服务表
		this.getContentPane().add(jp_btn, BorderLayout.NORTH); 
		this.getContentPane().add(jsp_s, BorderLayout.CENTER);
		//鼠标响应
		data_table_s.getTableHeader().addMouseListener(new MouseAdapter() {//表头排序
	         @Override
	         public void mouseClicked(MouseEvent e) {
        	 	int i=data_table_s.columnAtPoint(e.getPoint());//获取点击的列
        	 	if(i<0) {sort_s.setRowFilter(null);return;}
        	 	data_table_s.setAutoCreateRowSorter(true);//设置可自动排序
        	 	data_table_s.setRowSorter(sort_s);//添加排序器
	         }
	    });
		this.setSize(800, 400);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.reload_s_table();//显示服务界面

		this.btn_add_s.addActionListener(this);
		this.btn_modify_s.addActionListener(this);
		this.btn_del_s.addActionListener(this);
		this.btn_add_s_bgt.addActionListener(this);
		this.btnOk.addActionListener(this);
		
	}
//================================================================================
	private void reload_s_table() {//load所有服务信息
		try {
			if(flg==3)
				{all_s = jz_util.s_info_manager.load_s_byid(s_id);}
			else
				all_s = jz_util.s_info_manager.load_s_info("",0);
		}catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tbl_s_data =new Object[all_s.size()][s_info.tableTitles.length];
		for(int i=0;i<all_s.size();i++)
		for(int j=0;j<s_info.tableTitles.length;j++)
		{
			if(j==0) 
				{tbl_s_data[i][j]=i+1;continue;}//序号
			tbl_s_data[i][j]=all_s.get(i).getCell(j);
		}
		
		tab_s_model.setDataVector(tbl_s_data,tbl_s_title);
		this.data_table_s.validate();
		this.data_table_s.repaint();
	}
//响应设置==========================================================================
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnOk)
		{
			int i = this.data_table_s.getSelectedRow();
			if(i<0) {
				this.setVisible(false);return;
			}
			int item = this.data_table_s.convertRowIndexToModel(i);
			this.s_inf = all_s.get(item);
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.btn_add_s_bgt)
		{
			int i = this.data_table_s.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "请选择购买的服务", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			int item = this.data_table_s.convertRowIndexToModel(i);
			FrmModify_s_budget dlg = new FrmModify_s_budget(this, "购买服务", true);
			dlg.add_s_budget(this.all_s.get(item));
			dlg.setVisible(true);
			
			this.setVisible(false);
			
		}
		else if(e.getSource()==this.btn_add_s)
		{
			FrmModify_s dlg = new FrmModify_s(this, "新增服务", true);
			dlg.add_s();
			dlg.setVisible(true);
			this.reload_s_table();//显示服务界面
		}
		else if(e.getSource()==this.btn_modify_s)
		{
			int i = this.data_table_s.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "请选择修改的服务", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			int item = this.data_table_s.convertRowIndexToModel(i);
			FrmModify_s dlg = new FrmModify_s(this, "修改服务", true);
			dlg.modify_s(this.all_s.get(item));
			dlg.setVisible(true);
			this.reload_s_table();//显示服务界面
		}
		else if(e.getSource()==this.btn_del_s) {
			int i = this.data_table_s.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "请选择删除的服务", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			int item = this.data_table_s.convertRowIndexToModel(i);
			FrmModify_s dlg = new FrmModify_s(this, "删除服务", true);
			dlg.del_s(this.all_s.get(item));
			this.reload_s_table();//显示服务界面
		}
	}
}
