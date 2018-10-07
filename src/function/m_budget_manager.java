package function;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import itf.m_budget_itf;
import model.m_budget;
import model.m_info;
import model.user;
import util.BaseException;
import util.HBUtil;

public class m_budget_manager implements m_budget_itf{

	public m_budget add_m_budget(m_budget m_bgt) throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		Transaction tx = s.beginTransaction();
		String hql = "from m_info where m_id = "+m_bgt.getM().getM_id();
		Query query = s.createQuery(hql);
		if(query.list().size()==0)
				throw new BaseException("该商品不存在");
		
		s.save(m_bgt);
		tx.commit();
		s.close();
		return m_bgt;
	}

	@Override //2.修改材料预算
	public m_budget change_m_budget(m_budget m_bgt) throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		Transaction tx = s.beginTransaction();
		
		s.update(m_bgt);
		tx.commit();
		s.close();
		return m_bgt;
	}

	@Override //3.删除材料预算
	public void del_m_budget(m_budget m_bgt) throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		Transaction tx = s.beginTransaction();
		
		s.delete(m_bgt);
		tx.commit();
		s.close();
	}

	@Override 	//4.提取当前用户所有材料预算，每次提取，对单价进行重新核对;
				//若为管理员，则提取所有记录
	public List<m_budget> load_m_budget(user usr) throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		List<m_budget> m_budget_list = new ArrayList<m_budget>();
		String hql;
		
		hql = "from m_budget where user_id = "+usr.getUser_id();
		
		Query query = s.createQuery(hql);
		m_budget_list = query.list();
		s.close();
		return m_budget_list;
	}

}
