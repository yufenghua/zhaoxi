package com.ylp.date.mgr;

public interface ObjListener {
	void fileAdd(IBaseObj obj);
	void fireUpdate(String id,IBaseObj old,IBaseObj newObj);
	void fireRemove(String id);
}
