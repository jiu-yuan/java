package itf;

import java.util.List;

import model.brand;
import model.category;
import model.m_info;
import model.s_info;
import util.BaseException;

public interface m_info_itf {
	//1.添加材料
	//材料名称，所属类别，所属品牌，规格，型号，花色，单价，计价单位
	public m_info add_m(m_info m_inf)throws BaseException;
	
	//2.修改材料
	//材料名称，所属类别，所属品牌，规格，型号，花色，单价，计价单位
	public m_info change_m(m_info m_inf)throws BaseException;
	
	//3.删除材料，当有预算时，不能删除
	public void del_m(m_info m_inf)throws BaseException;
	
	//4.提取材料
	public List<m_info> load_m_info(String m_name, int brand_id, int category_id)throws BaseException;
	
}
