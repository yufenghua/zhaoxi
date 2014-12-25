package com.ylp.date.mgr.tag.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.ylp.date.mgr.BaseObjMgr;
import com.ylp.date.mgr.IBaseObj;
import com.ylp.date.mgr.PageCondition;
import com.ylp.date.mgr.condtion.ConditionPair;
import com.ylp.date.mgr.condtion.impl.Condition;
import com.ylp.date.mgr.condtion.impl.SimglePair;
import com.ylp.date.mgr.tag.ITag;
import com.ylp.date.mgr.tag.ITagMgr;
import com.ylp.date.server.SpringNames;

@Component(SpringNames.TagMgr)
@DependsOn(SpringNames.Server)
public class UserTagMgr extends BaseObjMgr implements ITagMgr {
	private static final Logger logger = LoggerFactory
			.getLogger(UserTagMgr.class);

	public ITag getObj(String id) {
		return (ITag) super.getObj(id);
	}

	public void init() {
		logger.info("init usertagmgr");
	}

	public void destroy() {
		logger.info("destroy usertagmgr");
	}

	@Override
	protected Class getBean() {
		return UserTag.class;
	}

	public List<ITag> getTagsByUser(String userId) {
		Condition cond = new Condition();
		cond.eq("userId", userId);
		SimglePair pair = new SimglePair();
		pair.setFirst(cond);
		List<IBaseObj> list = list(null, pair);
		if (list.isEmpty()) {
			return Collections.emptyList();
		}
		List<ITag> tags = new ArrayList<ITag>();
		for (IBaseObj iTag : list) {
			tags.add((ITag) iTag);

		}
		return tags;
	}

	public List<ITag> getTagBySugId(PageCondition condition, String sugId) {
		Condition cond = new Condition();
		cond.eq("sugId", sugId);
		SimglePair pair = new SimglePair();
		pair.setFirst(cond);
		List<IBaseObj> list = list(condition, pair);
		if (list.isEmpty()) {
			return Collections.emptyList();
		}
		List<ITag> tags = new ArrayList<ITag>();
		for (IBaseObj iTag : list) {
			tags.add((ITag) iTag);
		}
		return tags;
	}

}
