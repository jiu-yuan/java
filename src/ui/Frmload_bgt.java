package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import model.s_budget;
import model.user;
import util.BaseException;

public class Frmload_bgt extends JDialog implements ActionListener{
	private List<m_budget> all_m_budget = null;
	private List<s_budget> all_s_budget = null;
	private user usr = new user();
	
	private Object tbl_m_budget_title[] = m_budget.tableTitles;
	private Object tbl_m_budget_data[][];
	private DefaultTableModel tab_m_budget_model = new DefaultTableModel();
	private JTable data_table_m_budget = new JTable(tab_m_budget_model);
	private TableRowSorter<DefaultTableModel> sort_m_budget = new TableRowSorter<DefaultTableModel>(tab_m_budget_model);
	//服务预算
	private Object tbl_s_budget_title[] = s_budget.tableTitles;
	private Object tbl_s_budget_data[][];
	private DefaultTableModel tab_s_budget_model = new DefaultTableModel();
	private JTable data_table_s_budget = new JTable(tab_s_budget_model);
	private TableRowSorter<DefaultTableModel> sort_s_budget = new TableRowSorter<DefaultTableModel>(tab_s_budget_model);
	
	private JPanel jp = new JPanel(new BorderLayout());
	private JPanel jp_left = new JPanel(new BorderLayout());
	private JPanel jp_right = new JPanel(new BorderLayout());
	private JScrollPane jp1 = new JScrollPane();private JScrollPane jp2 = new JScrollPane();
	private JLabel label_m_bgt = new JLabel("材料预算表");
	private JLabel label_s_bgt = new JLabel("服务预算表");
	private JPanel jp_left_n = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel jp_right_n = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel jp_s_c = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JButton btn_total = new JButton("合计");
	
	public Frmload_bgt(Frmload_user f, String s, boolean b, user super_usr) {
		// TODO Auto-generated constructor stub
		super(f,s,b);
		this.usr = super_usr;
		fuyong();
	}
//=============================================================================================
	private void fuyong() {
		jp1 = new JScrollPane(this.data_table_m_budget);
		jp2 = new JScrollPane(this.data_table_s_budget);
		jp_left_n.add(label_m_bgt);jp_right_n.add(label_s_bgt);
		jp_left.add(jp_left_n, BorderLayout.NORTH); jp_left.add(jp1,BorderLayout.CENTER);
		jp_right.add(jp_right_n, BorderLayout.NORTH); jp_right.add(jp2, BorderLayout.CENTER);
		jp.add(jp_left, BorderLayout.WEST);
		jp.add(jp_right, BorderLayout.CENTER);
		jp.add(btn_total, BorderLayout.SOUTH);
		this.getContentPane().add(jp);
		this.reload_m_budget_table();
		this.reload_s_budget_table();
		
		 this.setSize(1000, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btn_total.addActionListener(this);
	}
//=============================================================================================
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btn_total)
		{
			Frmtotal dlg = new Frmtotal(this,"合计", true, usr);
			dlg.setVisible(true);
		}
	}
//=============================================================================================
	private void reload_m_budget_table() {//当前用户材料预算
		try {
			all_m_budget = jz_util.m_budget_manager.load_m_budget(usr);
			
		}catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tbl_m_budget_data = new Object[all_m_budget.size()][m_budget.tableTitles.length];
		for(int i=0; i<all_m_budget.size(); i++)
		for(int j=0; j<m_budget.tableTitles.length; j++)
		{
			if(j==0) 
				{tbl_m_budget_data[i][j]=i+1;continue;}//序号
			tbl_m_budget_data[i][j] = all_m_budget.get(i).getCell(j);
		}
		
		tab_m_budget_model.setDataVector(tbl_m_budget_data, tbl_m_budget_title);
		this.data_table_m_budget.validate();
		this.data_table_m_budget.repaint();
	}
	protected void reload_s_budget_table() {//当前用户服务预算
		try {
			all_s_budget = jz_util.s_budget_manager.load_s_budget(usr);
			
		}catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		tbl_s_budget_data = new Object[all_s_budget.size()][s_budget.tableTitles.length];
		for(int i=0; i<all_s_budget.size(); i++)
		for(int j=0; j<s_budget.tableTitles.length; j++)
		{
			if(j==0) 
				{tbl_s_budget_data[i][j]=i+1;continue;}//序号
			tbl_s_budget_data[i][j] = all_s_budget.get(i).getCell(j);
		}
		
		tab_s_budget_model.setDataVector(tbl_s_budget_data, tbl_s_budget_title);
		this.data_table_s_budget.validate();
		this.data_table_s_budget.repaint();
	}
	
}
