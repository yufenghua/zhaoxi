package com.ylp.date.mgr;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.ylp.date.server.Server;
import com.ylp.date.storage.HibernateStorageUtil;

/**
 * abstract class for object manager
 * 
 * @author Qiaolin Pan
 * 
 */
public abstract class BaseObjMgr implements IMgrBase {
	/**
	 * get the real class of ibaseobj
	 * 
	 * @return
	 */
	protected abstract Class getBean();

	public IBaseObj getObj(String id) {
		Session session = Server.getInstance().getCurentSession();
		try {
			return (IBaseObj) session.get(getBean(), id);
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
			Criteria criteria = session.createCriteria(getBean());
			setPage(criteria, page);
			setCondition(criteria, cond);
			return criteria.list();
		} finally {
			session.getTransaction().commit();
		}
	}

	/**
	 * 
	 * @param criteria
	 * @param cond
	 */
	protected abstract void setCondition(Criteria criteria, Condition cond);

	/**
	 * handle with pageCondition 。usually subclass don`t need to override this
	 * method
	 * 
	 * @param criteria
	 * @param page
	 */
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
		return HibernateStorageUtil.addObj(getBean().getName(), obj);
	}

	public boolean remove(String id) {
		IBaseObj user;
		try {
			user = (IBaseObj) getBean().newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(
					"must have an accessable none parameter constructor");
		} catch (IllegalAccessException e) {
			throw new RuntimeException(
					"must have an accessable none parameter constructor");
		}
		user.setId(id);
		return HibernateStorageUtil.removeObj(getBean().getName(), user);
	}

	public boolean update(String id, IBaseObj obj) throws Exception {
		return HibernateStorageUtil.updateObj(getBean().getName(), id, obj);
	}

}
