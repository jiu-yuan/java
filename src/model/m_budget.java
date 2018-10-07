package model;

public class m_budget {
	public static final String[] tableTitles={"序号","商品名","数量","单价","用户名"};
	/**
	 * 请自行根据javabean的设计修改本函数代码，col表示界面表格中的列序号，0开始
	 */
	
	
	public String getCell(int col){
		if(col==0) return String.valueOf(m_budget_id);
		else if(col==1) return m.getM_name();
		else if(col==2) return String.valueOf(quantity);
		else if(col==3) return String.valueOf(unit_price);
		else if(col==4) return user.getUser_name();
		else return "";
	}
	
	private int m_budget_id;
	private int quantity;
	private double unit_price;
	
	private m_info m;
	private user user;
	
	public m_budget() {
		super();
	}
	public m_budget(m_info m, int quantity, double unit_price, user user) {
		super();
		this.m = m;
		this.quantity = quantity;
		this.unit_price = unit_price;
		this.user = user;
	}
	
	
	
	public m_info getM() {
		return m;
	}
	public void setM(m_info m) {
		this.m = m;
	}
	public user getUser() {
		return user;
	}
	public void setUser(user user) {
		this.user = user;
	}
	public int getM_budget_id() {
		return m_budget_id;
	}
	public void setM_budget_id(int m_budget_id) {
		this.m_budget_id = m_budget_id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getUnit_price() {
		return unit_price;
	}
	public void setUnit_price(double unit_price) {
		this.unit_price = unit_price;
	}
    
}
