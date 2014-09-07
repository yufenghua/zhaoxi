package com.ylp.date.mgr;

import java.util.List;


/**
 * 管理类根接口
 * 
 * @author Qiaolin Pan
 * 
 */
public interface IMgrBase {
	IBaseObj getObj(String id);
	List<IBaseObj> list();
	List<IBaseObj> list(PageCondition page);
	List<IBaseObj> list(PageCondition  page,Condition cond);
}
