package com.ylp.date.mgr.tag;

import java.util.Date;

import com.ylp.date.mgr.IBaseObj;
import com.ylp.date.mgr.user.IUser;

public interface ITagSug extends IBaseObj{
	IUser getCreator();
	Date getCreateDate();
	/**
	 * 有效范围 userids
	 * @return
	 */
	String getRange();

}
