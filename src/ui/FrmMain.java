package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import _jz.jz_util;
import model.brand;
import model.category;
import model.m_budget;
import model.m_info;
import model.s_budget;
import model.s_info;
import model.user;
import util.BaseException;



public class FrmMain extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JMenuBar menubar = new JMenuBar();
	
    private JMenu menu_brand = new JMenu("品牌");
    private JMenu menu_category = new JMenu("类别");
    private JMenu menu_rec = new JMenu("推荐方案");
    private JMenu menu_manage = new JMenu("账号管理");
    private JMenu menu_more = new JMenu("商品");
    private JMenu menu_s = new JMenu("服务管理");
    
    private JMenuItem  menuItem_add_brand = new JMenuItem("添加品牌");
    private JMenuItem  menuItem_load_brand = new JMenuItem("查看品牌");
    private JMenuItem  menuItem_modify_brand = new JMenuItem("修改品牌");
    private JMenuItem  menuItem_delete_brand = new JMenuItem("删除品牌");
    
    private JMenuItem  menuItem_add_category = new JMenuItem("添加类别");
    private JMenuItem  menuItem_load_category = new JMenuItem("查看类别");
    private JMenuItem  menuItem_modify_category = new JMenuItem("修改类别");
    private JMenuItem  menuItem_delete_category = new JMenuItem("删除类别");
    
    private JMenuItem  menuItem_rec_first = new JMenuItem("卧室");
    private JMenuItem  menuItem_rec_second = new JMenuItem("客厅");
    private JMenuItem  menuItem_rec_third = new JMenuItem("厨房");
    private JMenuItem  menuItem_rec_fourth = new JMenuItem("卫生间");
    
    private JMenuItem  menuItem_modifyPwd = new JMenuItem("密码修改");
    private JMenuItem  menuItem_check_user = new JMenuItem("查看所有用户"); 
    
    private JMenuItem  menuItem_add_m = new JMenuItem("添加材料");
    //private JMenuItem  menuItem_load_m = new JMenuItem("显示所有商品");
    private JMenuItem  menuItem_modify_m = new JMenuItem("修改材料");
    private JMenuItem  menuItem_delete_m = new JMenuItem("删除材料");
    
    private JMenuItem  menuItem_load_s = new JMenuItem("显示所有服务");
        
	private FrmLogin dlgLogin=null;
	private JPanel statusBar = new JPanel();
	private JScrollPane left_jp = new JScrollPane();
	private JScrollPane center_jp = new JScrollPane();
	private JPanel right_model = new JPanel(new BorderLayout());//必须有这个参数才是border布局
	private JScrollPane right_jp_m = new JScrollPane();
	private JScrollPane right_jp_s = new JScrollPane();
	private JPanel right_south = new JPanel();
	private JButton m_budget_add = new JButton("添加商品");
	private JButton s_budget_add = new JButton("添加服务");
	private JButton btn_total = new JButton("合计");
	//中间模块商品查询
	private JPanel center_model = new JPanel(new BorderLayout());
	private JPanel center_top_jp = new JPanel();
	private JLabel label_m_inf = new JLabel("商品名：");
	private JTextField edt_m_inf = new JTextField(50);
	private JButton btn_m_inf = new JButton("查询");
	
	//品牌
	private Object tbl_brand_title[]=brand.tableTitles;//读取属性
	private Object tbl_brand_data[][];//存每个字段
	DefaultTableModel tab_brand_model=new DefaultTableModel();
	private JTable data_table_brand=new JTable(tab_brand_model);//创建表格
	TableRowSorter<DefaultTableModel> sort_brand = new TableRowSorter<DefaultTableModel>(tab_brand_model);//排序器
	//类别
	private Object tbl_category_title[] = category.tableTitles;
	private Object tbl_category_data[][];
	DefaultTableModel tab_category_model=new DefaultTableModel();
	private JTable data_table_category=new JTable(tab_category_model);
	TableRowSorter<DefaultTableModel> sort_category = new TableRowSorter<DefaultTableModel>(tab_category_model);
	//材料
	private Object tbl_m_title[] = m_info.tableTitles;
	private Object tbl_m_data[][];
	DefaultTableModel tab_m_model =new DefaultTableModel() {
		public boolean isCellEditable(int row,int column) {
			return false;
		}
	};
	private JTable data_table_m = new JTable(tab_m_model);
	TableRowSorter<DefaultTableModel> sort_m = new TableRowSorter<DefaultTableModel>(tab_m_model);
	//材料预算
	private Object tbl_m_budget_title[] = m_budget.tableTitles;
	private Object tbl_m_budget_data[][];
	DefaultTableModel tab_m_budget_model = new DefaultTableModel();
	private JTable data_table_m_budget = new JTable(tab_m_budget_model);
	TableRowSorter<DefaultTableModel> sort_m_budget = new TableRowSorter<DefaultTableModel>(tab_m_budget_model);
	//服务预算
	private Object tbl_s_budget_title[] = s_budget.tableTitles;
	private Object tbl_s_budget_data[][];
	DefaultTableModel tab_s_budget_model = new DefaultTableModel();
	private JTable data_table_s_budget = new JTable(tab_s_budget_model);
	TableRowSorter<DefaultTableModel> sort_s_budget = new TableRowSorter<DefaultTableModel>(tab_s_budget_model);
	
	
	private brand cur_brand=null;//当前品牌
	private category cur_category = null;//当前类别
	
	List<brand> all_brand = null;
	List<category> all_category = null;
	List<m_info> all_m = null;
	List<m_budget> all_m_budget = null;
	List<s_budget> all_s_budget = null;
	
	private void reload_brand_table(){//所有品牌信息
		try {
			all_brand=jz_util.brand_manager.load_all();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tbl_brand_data =  new Object[all_brand.size()][brand.tableTitles.length];
		for(int i=0;i<all_brand.size();i++)
		for(int j=0;j<brand.tableTitles.length;j++)
		{
			if(j==0) 
				{tbl_brand_data[i][j]=i+1;continue;}//序号
			tbl_brand_data[i][j]=all_brand.get(i).getCell(j);
		}
		tab_brand_model.setDataVector(tbl_brand_data,tbl_brand_title);
		this.data_table_brand.validate();//布局生效
		this.data_table_brand.repaint();//重绘窗口
	}
	private void reload_category_table()//所有类别信息
	{
		try {
			all_category = jz_util.category_manager.load_all();
		}catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tbl_category_data = new Object[all_category.size()][category.tableTitles.length];
		for(int i=0; i<all_category.size(); i++)
		for(int j=0; j<category.tableTitles.length; j++) 
		{
			if(j==0) 
				{tbl_category_data[i][j]=i+1;continue;}//序号
			tbl_category_data[i][j] = all_category.get(i).getCell(j);
		}
		tab_category_model.setDataVector(tbl_category_data, tbl_category_title);
		this.data_table_category.validate();
		this.data_table_category.repaint();
	}
	private void reload_brand_m_table(int brand_idx){//当前品牌的材料信息
		if(brand_idx<0) return;
		cur_brand=all_brand.get(brand_idx);
		try {
			all_m=jz_util.m_info_manager.load_m_info("", cur_brand.getBrand_id(),0);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tbl_m_data =new Object[all_m.size()][m_info.tableTitles.length];
		for(int i=0;i<all_m.size();i++)
		for(int j=0;j<m_info.tableTitles.length;j++)
		{
			if(j==0) 
			{tbl_m_data[i][j]=i+1;continue;}//序号
			tbl_m_data[i][j]=all_m.get(i).getCell(j);
		}
		
		tab_m_model.setDataVector(tbl_m_data,tbl_m_title);
		this.data_table_m.validate();
		this.data_table_m.repaint();
	}
	private void reload_category_m_table(int category_idx){//当前类别的材料信息
		if(category_idx<0) return;
		cur_category = all_category.get(category_idx);
		try {
			all_m = jz_util.m_info_manager.load_m_info("", 0, cur_category.getCategory_id());
		}catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tbl_m_data =new Object[all_m.size()][m_info.tableTitles.length];
		for(int i=0;i<all_m.size();i++)
		for(int j=0;j<m_info.tableTitles.length;j++)
		{
			if(j==0) 
				{tbl_m_data[i][j]=i+1;continue;}//序号
			tbl_m_data[i][j]=all_m.get(i).getCell(j);
		}
		
		tab_m_model.setDataVector(tbl_m_data,tbl_m_title);
		this.data_table_m.validate();//布局生效
		this.data_table_m.repaint();//重绘窗口
	}
	private void reload_m_table() {//所有材料信息
		try {
			all_m = jz_util.m_info_manager.load_m_info("", 0, 0);
		}catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tbl_m_data =new Object[all_m.size()][m_info.tableTitles.length];
		for(int i=0;i<all_m.size();i++)
		for(int j=0;j<m_info.tableTitles.length;j++)
		{
			if(j==0) 
				{tbl_m_data[i][j]=i+1;continue;}//序号
			tbl_m_data[i][j]=all_m.get(i).getCell(j);
		}
		
		tab_m_model.setDataVector(tbl_m_data,tbl_m_title);
		this.data_table_m.validate();
		this.data_table_m.repaint();
	}
	private void reload_m_budget_table() {//当前用户材料预算
		try {
			all_m_budget = jz_util.m_budget_manager.load_m_budget(user.currentLoginUser);
			
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
			all_s_budget = jz_util.s_budget_manager.load_s_budget(user.currentLoginUser);
			
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
	
	
	public FrmMain(){
		
		this.setExtendedState(Frame.MAXIMIZED_BOTH);//窗口初始最大化
		this.setTitle("家装预算系统");
		dlgLogin=new FrmLogin(this,"登陆界面",true);
		dlgLogin.setVisible(true);//显示界面
	    //=====================================菜单=====================================================
			//品牌   //加至UI中							//添加响应
	    this.menu_brand.add(this.menuItem_load_brand); this.menuItem_load_brand.addActionListener(this);
	    	//类别
	    this.menu_category.add(this.menuItem_load_category); this.menuItem_load_category.addActionListener(this);
	    	//材料
	  //  this.menu_more.add(this.menuItem_load_m); this.menuItem_load_m.addActionListener(this);
	    	//推荐方案
	    this.menu_rec.add(this.menuItem_rec_first); this.menuItem_rec_first.addActionListener(this);
	    this.menu_rec.add(this.menuItem_rec_second); this.menuItem_rec_second.addActionListener(this);
	    this.menu_rec.add(this.menuItem_rec_third); this.menuItem_rec_third.addActionListener(this);
	    this.menu_rec.add(this.menuItem_rec_fourth); this.menuItem_rec_fourth.addActionListener(this);
	    	//账号管理
	    this.menu_manage.add(this.menuItem_modifyPwd); this.menuItem_modifyPwd.addActionListener(this);
	    	//服务
	    this.menu_s.add(this.menuItem_load_s); this.menuItem_load_s.addActionListener(this);
	    	//预算按钮
	    this.m_budget_add.addActionListener(this); 
	    this.s_budget_add.addActionListener(this); 
	    this.btn_total.addActionListener(this);
	    this.btn_m_inf.addActionListener(this);
		if(user.currentLoginUser.getLevel()>3)
		{
			//品牌
			 this.menu_brand.add(this.menuItem_add_brand); this.menuItem_add_brand.addActionListener(this);
			 this.menu_brand.add(this.menuItem_modify_brand); this.menuItem_modify_brand.addActionListener(this);
			 this.menu_brand.add(this.menuItem_delete_brand); this.menuItem_delete_brand.addActionListener(this);
			//类别
			 this.menu_category.add(this.menuItem_add_category); this.menuItem_add_category.addActionListener(this);
			 this.menu_category.add(this.menuItem_modify_category); this.menuItem_modify_category.addActionListener(this);
			 this.menu_category.add(this.menuItem_delete_category); this.menuItem_delete_category.addActionListener(this);
			//材料
		    this.menu_more.add(this.menuItem_add_m); this.menuItem_add_m.addActionListener(this);
		    this.menu_more.add(this.menuItem_modify_m); this.menuItem_modify_m.addActionListener(this);
		    this.menu_more.add(this.menuItem_delete_m); this.menuItem_delete_m.addActionListener(this);
		    //查询用户
		    this.menu_manage.add(this.menuItem_check_user); this.menuItem_check_user.addActionListener(this);
		}
	    if(user.currentLoginUser.getLevel()>=3)
	    	menubar.add(menu_more);
	    menubar.add(menu_brand);
	    menubar.add(menu_category);
	    if(user.currentLoginUser.getLevel()<3)
	    	menubar.add(menu_rec);
	    menubar.add(menu_manage);
	    menubar.add(menu_s);
	    this.setJMenuBar(menubar);
//=================================主界面================================================================
	    //赋值模块内的数据
	    left_jp = new JScrollPane(this.data_table_brand);
	    center_jp = new JScrollPane(this.data_table_m);
	    right_jp_m = new JScrollPane(this.data_table_m_budget);
	    right_jp_s = new JScrollPane(this.data_table_s_budget);
	    //中间容器添加模块
	    center_top_jp.add(label_m_inf);
	    center_top_jp.add(edt_m_inf);
	    center_top_jp.add(btn_m_inf);
	    center_model.add(center_top_jp,BorderLayout.NORTH);
	    center_model.add(center_jp,BorderLayout.CENTER);
	    //右容器添加模块
	    right_model.add(right_jp_m, BorderLayout.NORTH);
	    right_model.add(right_jp_s, BorderLayout.CENTER);
	    
	    right_south.add(m_budget_add);
	    right_south.add(s_budget_add);
	    right_south.add(btn_total);
	    right_model.add(right_south, BorderLayout.SOUTH);
	    	    
	    //添加到root容器
	    this.getContentPane().add(left_jp, BorderLayout.WEST);
	    this.getContentPane().add(center_model, BorderLayout.CENTER);
	    if(user.currentLoginUser.getLevel()<3)
	    	this.getContentPane().add(right_model, BorderLayout.EAST);	    
//========================================鼠标响应========================================================
	    	//=======================tabel设置表头排序=================================================
	    	//===========================品牌==================================================
	    this.data_table_brand.getTableHeader().addMouseListener(new MouseAdapter() {
	         @Override
	         public void mouseClicked(MouseEvent e) {
        	 	int i=FrmMain.this.data_table_brand.columnAtPoint(e.getPoint());//获取点击的列
        	 	if(i<0) {sort_brand.setRowFilter(null);return;}
        	 	FrmMain.this.data_table_brand.setAutoCreateRowSorter(true);//设置可自动排序
                FrmMain.this.data_table_brand.setRowSorter(sort_brand);//添加排序器
	         }
	    });//=============================类别=================================================
	    this.data_table_category.getTableHeader().addMouseListener(new MouseAdapter() {//
	         @Override
	         public void mouseClicked(MouseEvent e) {
        	 	int i=FrmMain.this.data_table_category.columnAtPoint(e.getPoint());//获取点击的列
        	 	if(i<0) {sort_category.setRowFilter(null);return;}
        	 	FrmMain.this.data_table_category.setAutoCreateRowSorter(true);
                FrmMain.this.data_table_category.setRowSorter(sort_category);
	         }
	    });//===========================商品/材料==============================================
	    this.data_table_m.getTableHeader().addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e) {
	    		int i=FrmMain.this.data_table_m.columnAtPoint(e.getPoint());//获取点击的列
        	 	if(i<0) {sort_m.setRowFilter(null);return;}
        	 	FrmMain.this.data_table_m.setAutoCreateRowSorter(true);
                FrmMain.this.data_table_m.setRowSorter(sort_m);
	    	}
	    });//===========================材料预算================================================
	    this.data_table_m_budget.getTableHeader().addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e) {
	    		int i=FrmMain.this.data_table_m_budget.columnAtPoint(e.getPoint());//获取点击的列
        	 	if(i<0) {sort_m_budget.setRowFilter(null);return;}
        	 	FrmMain.this.data_table_m_budget.setAutoCreateRowSorter(true);
                FrmMain.this.data_table_m_budget.setRowSorter(sort_m_budget);
	    	}
	    });//===========================服务预算================================================
	    this.data_table_s_budget.getTableHeader().addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e) {
	    		int i=FrmMain.this.data_table_s_budget.columnAtPoint(e.getPoint());//获取点击的列
        	 	if(i<0) {sort_s_budget.setRowFilter(null);return;}
        	 	FrmMain.this.data_table_s_budget.setAutoCreateRowSorter(true);
                FrmMain.this.data_table_s_budget.setRowSorter(sort_s_budget);
	    	}
	    });
	  //=================================表格内选中行响应===========================================
	  //========================品牌表==========================================================
	    this.data_table_brand.addMouseListener(new MouseAdapter (){
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=FrmMain.this.data_table_brand.getSelectedRow();//获取显示的行
				if(i<0) 
					{return;}
				int item = FrmMain.this.data_table_brand.convertRowIndexToModel(i);//获取实际的行
				edt_m_inf.setText("");
				FrmMain.this.reload_brand_m_table(item);//显示该品牌的商品
			} 	
	    });//===================类别表==========================================================
	    this.data_table_category.addMouseListener(new MouseAdapter (){
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=FrmMain.this.data_table_category.getSelectedRow();
				if(i<0) 
					{return;}
				int item = FrmMain.this.data_table_category.convertRowIndexToModel(i);//获取实际的行
				edt_m_inf.setText("");
				FrmMain.this.reload_category_m_table(item);//显示该类别的商品
			}	    	
	    });//===================材料表===========================================================
	    this.data_table_m.addMouseListener(new MouseAdapter (){
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton()==MouseEvent.BUTTON1)//BUTTON1,鼠标左键
				{
					if(user.currentLoginUser.getLevel()>3 &&e.getClickCount()>1)
					{
						int i = FrmMain.this.data_table_m.getSelectedRow();
						if(i<0) 
							{return;}
						int j = FrmMain.this.data_table_m.convertRowIndexToModel(i);//获取实际的行
						FrmModify_m dlg = new FrmModify_m(this, "修改材料", true);
						dlg.modify_m(FrmMain.this.all_m.get(j));
						dlg.setVisible(true);
						
						String str = edt_m_inf.getText();
						if(!"".equals(str))
						{
							try 
							{
								
								all_m = jz_util.m_info_manager.load_m_info(str, 0, 0);
								
							} catch (BaseException e1) {
								JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
								return;
							}
							tbl_m_data =new Object[all_m.size()][m_info.tableTitles.length];
							for(int i2=0;i2<all_m.size();i2++)
							for(int j2=0;j2<m_info.tableTitles.length;j2++)
							{
								if(j2==0) 
									{tbl_m_data[i2][j2]=i2+1;continue;}//序号
								tbl_m_data[i2][j2]=all_m.get(i2).getCell(j2);
							}
							
							tab_m_model.setDataVector(tbl_m_data,tbl_m_title);
							FrmMain.this.data_table_m.validate();
							FrmMain.this.data_table_m.repaint();
						}
						else
						{
							int item = -1;
							if((item = FrmMain.this.data_table_category.getSelectedRow())>=0)//显示类别材料
								{FrmMain.this.reload_category_m_table(item);}
							else if((item = FrmMain.this.data_table_brand.getSelectedRow())>=0)//显示品牌材料
								{FrmMain.this.reload_brand_m_table(item);}
							else 
								{FrmMain.this.reload_m_table();}
						}
						
					}
				}
			}	    	
	    });
	    //===================材料预算表=====================================================
	    this.data_table_m_budget.addMouseListener(new MouseAdapter (){
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=FrmMain.this.data_table_m_budget.getSelectedRow();
				if(i<0) 
					{return;}
				int item = FrmMain.this.data_table_m_budget.convertRowIndexToModel(i);//获取实际的行
				FrmModify_m_budget dlg=new FrmModify_m_budget(FrmMain.this, "修改商品预算", true);
				dlg.modify_m_budget(FrmMain.this.all_m_budget.get(item));
				dlg.setVisible(true);
				FrmMain.this.reload_m_budget_table();//显示商品预算
				FrmMain.this.reload_s_budget_table();//显示服务预算
			}
	    });//====================服务预算表=====================================================
	    this.data_table_s_budget.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e) {
				int i=FrmMain.this.data_table_s_budget.getSelectedRow();
				if(i<0) 
					{return;}
				int item = FrmMain.this.data_table_s_budget.convertRowIndexToModel(i);//获取实际的行
				FrmModify_s_budget dlg=new FrmModify_s_budget(FrmMain.this, "修改服务预算", true);
				dlg.modify_s_budget(FrmMain.this.all_s_budget.get(item));
				dlg.setVisible(true);
				FrmMain.this.reload_s_budget_table();//显示服务预算
			}
	    });
	    //=================================================================================================
	    this.reload_brand_table();//显示品牌界面
	    this.reload_m_budget_table();//显示商品预算界面
	    this.reload_s_budget_table();//显示服务预算界面
	    this.reload_m_table();//显示所有商品
	    //状态栏
	    statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
	    JLabel label=new JLabel("您好!"+user.getCurrentLoginUser().getUser_name());//修改成   您好！+登陆用户名
	    statusBar.add(label);
	    this.getContentPane().add(statusBar,BorderLayout.SOUTH);
	    this.addWindowListener(new WindowAdapter(){   
	    	public void windowClosing(WindowEvent e){ 
	    		System.exit(0);
             }
        });
	    this.setVisible(true);
	}
//=======================================响应设置==========================================================
	@Override
	public void actionPerformed(ActionEvent e) {
//===============================品牌=====================================================================
		//============添加品牌==============================
		if(e.getSource()==this.menuItem_add_brand)
		{
			FrmModify_brand dlg=new FrmModify_brand(this,"添加品牌",true);
			dlg.add_brand();
			dlg.setVisible(true);
			this.reload_brand_table();//显示品牌界面
		}//==========显示品牌===============================
		else if(e.getSource()==this.menuItem_load_brand)
		{						//取消类别的选中行,否则添加完商品时刷新判断会有问题
			FrmMain.this.data_table_category.clearSelection();
			if(left_jp.equals(new JScrollPane(this.data_table_brand)));
			else
			{
				//必须先remove,再重新替换pane值
			    this.getContentPane().remove(left_jp);
			    left_jp = new JScrollPane(this.data_table_brand);
			    this.getContentPane().add(left_jp, BorderLayout.WEST);
			    
			    this.reload_brand_table();//显示品牌界面
			    this.setVisible(true);
			}
		}//==========修改品牌=============================
		else if(e.getSource()==this.menuItem_modify_brand)
		{
			int i=FrmMain.this.data_table_brand.getSelectedRow();
			
			if(i<0) {
				JOptionPane.showMessageDialog(null, "请选择品牌", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			int item = FrmMain.this.data_table_brand.convertRowIndexToModel(i);//获取实际的行
			FrmModify_brand dlg=new FrmModify_brand(this,"修改品牌",true);
			dlg.modify_brand(this.all_brand.get(item));
			dlg.setVisible(true);
			this.reload_brand_table();//显示品牌界面
		}//==========删除品牌===============================
		else if(e.getSource()==this.menuItem_delete_brand)
		{
			int i=FrmMain.this.data_table_brand.getSelectedRow();
			
			if(i<0) {
				JOptionPane.showMessageDialog(null, "请选择品牌", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			int item = FrmMain.this.data_table_brand.convertRowIndexToModel(i);//获取实际的行
			FrmModify_brand dlg=new FrmModify_brand(this,"删除品牌",true);
			dlg.del_brand(this.all_brand.get(item));
			this.reload_brand_table();
			
		}
//======================================类别===============================================================
		//==============添加类别===============================
		else if(e.getSource()==this.menuItem_add_category)
		{
			FrmModify_category dlg=new FrmModify_category(this,"添加类别",true);
			dlg.add_category();
			dlg.setVisible(true);
			this.reload_category_table();//显示类别界面
			
		}//==============显示类别==============================
		else if(e.getSource()==this.menuItem_load_category)
		{
			FrmMain.this.data_table_brand.clearSelection();//取消品牌的选中行,否则添加完商品时刷新判断会有问题
			if(left_jp.equals(new JScrollPane(this.data_table_category)));
			else
			{
				this.getContentPane().remove(left_jp);
				left_jp = new JScrollPane(this.data_table_category);
				this.getContentPane().add(left_jp, BorderLayout.WEST);
				this.reload_category_table();//显示类别界面
				this.setVisible(true);
			}
		}//=============修改类别==============================
		else if(e.getSource()==this.menuItem_modify_category)
		{
			int i=FrmMain.this.data_table_category.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "请选择类别", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			int item = FrmMain.this.data_table_category.convertRowIndexToModel(i);//获取实际的行
			FrmModify_category dlg = new FrmModify_category(this, "修改类别", true);
			dlg.modify_category(this.all_category.get(item));
			dlg.setVisible(true);
			this.reload_category_table();//显示类别界面
		}//==============删除类别==============================
		else if(e.getSource()==this.menuItem_delete_category)
		{
			int i=FrmMain.this.data_table_category.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "请选择类别", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			int item = FrmMain.this.data_table_category.convertRowIndexToModel(i);//获取实际的行
			FrmModify_category dlg = new FrmModify_category(this, "删除类别", true);
			dlg.del_category(this.all_category.get(item));
			this.reload_category_table();//显示类别界面
		}
//======================================推荐方案============================================================
		else if(e.getSource()==this.menuItem_rec_first)
		{
			Frm_rec dlg = new Frm_rec(this, "卧室样板", true);
			dlg.setVisible(true);
			this.reload_m_budget_table();
			this.reload_s_budget_table();
		}
		else if(e.getSource()==this.menuItem_rec_second)
		{
			Frm_rec dlg = new Frm_rec(this, "客厅样板", true);
			dlg.setVisible(true);
			this.reload_m_budget_table();
			this.reload_s_budget_table();
		}
		else if(e.getSource()==this.menuItem_rec_third)
		{
			Frm_rec dlg = new Frm_rec(this, "厨房样板", true);
			dlg.setVisible(true);
			this.reload_m_budget_table();
			this.reload_s_budget_table();
		}
		else if(e.getSource()==this.menuItem_rec_fourth)
		{
			Frm_rec dlg = new Frm_rec(this, "卫生间样板", true);
			dlg.setVisible(true);
			this.reload_m_budget_table();
			this.reload_s_budget_table();
		}
//======================================帐号管理============================================================
		//==============修改密码=====================
		else if(e.getSource()==this.menuItem_modifyPwd)
		{
			FrmModifyPwd dlg=new FrmModifyPwd(this,"密码修改",true);
			dlg.setVisible(true);
		}//=============查看用户=====================
		else if(e.getSource()==this.menuItem_check_user)
		{
			Frmload_user dlg = new Frmload_user(this, "用户列表",true);
			dlg.setVisible(true);
		}
//=====================================材料/商品===========================================================
		//================添加材料========================
		else if(e.getSource()==this.menuItem_add_m)
		{
			FrmModify_m dlg = new FrmModify_m(this, "添加材料", true);
			dlg.add_m();
			dlg.setVisible(true);
			
			int item = -1;
			if((item = FrmMain.this.data_table_category.getSelectedRow())>=0)//显示类别材料
				{this.reload_category_m_table(item);}
			else if((item = FrmMain.this.data_table_brand.getSelectedRow())>=0)//显示品牌材料
				{this.reload_brand_m_table(item);}
			else 
				{this.reload_m_table();}
		}//================显示材料========================
//		else if(e.getSource()==this.menuItem_load_m)
//		{
//			FrmMain.this.data_table_brand.clearSelection();//取消品牌的选中行,否则添加完商品时刷新判断会有问题
//			FrmMain.this.data_table_category.clearSelection();//取消类别的选中行
//			this.getContentPane().remove(center_jp);
//			center_jp = new JScrollPane(this.data_table_m);
//			this.getContentPane().add(center_jp, BorderLayout.CENTER);
//			this.reload_m_table();//显示材料界面
//			this.setVisible(true);
//		}//================修改材料========================
		else if(e.getSource()==this.menuItem_modify_m)
		{
			int i=FrmMain.this.data_table_m.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "请选择材料", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			int j = FrmMain.this.data_table_m.convertRowIndexToModel(i);//获取实际的行
			FrmModify_m dlg = new FrmModify_m(this, "修改材料", true);
			dlg.modify_m(this.all_m.get(j));
			dlg.setVisible(true);
			
			int item = -1;
			if((item = FrmMain.this.data_table_category.getSelectedRow())>=0)//显示类别材料
				{this.reload_category_m_table(item);}
			else if((item = FrmMain.this.data_table_brand.getSelectedRow())>=0)//显示品牌材料
				{this.reload_brand_m_table(item);}
			else 
				{this.reload_m_table();}
		}//================删除材料========================
		else if(e.getSource()==this.menuItem_delete_m)
		{
			int i=FrmMain.this.data_table_m.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "请选择材料", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			int j = FrmMain.this.data_table_m.convertRowIndexToModel(i);//获取实际的行
			FrmModify_m dlg = new FrmModify_m(this, "修改材料", true);
			dlg.del_m(this.all_m.get(j));
			
			int item = -1;
			if((item = FrmMain.this.data_table_category.getSelectedRow())>=0)//显示类别材料
				{this.reload_category_m_table(item);}
			else if((item = FrmMain.this.data_table_brand.getSelectedRow())>=0)//显示品牌材料
				{this.reload_brand_m_table(item);}
			else 
				{this.reload_m_table();}
		}
//=========================================商品预算========================================================
		//==============添加商品按钮===============
		else if(e.getSource()==this.m_budget_add)
		{
			int i = FrmMain.this.data_table_m.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "请选择商品", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			int j = FrmMain.this.data_table_m.convertRowIndexToModel(i);//获取实际的行
			FrmModify_m_budget dlg=new FrmModify_m_budget(this,"添加商品到预算",true);
			dlg.add_m_budget(this.all_m.get(j));
			dlg.setVisible(true);
			this.reload_m_budget_table();
			this.reload_s_budget_table();
		}//===============添加服务按钮===============
		else if(e.getSource()==this.s_budget_add)
		{
			Frmload_s dlg = new Frmload_s(this, "显示所有服务", true);
			dlg.setVisible(true);
			this.reload_s_budget_table();
		}//===============合计按钮===================
		else if(e.getSource()==this.btn_total)
		{
			Frmtotal dlg = new Frmtotal(this,"合计",true);
			dlg.setVisible(true);
		}
//==========================================服务===========================================================
		else if(e.getSource()==this.menuItem_load_s)//显示所有服务
		{
			Frmload_s dlg = new Frmload_s(this, "显示所有服务", true);
			dlg.setVisible(true);
			this.reload_s_budget_table();
		}
//========================================中间模块，查询====================================================
		else if(e.getSource()==this.btn_m_inf)//查询商品
		{
			try 
			{
				String str = edt_m_inf.getText();
				all_m = jz_util.m_info_manager.load_m_info(str, 0, 0);
				
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			tbl_m_data =new Object[all_m.size()][m_info.tableTitles.length];
			for(int i=0;i<all_m.size();i++)
			for(int j=0;j<m_info.tableTitles.length;j++)
			{
				if(j==0) 
					{tbl_m_data[i][j]=i+1;continue;}//序号
				tbl_m_data[i][j]=all_m.get(i).getCell(j);
			}
			
			tab_m_model.setDataVector(tbl_m_data,tbl_m_title);
			this.data_table_m.validate();
			this.data_table_m.repaint();
			
			FrmMain.this.data_table_brand.clearSelection();//取消品牌的选中行,否则添加完商品时刷新判断会有问题
			FrmMain.this.data_table_category.clearSelection();//取消类别的选中行
		}
	}

}
