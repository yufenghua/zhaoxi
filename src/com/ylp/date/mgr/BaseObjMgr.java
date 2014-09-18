package com.ylp.date.mgr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ylp.date.mgr.condtion.ConditionPair;
import com.ylp.date.mgr.condtion.impl.HibernateBuilder;
import com.ylp.date.server.Server;
import com.ylp.date.storage.HibernateStorageUtil;

/**
 * abstract class for object manager
 * 
 * @author Qiaolin Pan
 * 
 */
public abstract class BaseObjMgr implements IMgrBase {
	private static final Logger logger = LoggerFactory
			.getLogger(BaseObjMgr.class);
	private List<ObjListener> lists = new ArrayList<ObjListener>(5);

	/**
	 * get the real class of ibaseobj
	 * 
	 * @return
	 */
	protected abstract Class getBean();

	public void regListener(ObjListener lis) {
		lists.add(lis);
	}

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

	public List<IBaseObj> list(PageCondition page, ConditionPair cond) {
		Session session = Server.getInstance().getCurentSession();
		try {
			Criteria criteria = session.createCriteria(getBean());
			setPage(criteria, page);
			setCondition(criteria, cond);
			return criteria.list();
		} catch (Exception e) {
			Server.getInstance().handleException(e);
			logger.error("查询数据时发生异常", e);
		} finally {
			session.getTransaction().commit();
		}
		return Collections.emptyList();
	}

	/**
	 * 
	 * @param criteria
	 * @param cond
	 * @throws Exception
	 */
	protected void setCondition(Criteria criteria, ConditionPair cond)
			throws Exception {
		if (cond == null) {
			return;
		}
		HibernateBuilder builder = new HibernateBuilder(criteria);
		cond.build(builder);
	}

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

	public IBaseObj add(IBaseObj obj) {
		IBaseObj addObj = HibernateStorageUtil.addObj(getBean().getName(), obj);
		for (ObjListener lis : lists) {
			lis.fileAdd(addObj);
		}
		return addObj;
	}

	public boolean remove(String id) {
		IBaseObj user = this.getObj(id);
		boolean removeObj = HibernateStorageUtil.removeObj(getBean().getName(),
				user);
		for (ObjListener lis : lists) {
			lis.fireRemove(id);
		}
		return removeObj;
	}

	public boolean update(String id, IBaseObj obj) throws Exception {
		boolean updateObj = HibernateStorageUtil.updateObj(getBean().getName(),
				id, obj);
		for (ObjListener lis : lists) {
			lis.fireUpdate(id, obj);
		}
		return updateObj;
	}

}
