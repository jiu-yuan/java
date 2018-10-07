package function;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import itf.m_to_s_itf;
import model.m_to_s;
import util.BaseException;
import util.BusinessException;
import util.HBUtil;

public class m_to_s_manager implements m_to_s_itf{


	@Override //1.修改，若预算存在，则不能修改
	public m_to_s change_m_to_s(m_to_s mts) throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		Transaction tx = s.beginTransaction();
		String hql;
		Query query;
		if(mts.getS_id()==0)
		{
			hql = "delete from m_to_s where m_id = "+mts.getM_id();
			s.createQuery(hql).executeUpdate();
			s.close();
			return null;
		}
		hql = "from s_info where s_id = "+mts.getS_id();
		query = s.createQuery(hql);
		if(query.list().size()==0)
			{throw new BusinessException("服务不存在");}
		else
		{
			hql = "from m_to_s where m_id=  "+mts.getM_id();
			query = s.createQuery(hql);
			if(query.list().size()!=0)
			{
				hql = "update m_to_s set s_id = "+mts.getS_id()+" where m_id = "+mts.getM_id();
				s.createQuery(hql).executeUpdate();
				s.close();
			}
			else
			{
				s.save(mts);
				tx.commit();
				s.close();
			}

		}
		return mts;
	}

	@Override //2.删除，若预算存在，则不能删除
	public void del_m_to_s(m_to_s mts) throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		Transaction tx = s.beginTransaction();
		String hql = "from m_to_s where m_id=  "+mts.getM_id();
		Query query = s.createQuery(hql);
		if(query.list().size()!=0) 
		{
			hql = "delete from m_to_s where m_id = "+mts.getM_id();
			s.createQuery(hql).executeUpdate();
		}
		s.close();
	}

	@Override //3.提取对应服务
	public int m_get_s(int m_id) throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		String hql = "from m_to_s where m_id=  "+m_id;
		Query query = s.createQuery(hql);
		if(query.list().size()!=0) 
		{
			m_to_s mts = (m_to_s) query.list().get(0);
			return mts.getS_id();
		}
		s.close();
		return 0;
	}

	
	@Override //4.提取对应
		public m_to_s load_m_to_s(int mts_id)throws BaseException{
		Session s = HBUtil.getSession();
		String hql = "from m_to_s where id=  "+mts_id;
		Query query = s.createQuery(hql);
		if(query.list().size()!=0) 
		{
			m_to_s mts = (m_to_s) query.list().get(0);
			return mts;
		}
		s.close();
		return null;
	}

	

}
