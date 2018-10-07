package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import _jz.jz_util;
import model.category;
import util.BaseException;;

public class FrmModify_category extends JDialog implements ActionListener{
	private category category = new category();
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private JButton btnOk = new JButton("确定");
	private JButton btnCancel = new JButton("取消");
	
	private JLabel lb_category_name = new JLabel("类别名：");
	private JLabel lb_describe = new JLabel("描述：");
	private JTextField edt_category_name = new JTextField(20);
	private JTextField edt_describe = new JTextField(20);
	
	public FrmModify_category(Frame f, String s, boolean b) {
		super(f, s, b);
	}
//==================================================================================
	private int flg=0;//用于判断选项,add_s=1,modify_s=2
	protected void add_category() {										//添加类别
		flg=1;
		fuyong();
	}
	protected void modify_category(category super_category) {			//修改类别
		flg=2;
		this.category = super_category;		
		fuyong();
		
		edt_category_name.setText(category.getCategory_name());//赋值当前类别名
		edt_describe.setText(category.getCategory_describe());//赋值当前描述
	}
	protected void del_category(category super_category) {						//删除类别
		try {
			jz_util.category_manager.del_category(super_category);
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
//公用部分==========================================================================
	public void fuyong() {
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		workPane.add(lb_category_name);
		workPane.add(edt_category_name);
		workPane.add(lb_describe);
		workPane.add(edt_describe);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(250, 200);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);
		
		this.btnCancel.addActionListener(this);
		this.btnOk.addActionListener(this);
	}
//响应设置==========================================================================
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnCancel)
			this.setVisible(false);
		else if(e.getSource()==this.btnOk){
			try {
				category.setCategory_name(edt_category_name.getText());
				category.setCategory_describe(this.edt_describe.getText());
				if(flg==1)
					jz_util.category_manager.add_category(category);
				else if(flg==2)
					jz_util.category_manager.change_category(category);
				else throw new BaseException("类别操作错误，flg="+flg);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}
}
