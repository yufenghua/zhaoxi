package com.ylp.date.mgr.msg;

import java.util.List;

import com.ylp.date.mgr.IMgrBase;

public interface IMsgMgr extends IMgrBase {
	List<IMessage> listUnRead(String sender, String receiver);
}
