package function;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import itf.user_itf;
import model.user;
import util.BaseException;
import util.BusinessException;
import util.HBUtil;

public class user_manager implements user_itf {

	@Override//1.注册
	public user reg(String user_name, String psd,String psd2) throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		Transaction tx = s.beginTransaction();
		
		if(user_name==null || "".equals(user_name) || user_name.length()>20) 
			{throw new BusinessException("账号ID必须是1-20个字母");}
		if(psd==null || "".equals(psd)||psd.length()<1||psd.length()>11) 
			{throw new BusinessException("密码长度必须为1~11位");}
		if(!psd.equals(psd2)) 
			{throw new BusinessException("输入密码不一致");}
		
		user u = new user(user_name, 1, psd);
		
		String hql = "from user where user_name = '"+user_name+"'";
		Query query = s.createQuery(hql);
		//System.out.println("size = "+query.list().size());
		if(query.list().size()==0) //user_name不存在
			{s.save(u);}
		else
			{throw new BusinessException("账号ID已存在");}
		
		tx.commit();
		s.close();
		return u;
		
	}

	@Override//2.登陆
	public user login(String user_name, String psd) throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		user b = new user();
		if(user_name==null||"".equals(user_name)) 
			{throw new BusinessException("账号不能为空");}
		
		String hql = "from user where user_name = '"+user_name+"'";
		Query query = s.createQuery(hql);
		if(query.list().size()==0) 
			{throw new BaseException("帐号不存在");}
		else
		{
			user u = (user) query.list().get(0);
			if(!u.getPsd().equals(psd))
				{throw new BaseException("密码错误");}
			
			b=u;
		}
		
		s.close();
		return b;
	}

	@Override//3.修改密码
	public void change_psd(user user, String old_psd, String new_psd, String new_psd2) throws BaseException {
		// TODO Auto-generated method stub
		Session s = HBUtil.getSession();
		Transaction tx = s.beginTransaction();
		
		user b = s.get(user.class, user.getUser_id());
		if(!old_psd.equals(b.getPsd())) 
			{throw new BusinessException("密码错误");}
		if(new_psd.length()<1||new_psd.length()>11)
		{throw new BusinessException("新密码长度必须为1~11位");}
		if(!new_psd.equals(new_psd2)) 
			{throw new BusinessException("新密码输入不一致");}
		
		b.setPsd(new_psd);
		s.update(b);
		tx.commit();
		s.close();
	}
			//4.提取所有用户
	public List<user> load_all()throws BaseException{
		Session s = HBUtil.getSession();
		
		List<user> user_list = new ArrayList<user>();
		
		String hql = "from user";
		Query query = s.createQuery(hql);
		
		if(query.list().size()!=0)
		{
			user_list = query.list();
		}
		
		s.close();
		return user_list;
	}
	public List<user> load_user_id(int user_id)throws BaseException{
		Session s = HBUtil.getSession();
		List<user> user_list = new ArrayList<user>();
		
		String hql = "from user where user_id like '%"+user_id+"%'";
		Query query = s.createQuery(hql);
		
		if(query.list().size()!=0)
		{
			user_list = query.list();
		}
		
		s.close();
		return user_list;
	}
	public List<user> load_user_name(String user_name)throws BaseException{
		Session s = HBUtil.getSession();
		
		List<user> user_list = new ArrayList<user>();
		
		String hql = "from user where user_name like '%"+user_name+"%'";
		Query query = s.createQuery(hql);
		
		if(query.list().size()!=0)
		{
			user_list = query.list();
		}
		
		s.close();
		return user_list;
	}
	public List<user> load_user_level(int user_level)throws BaseException{
		Session s = HBUtil.getSession();
		
		List<user> user_list = new ArrayList<user>();
		
		String hql = "from user where level like '%"+user_level+"%'";
		Query query = s.createQuery(hql);
		if(query.list().size()!=0)
		{
			user_list = query.list();
		}
		
		s.close();
		return user_list;
	}
	public void change_user(user usr, int flg)throws BaseException{
		Session s = HBUtil.getSession();
		Transaction tx = s.beginTransaction();
		System.out.println("flg2 = "+flg);
		if(flg==1)
		{
			s.update(usr);
			tx.commit();
		}
		else
		{
			String hql = "from user where user_name = '"+usr.getUser_name()+"'";
			Query query = s.createQuery(hql);
			if(query.list().size()!=0) 
				{throw new BaseException("用户名已存在");}
			else {
				s.update(usr);
				tx.commit();
			}
		}
		s.close();
	}
	public void del_user(user usr)throws BaseException{
		Session s = HBUtil.getSession();
		Transaction tx = s.beginTransaction();
		
		String hql = "from m_budget where user_id = "+usr.getUser_id();
		Query query = s.createQuery(hql);
		if(query.list().size()!=0)
			{throw new BaseException("此用户存在材料预算，不能删除");}
		else
		{
			hql = "from s_budget where user_id = "+usr.getUser_id();
			query = s.createQuery(hql);
			if(query.list().size()!=0)
				{throw new BaseException("此用户存在服务预算，不能删除");}
			else
			{
				s.delete(usr);
				tx.commit();
			}
		}
		
		s.close();
	}
}
