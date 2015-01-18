package com.ylp.date.mgr.helpinfo;

import com.ylp.date.mgr.IMgrBase;

public interface IHelpMgr extends IMgrBase {
	/**
	 * 验证是否查看
	 * 
	 * @param user
	 * @param version
	 * @return
	 */
	boolean checkRead(String user, String version);

	/**
	 * 查看完毕之后确认
	 * 
	 * @param user
	 * @param version
	 * @return
	 */
	boolean read(String user, String version);

	IHelpInfo getHelpInfo(String user, String version);
}
