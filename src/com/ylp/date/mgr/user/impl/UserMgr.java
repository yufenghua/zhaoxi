package com.ylp.date.mgr.user.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.ylp.date.mgr.BaseObjMgr;
import com.ylp.date.mgr.Condition;
import com.ylp.date.mgr.IBaseObj;
import com.ylp.date.mgr.PageCondition;
import com.ylp.date.mgr.user.IUserMgr;
import com.ylp.date.server.Server;
import com.ylp.date.server.SpringNames;
import com.ylp.date.storage.HibernateStorageUtil;

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

	@Override
	protected void setCondition(Criteria criteria, Condition cond) {
		// TODO Auto-generated method stub
		
	}

}
