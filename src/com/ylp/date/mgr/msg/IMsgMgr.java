package com.ylp.date.mgr.msg;

import java.util.List;

import com.ylp.date.mgr.IMgrBase;

public interface IMsgMgr extends IMgrBase {
	/**
	 * sender 和 receiver表示不设限制
	 * @param sender
	 * @param receiver
	 * @return
	 */
	List<IMessage> listUnRead(String sender, String receiver);

	/**
	 * sender 不能为空
	 * @param sender
	 * @param receiver
	 */
	void read(String sender, String receiver);
}
