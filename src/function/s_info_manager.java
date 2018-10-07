package function;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import itf.s_info_itf;
import model.brand;
import model.s_info;
import model.s_info;
import model.s_info;
import util.BaseException;
import util.BusinessException;
import util.HBUtil;

public class s_info_manager implements s_info_itf{

	@Override //1.添加服务
	//服务内容，服务等级，单价，计价单位，服务时间
	public s_info add_s(s_info s_inf) throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		Transaction tx = s.beginTransaction();
		
		if(s_inf.getService_content()==null || "".equals(s_inf.getService_content()) || s_inf.getService_content().length()>20) 
			{throw new BusinessException("服务内容必须是1-20个字母");}
		
			
		String hql = "from s_info where service_content = '"+s_inf.getService_content()+"'";
		Query query = s.createQuery(hql);
		if(query.list().size()!=0) 
		{
			List<s_info> list_s = query.list();
			for(int i=0; i<list_s.size(); i++) 
			{
				if(list_s.get(i).getService_level()==s_inf.getService_level() 
						&& list_s.get(i).getService_time()==s_inf.getService_time())
						{throw new BusinessException("此服务已存在");}
			}
		}
		s.save(s_inf);
		tx.commit();
		s.close();
		return s_inf;
	}

	@Override //2.修改服务
	//服务内容，服务等级，单价，计价单位，服务时间
	public s_info change_s(s_info s_info) throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		Transaction tx = s.beginTransaction();
		
		if(s_info.getService_content()==null || "".equals(s_info.getService_content()) || s_info.getService_content().length()>20) 
			{throw new BusinessException("服务内容必须是1-20个字母");}
		if(s_info.getService_level()<1||s_info.getService_level()>5) 
			{throw new BusinessException("服务等级须在1~5之间");}
		if(s_info.getService_time()<0)
			{throw new BusinessException("服务时间不能小于0");}
		if(s_info.getUnit_price()<0)
			{throw new BusinessException("单价不能小于0");}
		
		String hql = "from s_budget where s_id = "+s_info.getS_id();
		Query query = s.createQuery(hql);
		if(query.list().size()!=0)
			{throw new BusinessException("此服务在用户预算内，不能修改.");}
		
		s.update(s_info);
		tx.commit();
		s.close();
		return s_info;
	}

	@Override //3.删除服务，当有预算时，不能删除
	public void del_s(s_info s_info) throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		Transaction tx = s.beginTransaction();
		
		String hql = "from s_budget where s_id = "+s_info.getS_id();
		Query query = s.createQuery(hql);
		if(query.list().size()!=0)
			{throw new BusinessException("此服务在用户预算内，不能删除.");}
		else
		{
			
		}
		s.delete(s_info);
		tx.commit();
		s.close();
	}

	@Override //4.提取所有服务
	public List<s_info> load_s_info(String s_content, int time) throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		
		List<s_info> list_s = new ArrayList<s_info>();
		String hql = null;
		if("".equals(s_content))
			{hql = "from s_info ";}
		else
		{
			if(time==0)
				{hql = "from s_info where service_content = '"+s_content+"'";}
			else
				{hql = "from s_info where service_content = '"+s_content
				+"' and service_time = "+time;}
		}
			
		Query query = s.createQuery(hql);
		if(query.list().size()!=0)
			list_s = query.list();
		
		s.close();
		return list_s;
	}
	public List<s_info> load_s_byid(int s_id) throws BaseException {
		Session s = HBUtil.getSession();
		
		List<s_info> list_s = new ArrayList<s_info>();
		String hql = null;
		if(s_id==0)
			{return list_s;}
		else
		{
			hql = "from s_info where s_id = "+s_id;
		}
			
		Query query = s.createQuery(hql);
		if(query.list().size()!=0)
			list_s = query.list();
		
		s.close();
		return list_s;
	}

}
