package com.ylp.date.mgr.tag.impl;

import com.ylp.date.mgr.BaseObjMgr;
import com.ylp.date.mgr.IBaseObj;
import com.ylp.date.mgr.tag.ITagSug;
import com.ylp.date.mgr.tag.ITagSugMgr;

public class UserTagSugMgr extends BaseObjMgr implements ITagSugMgr {

	@Override
	public ITagSug getObj(String id) {
		// TODO Auto-generated method stub
		return (ITagSug) super.getObj(id);
	}

	@Override
	protected Class getBean() {
		// TODO Auto-generated method stub
		return UserTagSug.class;
	}

}
