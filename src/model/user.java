package model;

public class user {
	public static user currentLoginUser=null;
	
	public static final String[] tableTitles={"序号","用户名","用户密码","等级"};
	/**
	 * 请自行根据javabean的设计修改本函数代码，col表示界面表格中的列序号，0开始
	 */
	
	
	public String getCell(int col){
		if(col==0) return String.valueOf(user_id);
		else if(col==1) return user_name;
		else if(col==2) return psd;
		else if(col==3) return String.valueOf(level);
		else return "";
	}
	
	private int user_id;
	private String user_name;
	private int level;
	private String psd;
	
	public user() {
		super();
	}

	public user(String user_name, int level, String psd) {
		super();
		this.user_name = user_name;
		this.level = level;
		this.psd = psd;
	}
	
	public static user getCurrentLoginUser() {
		return currentLoginUser;
	}

	public static void setCurrentLoginUser(user currentLoginUser) {
		user.currentLoginUser = currentLoginUser;
	}
	
	public int getUser_id() {
		return user_id;
	}
	
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getPsd() {
		return psd;
	}
	public void setPsd(String psd) {
		this.psd = psd;
	}
	
}
