package com.ylp.date.mgr.relation;

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
	int RECOG_LINE = 5;
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
}
