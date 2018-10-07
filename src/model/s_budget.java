package model;

public class s_budget {
	public static final String[] tableTitles={"序号","服务内容","数量","单价","服务时间","用户名"};
	/**
	 * 请自行根据javabean的设计修改本函数代码，col表示界面表格中的列序号，0开始
	 */
	
	
	public String getCell(int col){
		if(col==0) return String.valueOf(s_budget_id);
		else if(col==1) return s.getService_content();
		else if(col==2) return String.valueOf(quantity);
		else if(col==3) return String.valueOf(s.getUnit_price());
		else if(col==4) return String.valueOf(s.getService_time());
		else if(col==5) return user.getUser_name();
		else return "";
	}
	
	private int s_budget_id;
    private int quantity;
    private int required_time;
    private String remark;
    
    private s_info s;
    private user user;
    
	public s_budget() {
		super();
	}
	public s_budget(s_info s, int quantity, int required_time, String remark, user user) {
		super();
		//this.s_budget_id = s_budget_id;
		this.s = s;
		this.quantity = quantity;
		this.required_time = required_time;
		this.remark = remark;
		this.user = user;
	}
	
	
	public s_info getS() {
		return s;
	}
	public void setS(s_info s) {
		this.s = s;
	}
	public user getUser() {
		return user;
	}
	public void setUser(user user) {
		this.user = user;
	}
	public int getS_budget_id() {
		return s_budget_id;
	}
	public void setS_budget_id(int s_budget_id) {
		this.s_budget_id = s_budget_id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getRequired_time() {
		return required_time;
	}
	public void setRequired_time(int required_time) {
		this.required_time = required_time;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
    
}
