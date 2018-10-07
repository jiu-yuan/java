package model;

public class category {
	public static final String[] tableTitles={"序号","类别名","描述"};
	/**
	 * 请自行根据javabean的设计修改本函数代码，col表示界面表格中的列序号，0开始
	 */
	
	
	public String getCell(int col){
		if(col==0) return String.valueOf(category_id);
		else if(col==1) return category_name;
		else if(col==2) return category_describe;
		else return "";
	}
	
	private int category_id;
	private String category_name;
	private String category_describe;
	
	public category() {
		super();
	}
	public category(String category_name, String category_describe) {
		super();
		//this.category_id = category_id;
		this.category_name = category_name;
		this.category_describe = category_describe;
	}
	
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getCategory_describe() {
		return category_describe;
	}
	public void setCategory_describe(String category_describe) {
		this.category_describe = category_describe;
	}
	
}
