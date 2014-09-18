package com.ylp.date.mgr;

public interface ObjListener {
	void fileAdd(IBaseObj obj);
	void fireUpdate(String id,IBaseObj obj);
	void fireRemove(String id);
}
