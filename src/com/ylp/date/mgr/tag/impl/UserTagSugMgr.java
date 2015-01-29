package com.ylp.date.mgr.tag.impl;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.ylp.date.mgr.BaseObjMgr;
import com.ylp.date.mgr.tag.ITagSug;
import com.ylp.date.mgr.tag.ITagSugMgr;
import com.ylp.date.server.SpringNames;

@Component(SpringNames.TagSugMgr)
public class UserTagSugMgr extends BaseObjMgr implements ITagSugMgr {
	public void init() {
		if (calcCount(null) == 0) {
			UserTagSug likeWho = new UserTagSug();
			likeWho.setCaption("偶像");
			likeWho.setCreateDate(new Date());
			add(likeWho);
			UserTagSug liketodo = new UserTagSug();
			liketodo.setCaption("爱好");
			liketodo.setCreateDate(new Date());
			add(liketodo);
//去除还有呢 这个选项
//			UserTagSug other = new UserTagSug();
//			other.setCaption("还有呢");
//			other.setCreateDate(new Date());
//			add(other);
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
