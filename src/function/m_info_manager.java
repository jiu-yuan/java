package function;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;

import _jz.jz_util;

import org.hibernate.Session;
import org.hibernate.Transaction;

import itf.m_info_itf;
import model.brand;
import model.category;
import model.m_info;
import model.m_to_s;
import model.s_info;
import util.BaseException;
import util.BusinessException;
import util.HBUtil;

public class m_info_manager implements m_info_itf{

	@Override //1.添加材料
	//材料名称，所属类别，所属品牌，规格，型号，花色，单价，计价单位
	public m_info add_m(m_info m_inf) throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		Transaction tx = s.beginTransaction();
		
		
		if(m_inf.getM_name()==null || "".equals(m_inf.getM_name()) || m_inf.getM_name().length()>20) 
			{throw new BusinessException("商品名必须是1-20个字母");}
		
		brand bd = m_inf.getBrand();
		category cate = m_inf.getCategory();
		String hql = "from m_info where m_name = '"+m_inf.getM_name()+"'";
		Query query = s.createQuery(hql);
		if(query.list().size()!=0) 
		{
			List<m_info> list_m = query.list();
			for(int i=0; i<list_m.size(); i++) 
			{
				if(list_m.get(i).getBrand().getBrand_id()!=bd.getBrand_id() 
						|| list_m.get(i).getCategory().getCategory_id()!=cate.getCategory_id())
					{throw new BusinessException("此商品已存在");}
				if(list_m.get(i).getStandard().equals(m_inf.getStandard())
						&& list_m.get(i).getModel_number().equals(m_inf.getModel_number()) 
						&& list_m.get(i).getFlower_color().equals(m_inf.getFlower_color()))
					{throw new BusinessException("此商品已存在");}
			}
		}
		brand b = s.get(brand.class, bd.getBrand_id());
		if(b==null) {throw new BusinessException("品牌号不存在");}
		category cate2 = s.get(category.class, cate.getCategory_id());
		if(cate2==null) {throw new BusinessException("类别号不存在");}
			
		s.save(m_inf);
		tx.commit();
		s.close();	
		return m_inf;
	}

	@Override //2.修改材料
	//材料名称，所属类别，所属品牌，规格，型号，花色，单价，计价单位
	public m_info change_m(m_info m_info)throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		Transaction tx = s.beginTransaction();
		
		if(m_info.getM_name()==null || "".equals(m_info.getM_name()) || m_info.getM_name().length()>20) 
			{throw new BusinessException("商品名必须是1-20个字母");}
		if(m_info.getBrand().getBrand_id()==0) 
			{throw new BusinessException("品牌号错误");}
		if(m_info.getCategory().getCategory_id()==0)
			{throw new BusinessException("类别号错误");}
		if(m_info.getUnit_price()<0)
			{throw new BusinessException("单价不能小于0");}	
		
		String hql = "from m_budget where m_id = "+m_info.getM_id();
		Query query = s.createQuery(hql);
		if(query.list().size()!=0)
			{throw new BusinessException("此商品在用户预算内，不能修改.");}
		
		s.update(m_info);
		tx.commit();
		s.close();
		return m_info;
	}

	@Override //3.删除材料，当有预算时，不能删除
	public void del_m(m_info m_info) throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		Transaction tx = s.beginTransaction();
		
		String hql = "from m_budget where m_id = "+m_info.getM_id();
		Query query = s.createQuery(hql);
		if(query.list().size()!=0)
			{throw new BusinessException("此商品在用户预算内，不能删除.");}
		
		hql = "delete from m_info where m_id = "+m_info.getM_id();
		s.createQuery(hql).executeUpdate();
		s.close();
	}

	@Override //4.提取材料
	public List<m_info> load_m_info(String m_name, int brand_id, int category_id) throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		
		List<m_info> list_m = new ArrayList<m_info>();
		String hql = null;
		if("".equals(m_name) || m_name==null)
		{
			if(brand_id==0)
			{
				if(category_id==0)	{hql = "from m_info ";}
				else	{hql = "from m_info where category_id = "+category_id;}
			}
			else
			{
				if(category_id==0)	{hql = "from m_info where brand_id = "+brand_id;}
				else	{hql = "from m_info where brand_id = "+brand_id+" and category_id = "+category_id;}
			}
		}
		else
		{
			if(brand_id==0)
			{
				if(category_id==0)	{hql = "from m_info where m_name like '%"+m_name+"%'";}
				else	{hql = "from m_info where m_name = '"+m_name+"' and category_id = "+category_id;}
			}
			else
			{
				if(category_id==0)	{hql = "from m_info where m_name = '"+m_name+"' and brand_id = "+brand_id;}
				else	{hql = "from m_info where m_name = '"+m_name+"' and brand_id = "+brand_id+" and category_id = "+category_id;}
			}
		}
		
		Query query = s.createQuery(hql);
		if(query.list().size()!=0)
		{
			list_m = query.list();
		}
		s.close();
		return list_m;
	}

}
