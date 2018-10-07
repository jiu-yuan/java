package itf;

import java.util.List;

import model.user;
import util.BaseException;

public interface user_itf {
	/**
	 * 注册：
	 * 要求用户名不能重复，不能为空
	 * 两次输入的密码必须一致，密码不能为空
	 * 如果注册失败，则抛出异常
	 * @param user_name
	 * @param psd  密码
	 * @param psd2 重复输入的密码
	 * @return
	 * @throws BaseException
	 */
	public user reg(String user_name, String psd,String psd2) throws BaseException;
	
	/**
	 * 登陆
	 * 1、如果用户不存在或者密码错误，抛出一个异常
	 * 2、如果认证成功，则返回当前用户信息
	 * @param userid
	 * @param psd
	 * @return
	 * @throws BaseException
	 */
	public user login(String user_name,String psd)throws BaseException;
	
	/**
	 * 修改密码
	 * 如果没有成功修改，则抛出异常
	 * @param user    当前用户
	 * @param old_psd  原密码
	 * @param new_psd  新密码
	 * @param new_psd2 重复输入的新密码
	 */
	public void change_psd(user user, String old_psd, String new_psd, String new_psd2)throws BaseException;
	//提取所有用户
	public List<user> load_all()throws BaseException;
	
	public List<user> load_user_id(int user_id)throws BaseException;
	public List<user> load_user_name(String user_name)throws BaseException;
	public List<user> load_user_level(int user_level)throws BaseException;
	public void change_user(user usr, int flg)throws BaseException;
	public void del_user(user usr)throws BaseException;
}