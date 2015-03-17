package com.ylp.date.mgr.relation;

import java.util.Date;

import com.ylp.date.mgr.IBaseObj;

/**
 * relation interface
 * 
 * @author Qiaolin Pan
 * 
 */
public interface IRelation extends IBaseObj {
	int TYPE_LINE = 0;
	int TYPE_FLOWER = 1;
	int TYPE_PLAN = 2;
	int RECOG_PLAN = 1;
	int RECOG_LINE = 3;
	int RECOG_FLOWER = 2;

	/**
	 * one side of the relation
	 * 
	 * @return
	 */
	String getOne();

	/**
	 * the oherside of the relation
	 * 
	 * @return
	 */
	String getOtherOne();

	String getOther(String userId);

	/**
	 * relation type 。
	 * 
	 * @return
	 */
	int getType();

	/***
	 * current recognition
	 * 
	 * @return
	 */
	int getRecognition();

	Date getOkTime();

	/**
	 * 关系发生的上下文类型,如新年计划 赞 连线等
	 * 
	 * @return
	 */
	int getContextType();

	/**
	 * 关系发生的上下文对象id 有可能为空，如 新年计划的id
	 * 
	 * @return
	 */
	String getContextObjId();
}
