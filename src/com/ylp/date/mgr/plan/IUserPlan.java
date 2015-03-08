package com.ylp.date.mgr.plan;

import java.util.Date;

import com.ylp.date.mgr.IBaseObj;

public interface IUserPlan extends IBaseObj {
	String TYPE_NEWYEAR="newyearplan";
	/**
	 * 获取计划类型,如新年计划 元旦计划等
	 * 
	 * @return
	 */
	String getType();

	/**
	 * 计划内容，可以委托给getCaption
	 * 
	 * @return
	 */
	String getContent();

	/**
	 * 计划发布者
	 * 
	 * @return
	 */
	String getUserId();

	/**
	 * 计划发布时间
	 * 
	 * @return
	 */
	Date getDate();

	/**
	 * 计划是否有限
	 * 
	 * @return
	 */
	boolean isValid();
}
