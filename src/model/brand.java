package model;

public class brand {
	public static final String[] tableTitles={"序号","品牌名","描述"};
	/**
	 * 请自行根据javabean的设计修改本函数代码，col表示界面表格中的列序号，0开始
	 */
	
	
	public String getCell(int col){
		if(col==0) return String.valueOf(brand_id);
		else if(col==1) return brand_name;
		else if(col==2) return brand_describe;
		else return "";
	}
	
	private int brand_id;
	private String brand_name;
	private String brand_describe;
	
	public brand() {
		super();
	}
	public brand(String brand_name, String brand_describe) {
		super();
		//this.brand_id = brand_id;
		this.brand_name = brand_name;
		this.brand_describe = brand_describe;
	}
	
	public int getBrand_id() {
		return brand_id;
	}
	public void setBrand_id(int brand_id) {
		this.brand_id = brand_id;
	}
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	public String getBrand_describe() {
		return brand_describe;
	}
	public void setBrand_describe(String brand_describe) {
		this.brand_describe = brand_describe;
	}
	
}
