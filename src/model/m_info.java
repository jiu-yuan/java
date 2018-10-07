package model;

public class m_info {
	public static final String[] tableTitles={"序号","商品名","品牌名","类别名","规格","型号","花色","单价"};
	/**
	 * 请自行根据javabean的设计修改本函数代码，col表示界面表格中的列序号，0开始
	 */
	public static final String[] tableTitles2={"序号","商品名","品牌名","类别名","规格","型号","花色","单价","数量"};
	
	public String getCell(int col){
		if(col==0) return String.valueOf(m_id);
		else if(col==1) return m_name;
		else if(col==2) return brand.getBrand_name();
		else if(col==3) return category.getCategory_name();
		else if(col==4) return standard;
		else if(col==5) return model_number;
		else if(col==6) return flower_color;
		else if(col==7) return String.valueOf(unit_price);
		else return "";
	}
	
	private int m_id;
	private String m_name;
	private String standard;	//规格
	private String model_number;//型号
	private String flower_color;//花色
	private double unit_price;	//单价
	private String unit_of_valuation;//计价单位
	private int mts_id;
	private int s_id;
	
	private brand brand;
	private category category;
	
	public m_info() {
		super();
	}
	public m_info(String m_name, category category, brand brand, String standard, String model_number,
			String flower_color, double unit_price, String unit_of_valuation) {
		super();
		//this.m_id = m_id;
		this.m_name = m_name;
		this.category = category;
		this.brand = brand;
		this.standard = standard;
		this.model_number = model_number;
		this.flower_color = flower_color;
		this.unit_price = unit_price;
		this.unit_of_valuation = unit_of_valuation;
	}
	
	public brand getBrand() {
		return brand;
	}
	public void setBrand(brand brand) {
		this.brand = brand;
	}
	public category getCategory() {
		return category;
	}
	public void setCategory(category category) {
		this.category = category;
	}
	public int getM_id() {
		return m_id;
	}
	public void setM_id(int m_id) {
		this.m_id = m_id;
	}
	public String getM_name() {
		return m_name;
	}
	public void setM_name(String m_name) {
		this.m_name = m_name;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getModel_number() {
		return model_number;
	}
	public void setModel_number(String model_number) {
		this.model_number = model_number;
	}
	public String getFlower_color() {
		return flower_color;
	}
	public void setFlower_color(String flower_color) {
		this.flower_color = flower_color;
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
	public int getMts_id() {
		return mts_id;
	}
	public void setMts_id(int mts_id) {
		this.mts_id = mts_id;
	}
	public int getS_id() {
		return s_id;
	}
	public void setS_id(int s_id) {
		this.s_id = s_id;
	}
	
}
