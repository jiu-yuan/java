package function;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import itf.brand_itf;
import model.brand;
import util.BaseException;
import util.BusinessException;
import util.HBUtil;

public class brand_manager implements brand_itf{

	@Override//1.添加品牌
	public brand add_brand(brand brd) throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		Transaction tx = s.beginTransaction();
		
		if(brd.getBrand_name()==null || "".equals(brd.getBrand_name()) || brd.getBrand_name().length()>20) 
			{throw new BusinessException("品牌名必须是1-20个字母");}
				
		String hql = "from brand where brand_name = '"+brd.getBrand_name()+"'";
		Query query = s.createQuery(hql);
		if(query.list().size()==0) //品牌名不存在
			{s.save(brd);tx.commit();System.out.println("输出在这12");}
		else
			{throw new BusinessException("品牌名已存在1");}
		
		s.close();
		return brd;
	}

	@Override//2.删除品牌，有材料存在时，不能删除
	public void del_brand(brand brand) throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		Transaction tx = s.beginTransaction();
		
		String hql = "from brand where brand_name = '"+brand.getBrand_name()+"'";
		Query query = s.createQuery(hql);
		if(query.list().size()==0) //品牌名不存在
			{throw new BusinessException("此品牌不存在");}
		else
		{
			String hql2 = "from m_info where brand_id = "+brand.getBrand_id();
			Query query2 = s.createQuery(hql2);
			if(query2.list().size()!=0)
				{throw new BusinessException("此品牌下存在商品,不能删除");}
		}
		hql = "delete from brand where brand_name = '"+brand.getBrand_name()+"'";
		s.createQuery(hql).executeUpdate();
		tx.commit();
		s.close();
	}

	@Override//3.修改品牌名,不能与已有名重复
	public brand change_brand(brand brd) throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		Transaction tx = s.beginTransaction();
		
		if(brd.getBrand_name()==null || "".equals(brd.getBrand_name()) || brd.getBrand_name().length()>20) 
			{throw new BusinessException("品牌名必须是1-20个字母");}

		String hql2 = "from m_info where brand_id = "+brd.getBrand_id();
		Query query2 = s.createQuery(hql2);
		if(query2.list().size()!=0)
			{throw new BusinessException("此品牌下存在商品,不能修改");}
		
		String hql = "from brand where brand_name = '"+brd.getBrand_name()+"'";
		Query query = s.createQuery(hql);
		if(query.list().size()==0) //品牌名不存在
			{s.update(brd);}
		else
			{throw new BusinessException("此品牌名已存在");}
	
		tx.commit();
		s.close();
		return brd;
	}

	@Override//4.提取当前所有品牌
	public List<brand> load_all() throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		
		List<brand> all_brand = new ArrayList<brand>();
		
		String hql = "from brand";
		all_brand = s.createQuery(hql).list();
		
		s.close();
		return all_brand;
	}
	
			//5.提取指定品牌
	public brand load_brand(int brand_id)throws BaseException{
		Session s = HBUtil.getSession();
		brand brand = new brand();
		String hql;
		
		if(brand_id==0)
			{throw new BusinessException("请指定品牌号");}
		else if(brand_id!=0)
		{
			hql = "from brand where brand_id = "+brand_id;
			Query query = s.createQuery(hql);
			if(query.list().size()==0)
				{throw new BusinessException("该品牌不存在");}
			brand = (model.brand) query.list().get(0);
		}
		s.close();
		return brand;
		
	}
	public List<brand> load_brand_byname(String name)throws BaseException{
		Session s = HBUtil.getSession();
		List<brand> all_brand = new ArrayList<brand>();
		String hql;
		
		if("".equals(name))
		{
			hql = "from brand";
		}
		else
		{
			hql = "from brand where brand_name like '%"+name+"%'";
		}
		all_brand = s.createQuery(hql).list();
		
		s.close();
		return all_brand;
	}
}
