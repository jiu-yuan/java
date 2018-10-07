package function;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import itf.s_budget_itf;
import model.s_budget;
import model.s_budget;
import model.user;
import util.BaseException;
import util.HBUtil;

public class s_budget_manager implements s_budget_itf{

	@Override //1.添加服务预算
	public s_budget add_s_budget(s_budget s_bgt) throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		Transaction tx = s.beginTransaction();
		String hql = "from s_info where s_id = "+s_bgt.getS().getS_id();
		Query query = s.createQuery(hql);
		if(query.list().size()==0)
				throw new BaseException("该商品不存在");
		
		s.save(s_bgt);
		tx.commit();
		s.close();
		return s_bgt;
	}

	@Override //2.修改服务预算
	public s_budget change_s_budget(s_budget s_bgt)throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		Transaction tx = s.beginTransaction();
		
		s.update(s_bgt);
		tx.commit();
		s.close();
		return s_bgt;
	}

	@Override //3.删除服务预算
	public void del_s_budget(s_budget s_bgt) throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		Transaction tx = s.beginTransaction();
		
		s.delete(s_bgt);
		tx.commit();
		s.close();
	}

	@Override //4.提取当前用户所有服务预算，每次提取，对单价进行重新核对;
	//若为管理员，则提取所有记录
	public List<s_budget> load_s_budget(user usr) throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		List<s_budget> s_budget_list = new ArrayList<s_budget>();
		String hql;
		
		hql = "from s_budget where user_id = "+usr.getUser_id();
		
		Query query = s.createQuery(hql);
		s_budget_list = query.list();
		s.close();
		return s_budget_list;
	}
	
}
