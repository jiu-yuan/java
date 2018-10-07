package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import _jz.jz_util;
import model.brand;
import util.BaseException;

public class Frmload_brand extends JDialog implements ActionListener{
	public brand brand;//通过此条读取当前选中品牌
	private int item = 0;
	
	private JPanel toolBar = new JPanel();
	private JButton btnOk = new JButton("确定");
	private List<brand> all_brand = null;
	private Object tbl_brand_title[]=brand.tableTitles;//读取属性
	private Object tbl_brand_data[][];//存每个字段
	private DefaultTableModel tab_brand_model=new DefaultTableModel();
	private JTable data_table_brand=new JTable(tab_brand_model);//创建表格
	private TableRowSorter<DefaultTableModel> sort_brand = new TableRowSorter<DefaultTableModel>(tab_brand_model);//排序器
						//FrmAdd_m frmAdd_m, String s, boolean b

	public Frmload_brand(FrmModify_m frmModify_m, String s, boolean b) {//用于FrmModify_m
		// TODO Auto-generated constructor stub
		super(frmModify_m, s, b);
		fuyong();
	}
	
	private void fuyong() {
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		JScrollPane JP2 = new JScrollPane(this.data_table_brand);
		this.getContentPane().add(JP2);
		//表格信息
		try {
			all_brand=jz_util.brand_manager.load_all();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tbl_brand_data =  new Object[all_brand.size()][brand.tableTitles.length];
		for(int i=0;i<all_brand.size();i++){
			for(int j=0;j<brand.tableTitles.length;j++)
				tbl_brand_data[i][j]=all_brand.get(i).getCell(j);
		}
		tab_brand_model.setDataVector(tbl_brand_data,tbl_brand_title);
		this.data_table_brand.validate();//布局生效
		this.data_table_brand.repaint();//重绘窗口
		
		//鼠标点击事件===============================================================
		this.data_table_brand.getTableHeader().addMouseListener(new MouseAdapter() {//表头排序
	         @Override
	         public void mouseClicked(MouseEvent e) {
       	 	int i=data_table_brand.columnAtPoint(e.getPoint());//获取点击的列
       	 	if(i<0) {sort_brand.setRowFilter(null);return;}
       	 	data_table_brand.setAutoCreateRowSorter(true);//设置可自动排序
               data_table_brand.setRowSorter(sort_brand);//添加排序器
	         }
	    });
		
		this.data_table_brand.addMouseListener(new MouseAdapter (){//获取当前选中的品牌号
			@Override
		public void mouseClicked(MouseEvent e) {
				int i=data_table_brand.getSelectedRow();//获取显示的行
				item = data_table_brand.convertRowIndexToModel(i);//获取实际的行
			if(item<0) {
				return;
			}
		}
	    	
	    });
		this.setSize(500, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width + this.getWidth()) / 2,
				(int) (height - this.getHeight()*0.8) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		
		this.setVisible(true);
				
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnOk) {
			brand = all_brand.get(item); 
			this.setVisible(false);
			return;
		}
	}
	
}
