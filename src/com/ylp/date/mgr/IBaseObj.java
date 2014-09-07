package com.ylp.date.mgr;

/**
 * 所有用于管理对象的跟对象，此对象提供两个方法getId和getCaption
 * 
 * @author Qiaolin Pan
 * 
 */
public interface IBaseObj {
	/**
	 * 获取对象的id <br/>
	 * 该id应该具有"唯一性" 即 在该对象的域中，id应该是可以作为key
	 * @return 对象的唯一ID
	 */
	String getId();

	/**
	 * 获取对象的标题,"标题"一般用作界面显示<br/>
	 * 如果 该对象没有"标题"，那么推荐返回该对象的id
	 * @see IBaseObj#getId()
	 * @return 对象的标题
	 */
	String getCaption();
}
