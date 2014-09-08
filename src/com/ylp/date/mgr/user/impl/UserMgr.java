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
public class UserMgr implements IUserMgr {
	private static final Logger logger = LoggerFactory.getLogger(UserMgr.class);

	public void init() {
		logger.debug("usermgr init");
	}

	public void destroy() {
		logger.debug("usermgr destroy");
	}

	public IBaseObj getObj(String id) {
		Session session = Server.getInstance().getCurentSession();
		try {
			return (IBaseObj) session.get(User.class, id);
		} finally {
			session.getTransaction().commit();
		}
	}

	public List<IBaseObj> list() {
		return list(null);
	}

	public List<IBaseObj> list(PageCondition page) {
		return list(page, null);
	}

	public List<IBaseObj> list(PageCondition page, Condition cond) {
		Session session = Server.getInstance().getCurentSession();
		try {
			Criteria criteria = session.createCriteria(User.class);
			setPage(criteria, page);
			setCondition(criteria, cond);
			return criteria.list();
		} finally {
			session.getTransaction().commit();
		}
	}

	protected void setCondition(Criteria criteria, Condition cond) {
		// TODO Auto-generated method stub

	}

	protected void setPage(Criteria criteria, PageCondition page) {
		if (page == null) {
			return;
		}
		int start = page.getStart();
		int size = page.getLength();
		if (start == -1 || size == -1) {
			return;
		}
		criteria.setFirstResult(start);
		criteria.setMaxResults(size);
	}

	public boolean add(IBaseObj obj) {
		return HibernateStorageUtil.addObj(User.class.getName(), obj);
	}

	public boolean remove(String id) {
		User user = new User();
		user.setId(id);
		return HibernateStorageUtil.removeObj(User.class.getName(), user);
	}

	public boolean update(String id, IBaseObj obj) throws Exception {
		return HibernateStorageUtil.updateObj(User.class.getName(), id, obj);
	}

}
