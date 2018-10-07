package model;

public class m_to_s {
	private int id;
	private int quantity;
	
	private int m_id;
	private int s_id;
    
	public m_to_s() {
		super();
	}
	public m_to_s(int m_id, int s_id, int quantity) {
		super();
		//this.id = id;
		this.m_id = m_id;
		this.s_id = s_id;
		this.quantity = quantity;
	}
	
//	public m_info getM() {
//		return m;
//	}
//	public void setM(m_info m) {
//		this.m = m;
//	}
//	public s_info getS() {
//		return s;
//	}
//	public void setS(s_info s) {
//		this.s = s;
//	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getM_id() {
		return m_id;
	}
	public void setM_id(int m_id) {
		this.m_id = m_id;
	}
	public int getS_id() {
		return s_id;
	}
	public void setS_id(int s_id) {
		this.s_id = s_id;
	}
    
}
