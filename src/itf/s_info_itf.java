package itf;

import java.util.List;

import model.m_info;
import model.s_info;
import util.BaseException;

public interface s_info_itf {
	//1.添加服务
	//服务内容，服务等级，单价，计价单位，服务时间
	public s_info add_s(s_info s)throws BaseException;
	
	//2.修改服务
	//服务内容，服务等级，单价，计价单位，服务时间
	public s_info change_s(s_info s_inf)throws BaseException;
	
	//3.删除服务，当有预算时，不能删除
	public void del_s(s_info s_info)throws BaseException;
	
	//4.提取服务
	public List<s_info> load_s_info(String s_content,int time)throws BaseException;
	
	public List<s_info> load_s_byid(int s_id) throws BaseException;
}
