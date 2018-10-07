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
import model.category;
import util.BaseException;

public class Frmload_category extends JDialog implements ActionListener{
	public category category;
	private int item = 0;
	
	private JPanel toolBar = new JPanel();
	private JButton btnOk = new JButton("确定");
	private List<category> all_category = null;
	private Object tbl_category_title[]=category.tableTitles;//读取属性
	private Object tbl_category_data[][];//存每个字段
	private DefaultTableModel tab_category_model=new DefaultTableModel();
	private JTable data_table_category=new JTable(tab_category_model);//创建表格
	private TableRowSorter<DefaultTableModel> sort_category = new TableRowSorter<DefaultTableModel>(tab_category_model);
	
	public Frmload_category(FrmModify_m frmModify_m, String s, boolean b) {
		// TODO Auto-generated constructor stub
		super(frmModify_m, s, b);
		fuyong();
	}
	
	private void fuyong() {
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		JScrollPane JP2 = new JScrollPane(this.data_table_category);
		this.getContentPane().add(JP2);
		
		try {
			all_category=jz_util.category_manager.load_all();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tbl_category_data =  new Object[all_category.size()][category.tableTitles.length];
		for(int i=0;i<all_category.size();i++){
			for(int j=0;j<category.tableTitles.length;j++)
				tbl_category_data[i][j]=all_category.get(i).getCell(j);
		}
		tab_category_model.setDataVector(tbl_category_data,tbl_category_title);
		this.data_table_category.validate();//布局生效
		this.data_table_category.repaint();//重绘窗口
		
		//鼠标响应====================================================================
		this.data_table_category.getTableHeader().addMouseListener(new MouseAdapter() {//类别
	         @Override
	         public void mouseClicked(MouseEvent e) {
       	 	int i=data_table_category.columnAtPoint(e.getPoint());//获取点击的列
       	 	if(i<0) {sort_category.setRowFilter(null);return;}
       	 	data_table_category.setAutoCreateRowSorter(true);
               data_table_category.setRowSorter(sort_category);
	         }
	    });
		
		this.data_table_category.addMouseListener(new MouseAdapter (){//获取当前选中的类别号

			@Override
			public void mouseClicked(MouseEvent e) {
				int i=data_table_category.getSelectedRow();//获取显示的行
				item = data_table_category.convertRowIndexToModel(i);//获取实际的行
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
			category = all_category.get(item); 
			this.setVisible(false);
			return;
		}
	}

}
