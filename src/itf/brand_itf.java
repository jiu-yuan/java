package itf;

import java.util.List;

import model.brand;
import util.BaseException;

public interface brand_itf {
	//添加品牌
	public brand add_brand(brand brd)throws BaseException;
	
	//删除品牌，有材料存在时，不能删除
	public void del_brand(brand brand)throws BaseException;
	
	//修改品牌名、描述
	public brand change_brand(brand brand)throws BaseException;
	
	//提取当前所有品牌
	public List<brand> load_all()throws BaseException;
	//提取指定品牌
	public brand load_brand(int brand_id)throws BaseException;
	public List<brand> load_brand_byname(String name)throws BaseException;

}
