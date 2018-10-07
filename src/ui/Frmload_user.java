package ui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import _jz.is_digit;
import _jz.jz_util;
import model.user;
import util.BaseException;

public class Frmload_user extends JDialog implements ActionListener{
	
	private JPanel jp = new JPanel();
	private JPanel jp_up = new JPanel();
	private JScrollPane jp_center = new JScrollPane();
	private JPanel jp_down = new JPanel();
	
	private JComboBox<String> jcb_fangshi = new JComboBox<String>();
	private JTextField edt_txt = new JTextField(25);
	private JButton btn_chaxun = new JButton("查询");
	private JButton btn_bgt = new JButton("显示预算");
	
	private List<user> all_user = null;
	private user cur_user = null;
	private int id = 0;
	private String name=null;
	private int level = 0;
	//用户
	private Object tbl_user_title[] = user.tableTitles;
	private Object tbl_user_data[][];
	private DefaultTableModel tab_user_model=new DefaultTableModel() {
		public boolean isCellEditable(int row,int column) {
			return false;
		}
	};
	private JTable data_table_user = new JTable(tab_user_model);
	

	TableRowSorter<DefaultTableModel> sort_user = new TableRowSorter<DefaultTableModel>(tab_user_model);
	
	public Frmload_user(FrmMain f, String s, boolean b) {
		super(f, s, b);
		fuyong();
	}
	
	private void fuyong() {
		jp.setLayout(new BorderLayout());
		jcb_fangshi.addItem("用户id");
		jcb_fangshi.addItem("用户名");
		jcb_fangshi.addItem("用户等级");
		jp_up.add(jcb_fangshi);
		jp_up.add(edt_txt);
		jp_up.add(btn_chaxun);
		jp_center = new JScrollPane(data_table_user);
		jp_down.add(btn_bgt);
		jp.add(jp_up,  BorderLayout.NORTH);
		jp.add(jp_center, BorderLayout.CENTER);
		jp.add(jp_down, BorderLayout.SOUTH);
		this.getContentPane().add(jp);
//===========================鼠标响应====================================================
	    this.data_table_user.getTableHeader().addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e) {
	    		int i=Frmload_user.this.data_table_user.columnAtPoint(e.getPoint());//获取点击的列
        	 	if(i<0) {sort_user.setRowFilter(null);return;}
        	 	Frmload_user.this.data_table_user.setAutoCreateRowSorter(true);
        	 	Frmload_user.this.data_table_user.setRowSorter(sort_user);
	    	}
	    });
	    this.data_table_user.addMouseListener(new MouseAdapter (){
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton()==MouseEvent.BUTTON1)//BUTTON1,鼠标左键
				{
					if(e.getClickCount()>1)
					{
						int i=Frmload_user.this.data_table_user.getSelectedRow();//获取显示的行
						if(i<0) 
							{return;}
						int item = Frmload_user.this.data_table_user.convertRowIndexToModel(i);//获取实际的行
						Frmload_user.this.reload_user_inf_table(item);//显示该用户
					}
				}
			}	
	    });
//=============================================================================================
	    this.reload_all_user_table();
	    
	    this.setSize(500, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
	    this.btn_chaxun.addActionListener(this);
	    this.btn_bgt.addActionListener(this);
	}
	
	private void reload_user_inf_table(int user_idx) {//当前用户信息
		if(user_idx<0)	return;
		cur_user = all_user.get(user_idx);
		FrmModify_user dlg = new FrmModify_user(this,"修改用户信息",true);
		dlg.modify_user(cur_user);
		dlg.setVisible(true);
		this.reload_all_user_table();
	}
	private void reload_all_user_table() {//所有用户
		try {
			if(all_user==null)
				all_user = jz_util.user_manager.load_all();
			
		}catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		tbl_user_data = new Object[all_user.size()][user.tableTitles.length];
		for(int i=0; i<all_user.size(); i++)
		for(int j=0; j<user.tableTitles.length; j++)
		{
			tbl_user_data[i][j] = all_user.get(i).getCell(j);
		}
		
		tab_user_model.setDataVector(tbl_user_data, tbl_user_title);
		this.data_table_user.validate();
		this.data_table_user.repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btn_bgt)
		{
			int i=this.data_table_user.getSelectedRow();//获取显示的行
			if(i<0) 
				{return;}
			int item = this.data_table_user.convertRowIndexToModel(i);//获取实际的行
			user usr = all_user.get(item);
			Frmload_bgt dlg = new Frmload_bgt(this,"预算信息", true, usr);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.btn_chaxun)
		{
			try {
				if("用户id".equals(this.jcb_fangshi.getSelectedItem()))
				{
					if("".equals(edt_txt.getText()))
						all_user = jz_util.user_manager.load_all();
					else if(!is_digit.isInteger(edt_txt.getText()))
						throw new BaseException("请输入纯数字");
					else {
						id = Integer.parseInt(edt_txt.getText());
						all_user = jz_util.user_manager.load_user_id(id);
					}
				}
				else if("用户名".equals(this.jcb_fangshi.getSelectedItem()))
				{
					name = edt_txt.getText();
					all_user = jz_util.user_manager.load_user_name(name);
				}
				else if("用户等级".equals(this.jcb_fangshi.getSelectedItem()))
				{
					if("".equals(edt_txt.getText()))
						all_user = jz_util.user_manager.load_all();
					else if(!is_digit.isInteger(edt_txt.getText()))
						throw new BaseException("请输入纯数字");
					else{
						level = Integer.parseInt(edt_txt.getText());
						all_user = jz_util.user_manager.load_user_level(level);
					}
				}
				this.reload_all_user_table();
			}
			catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}
	
}
