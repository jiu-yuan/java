package model;

public class s_info {
	public static final String[] tableTitles={"序号","服务内容","服务等级","单价","计价单位","服务时间"};
	/**
	 * 请自行根据javabean的设计修改本函数代码，col表示界面表格中的列序号，0开始
	 */
	public static final String[] tableTitles2={"序号","服务内容","服务等级","单价","计价单位","服务时间","数量"};
	
	public String getCell(int col){
		if(col==0) return String.valueOf(s_id);
		else if(col==1) return service_content;
		else if(col==2) return String.valueOf(service_level);
		else if(col==3) return String.valueOf(unit_price);
		else if(col==4) return unit_of_valuation;
		else if(col==5) return String.valueOf(service_time);
		else return "";
	}
	
	private int s_id;
	private String service_content;	//服务内容
	private int service_level;		//服务等级
	private double unit_price;		//单价
	private String unit_of_valuation;//计价单位
	private int service_time;		//服务时间
	
	public s_info() {
		super();
	}
	public s_info(String service_content, int service_level, double unit_price, String unit_of_valuation,
			int service_time) {
		super();
		this.service_content = service_content;
		this.service_level = service_level;
		this.unit_price = unit_price;
		this.unit_of_valuation = unit_of_valuation;
		this.service_time = service_time;
	}
	
	public int getS_id() {
		return s_id;
	}
	public void setS_id(int s_id) {
		this.s_id = s_id;
	}
	public String getService_content() {
		return service_content;
	}
	public void setService_content(String service_content) {
		this.service_content = service_content;
	}
	public int getService_level() {
		return service_level;
	}
	public void setService_level(int service_level) {
		this.service_level = service_level;
	}
	public double getUnit_price() {
		return unit_price;
	}
	public void setUnit_price(double unit_price) {
		this.unit_price = unit_price;
	}
	public String getUnit_of_valuation() {
		return unit_of_valuation;
	}
	public void setUnit_of_valuation(String unit_of_valuation) {
		this.unit_of_valuation = unit_of_valuation;
	}
	public int getService_time() {
		return service_time;
	}
	public void setService_time(int service_time) {
		this.service_time = service_time;
	}
	
}
