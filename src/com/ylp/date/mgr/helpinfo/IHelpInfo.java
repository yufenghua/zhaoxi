package com.ylp.date.mgr.helpinfo;

import java.util.Date;

import com.ylp.date.mgr.IBaseObj;

public interface IHelpInfo extends IBaseObj {
	/**
	 * 版本号 独立于服务器版本号
	 * 
	 * @return
	 */
	String getVersion();

	/**
	 * 用户名
	 * 
	 * @return
	 */
	String getUser();

	/**
	 * 是否已阅
	 * 
	 * @return
	 */
	boolean isReaded();

	/**
	 * 阅读时间
	 * 
	 * @return
	 */
	Date getReadTime();

}
