package itf;

import java.util.List;

import model.m_budget;
import model.m_info;
import model.user;
import util.BaseException;

public interface m_budget_itf {
	//1.添加材料预算
	public m_budget add_m_budget(m_budget m_bgt)throws BaseException;
	
	//2.修改材料预算
	public m_budget change_m_budget(m_budget m_bgt)throws BaseException;
	
	//3.删除材料预算
	public void del_m_budget(m_budget m_bgt)throws BaseException;
	
	//4.提取当前用户所有材料预算，每次提取，对单价进行重新核对;
	//若为管理员，则提取所有记录
	public List<m_budget> load_m_budget(user usr)throws BaseException;
}
