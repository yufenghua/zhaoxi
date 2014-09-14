package com.ylp.date.mgr.msg;

import java.util.Date;

import com.ylp.date.mgr.IBaseObj;

public interface IMessage extends IBaseObj {
	/**
	 * 
	 * @return
	 */
	String getSender();
	/**
	 * 
	 * @return
	 */
	String getReceiver();
	/**
	 * 
	 * @return
	 */
	String getContent();
	/**
	 * 
	 * @return
	 */
	Date getDate();

}
