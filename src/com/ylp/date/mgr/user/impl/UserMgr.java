package com.ylp.date.mgr.user.impl;

import org.hibernate.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.ylp.date.mgr.BaseObjMgr;
import com.ylp.date.mgr.condtion.ConditionPair;
import com.ylp.date.mgr.user.IUserMgr;
import com.ylp.date.server.SpringNames;

@Component(SpringNames.UserMgr)
@DependsOn(SpringNames.Server)
@Lazy(false)
public class UserMgr extends BaseObjMgr implements IUserMgr {
	private static final Logger logger = LoggerFactory.getLogger(UserMgr.class);

	public void init() {
		logger.debug("usermgr init");
	}

	public void destroy() {
		logger.debug("usermgr destroy");
	}

	@Override
	protected Class getBean() {
		return User.class;
	}

	@Override
	public User getObj(String id) {
		return (User) super.getObj(id);
	}

}
