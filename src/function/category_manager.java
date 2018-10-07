package function;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import itf.category_itf;
import model.brand;
import model.category;
import util.BaseException;
import util.BusinessException;
import util.HBUtil;

public class category_manager implements category_itf{

	@Override//1.添加类别
	public category add_category(category cate) throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		Transaction tx = s.beginTransaction();
		
		if(cate.getCategory_name()==null || "".equals(cate.getCategory_name()) || cate.getCategory_name().length()>20) 
		{throw new BusinessException("类别名必须是1-20个字母");}
		
		String hql = "from category where category_name = '"+cate.getCategory_name()+"'";
		Query query = s.createQuery(hql);
		if(query.list().size()==0) //类别名不存在
			{s.save(cate);}
		else
			{throw new BusinessException("类别名已存在");}
		
		tx.commit();
		s.close();
		return cate;
	}

	@Override//2.删除类别，有材料存在时，不能删除
	public void del_category(category category) throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		Transaction tx = s.beginTransaction();
		
		String hql = "from category where category_name = '"+category.getCategory_name()+"'";
		Query query = s.createQuery(hql);
		if(query.list().size()==0) //类别名不存在
			{throw new BusinessException("此类别不存在");}
		else
		{
			String hql2 = "from m_info where category_id = "+category.getCategory_id();
			Query query2 = s.createQuery(hql2);
			if(query2.list().size()!=0)
				{throw new BusinessException("此类别下存在商品,不能删除");}
		}
		
		hql = "delete from category where category_name = '"+category.getCategory_name()+"'";
		s.createQuery(hql).executeUpdate();
		tx.commit();
		s.close();
		
	}

	@Override//3.修改类别名,不能与已有名重复
	public category change_category(category cate) throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		Transaction tx = s.beginTransaction();
		
		if(cate.getCategory_name()==null || "".equals(cate.getCategory_name()) || cate.getCategory_name().length()>20) 
		{throw new BusinessException("类别名必须是1-20个字母");}
		else 
		{
			String hql2 = "from m_info where category_id = "+cate.getCategory_id();
			Query query2 = s.createQuery(hql2);
			if(query2.list().size()!=0)
				{throw new BusinessException("此类别下存在商品,不能修改");}
			
			String hql = "from category where category_name = '"+cate.getCategory_name()+"'";
			Query query = s.createQuery(hql);
			if(query.list().size()==0) //品牌名不存在
				{s.update(cate);}
			else
				{throw new BusinessException("此类别名已存在");}
		}
		tx.commit();
		s.close();
		return cate;
	}

	@Override//4.提取当前所有类别
	public List<category> load_all() throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		
		List<category> all_category = new ArrayList<category>();
		
		String hql = "from category";
		all_category = s.createQuery(hql).list();
		s.close();
		return all_category;
	}
			//5.提取指定品牌
	public category load_category(int category_id)throws BaseException{
		Session s = HBUtil.getSession();
		category category = new category();
		String hql;
		
		if(category_id==0)
			{throw new BusinessException("请指定类别号");}
		else if(category_id!=0)
		{
			hql = "from category where category_id = "+category_id;
			Query query = s.createQuery(hql);
			if(query.list().size()==0)
				{throw new BusinessException("该类别不存在");}
			category = (model.category) query.list().get(0);
		}
		s.close();
		return category;
	}
	public List<category> load_category_byname(String name) throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		String hql;
		List<category> all_category = new ArrayList<category>();
		if("".equals(name))
			hql = "from category";
		else
			hql = "from category where category_name like '%"+name+"%'";
		all_category = s.createQuery(hql).list();
		
		s.close();
		return all_category;
	}
}
