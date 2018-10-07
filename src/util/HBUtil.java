package util;

import org.hibernate.*;
import org.hibernate.cfg.*;

public class HBUtil {
	private static SessionFactory sf = new Configuration().configure().buildSessionFactory();
	public static Session getSession() {
		return sf.openSession();
	}
	
	public static void main(String[] args) {
		Session ss = HBUtil.getSession();
		if(ss.isConnected()) {System.out.println("连接成功");	}
		else {System.out.println("连接失败");}
		ss.close();
	}
	
}
