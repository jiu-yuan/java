package itf;

import java.util.List;

import model.brand;
import model.category;
import util.BaseException;

public interface category_itf {
	//添加类别
	public category add_category(category cate)throws BaseException;
	
	//删除类别，有材料存在时，不能删除
	public void del_category(category cate)throws BaseException;
	
	//修改类别名,描述
	public category change_category(category cate)throws BaseException;
	
	//提取当前所有类别
	public List<category> load_all()throws BaseException;
	//提取指定品牌
	public category load_category(int category_id)throws BaseException;
	public List<category> load_category_byname(String name) throws BaseException;
		
}
