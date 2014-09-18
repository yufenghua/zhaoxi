package com.ylp.date.mgr;

import java.util.List;

import com.ylp.date.mgr.condtion.ConditionPair;

/**
 * The root interface for manager objects
 * 
 * @author Qiaolin Pan
 * 
 */
public interface IMgrBase {
	/**
	 * receive an IBaseObj instance by id
	 * 
	 * @param id
	 * @return if have return this instance else return null
	 */
	IBaseObj getObj(String id);

	/**
	 * list all IBaseObj instance which managed by this
	 * 
	 * @return
	 */
	List<IBaseObj> list();

	/**
	 * list IBaseObj instance with the giving range
	 * 
	 * @param page
	 *            page range object if null ,will return all ins
	 * @return
	 */
	List<IBaseObj> list(PageCondition page);

	/**
	 * list IBaseObj instance with the giving range and condition
	 * 
	 * @param page
	 * @param cond Condition object
	 * @return
	 */
	List<IBaseObj> list(PageCondition page, ConditionPair cond);

	/**
	 * add an ins
	 * 
	 * @param obj
	 *            ins ,can not be null
	 * @return true if success or false
	 */
	IBaseObj add(IBaseObj obj);

	/**
	 * remove an ins.
	 * if this ins does not exist,will throw an exception
	 * 
	 * @param id the id for the instance want to be removed
	 * @return true if success or false
	 */
	boolean remove(String id);

	/**
	 * update an ins
	 *  if this ins does not exist,will throw an exception
	 * @param id the id for the instance want to be updated
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	boolean update(String id, IBaseObj obj) throws Exception;
	/**
	 * reg a listener which will do something when an object to be added ,updated,removed
	 * @param lis
	 */
	void regListener(ObjListener lis);

}
