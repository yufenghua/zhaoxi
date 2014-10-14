package com.ylp.date.mgr.tag.impl;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.ylp.date.mgr.BaseObjMgr;
import com.ylp.date.mgr.IBaseObj;
import com.ylp.date.mgr.tag.ITagSug;
import com.ylp.date.mgr.tag.ITagSugMgr;
import com.ylp.date.server.SpringNames;

@Component(SpringNames.TagSugMgr)
public class UserTagSugMgr extends BaseObjMgr implements ITagSugMgr {
	public void init() {
		if (count(null) == 0) {
			UserTagSug liketodo = new UserTagSug();
			liketodo.setCaption("喜欢做的事");
			liketodo.setCreateDate(new Date());
			add(liketodo);
			UserTagSug likeWho = new UserTagSug();
			likeWho.setCaption("喜欢的人");
			likeWho.setCreateDate(new Date());
			add(likeWho);
			UserTagSug other = new UserTagSug();
			other.setCaption("还有呢");
			other.setCreateDate(new Date());
			add(other);
		}
	}

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
