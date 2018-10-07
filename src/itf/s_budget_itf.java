package itf;

import java.util.List;

import model.m_budget;
import model.s_budget;
import model.user;
import util.BaseException;

public interface s_budget_itf {
	//1.添加服务预算
	public s_budget add_s_budget(s_budget s_bgt)throws BaseException;
	
	//2.修改服务预算
	public s_budget change_s_budget(s_budget s_budget)throws BaseException;
	
	//3.删除服务预算
	public void del_s_budget(s_budget s_budget)throws BaseException;
	
	//4.提取当前用户所有服务预算，每次提取，对单价进行重新核对;
	//若为管理员，则提取所有记录
	public List<s_budget> load_s_budget(user usr)throws BaseException;
}
