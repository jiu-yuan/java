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
import model.brand;
import util.BaseException;

public class FrmModify_brand extends JDialog implements ActionListener{
	private brand brand = new brand();
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private JButton btnOk = new JButton("确定");
	private JButton btnCancel = new JButton("取消");
	
	private JLabel lb_brand_name = new JLabel("品牌名：");
	private JLabel lb_describe = new JLabel("描述：");
	private JTextField edt_brand_name = new JTextField(20);
	private JTextField edt_describe = new JTextField(20);
	
	public FrmModify_brand(Frame f, String s, boolean b) {
		super(f, s, b);
	}
//========================================================================
	private int flg=0;//用于判断选项,add=1,modify=2
	protected void add_brand() {							//添加品牌
		flg = 1;
		fuyong();
	}
	protected void modify_brand(brand super_brand) {		//修改品牌
		flg = 2;
		this.brand = super_brand;//获取当前brand信息
		fuyong();
		
		edt_brand_name.setText(brand.getBrand_name());//赋值当前品牌名
		edt_describe.setText(brand.getBrand_describe());//赋值当前描述
	}
	protected void del_brand(brand super_brand) {			//删除品牌
		try {
			jz_util.brand_manager.del_brand(super_brand);
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
//公用部分=================================================================
	public void fuyong() {
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		workPane.add(lb_brand_name);
		workPane.add(edt_brand_name);
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
//响应设置=================================================================
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnCancel)
			this.setVisible(false);
		else if(e.getSource()==this.btnOk){
			try {
				brand.setBrand_name(edt_brand_name.getText());
				brand.setBrand_describe(this.edt_describe.getText());
				if(flg==1)
					jz_util.brand_manager.add_brand(brand);
				else if(flg==2)
					jz_util.brand_manager.change_brand(brand);
				else throw new BaseException("品牌操作错误，flg="+flg);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}
}
