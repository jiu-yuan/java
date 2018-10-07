package itf;

import java.util.List;

import model.m_to_s;
import util.BaseException;

public interface m_to_s_itf {
	
	//2.修改，若预算存在，则不能删除
	public m_to_s change_m_to_s(m_to_s mts)throws BaseException;
	
	//3.删除，若预算存在，则不能删除
	public void del_m_to_s(m_to_s mts)throws BaseException;
	
	//4.提取对应服务
	public int m_get_s(int m_id)throws BaseException;
	
	//5.提取对应
	public m_to_s load_m_to_s(int mts_id)throws BaseException;
}
