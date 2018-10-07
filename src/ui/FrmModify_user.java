package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import _jz.is_digit;
import _jz.jz_util;
import model.user;
import util.BaseException;

public class FrmModify_user extends JDialog implements ActionListener{
	private user user;
	
	private JPanel toolBar = new JPanel();
	private JPanel jp = new JPanel();
	private JPanel jp1 = new JPanel();private JPanel jp2 = new JPanel();
	private JPanel jp3 = new JPanel();private JPanel jp4 = new JPanel();
	
	private JButton btnOk = new JButton("确定");
	private JButton btnCancel = new JButton("取消");
	private JButton btnDel = new JButton("删除");
	
	private JLabel label_userid = new JLabel();
	private JLabel label_username = new JLabel();
	private JLabel label_userpsd = new JLabel();
	private JLabel label_level = new JLabel();
	
	private JTextField edt_username = new JTextField(25);
	private JTextField edt_userpsd = new JTextField(25);
	private JTextField edt_level = new JTextField(3);
	
	public FrmModify_user(Frmload_user f, String s, boolean b) {
		super(f,s,b);
	}
//=====================================================================================
	protected void modify_user(user super_user) {
		this.user = super_user;
		fuyong();
	}
//复用=================================================================================
	private void fuyong() {
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		toolBar.add(btnDel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		label_userid.setText("用户id    ："+this.user.getUser_id());
		label_username.setText("用户名    ：");
		label_userpsd.setText("用户密码：");
		label_level.setText("用户等级：");
		edt_username.setText(this.user.getUser_name());
		edt_userpsd.setText(this.user.getPsd());
		edt_level.setText(String.valueOf(this.user.getLevel()));
		
		jp.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp1.setLayout(new FlowLayout(FlowLayout.LEFT));jp2.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp3.setLayout(new FlowLayout(FlowLayout.LEFT));jp4.setLayout(new FlowLayout(FlowLayout.LEFT));
	
		jp1.setPreferredSize(new Dimension(300, 30));jp2.setPreferredSize(new Dimension(300, 50));
		jp3.setPreferredSize(new Dimension(300, 50));jp4.setPreferredSize(new Dimension(160, 50));
		
		jp1.add(label_userid);	 
		jp2.add(label_username); jp2.add(edt_username);
		jp3.add(label_userpsd);  jp3.add(edt_userpsd);
		jp4.add(label_level);	 jp4.add(edt_level);
		
		jp.add(jp1);jp.add(jp2);
		jp.add(jp3);jp.add(jp4);
		
		this.getContentPane().add(jp);
		
		this.setSize(350, 350);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		this.btnDel.addActionListener(this);
	}
//响应设置==============================================================================
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnCancel)
			this.setVisible(false);
		else if(e.getSource()==this.btnDel) {
			try {
				jz_util.user_manager.del_user(this.user);
				this.setVisible(false);
			} catch (BaseException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.btnOk){
			try {
				if("".equals(this.edt_level.getText()))
					throw new BaseException("等级不能为空");
				if(!is_digit.isInteger(this.edt_level.getText()))
					throw new BaseException("等级请输入纯数字");
				if(Integer.parseInt(this.edt_level.getText())<1 ||Integer.parseInt(this.edt_level.getText())>4)
					throw new BaseException("等级必须在1-4之间");
				
				int flg=0;
				user.setLevel(Integer.parseInt(this.edt_level.getText()));
				user.setPsd(this.edt_userpsd.getText());
				if(user.getUser_name().equals(this.edt_username.getText()))
					flg=1;
				user.setUser_name(this.edt_username.getText());
				jz_util.user_manager.change_user(user,flg);
				
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
	}
	
}
