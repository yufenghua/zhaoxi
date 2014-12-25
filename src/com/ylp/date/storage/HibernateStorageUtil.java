package com.ylp.date.storage;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ylp.date.mgr.IBaseObj;
import com.ylp.date.server.Server;

/**
 * as the name
 * 
 * @author Qiaolin Pan
 * 
 */
public class HibernateStorageUtil {
	private static final Logger logger = LoggerFactory
			.getLogger(HibernateStorageUtil.class);

	/**
	 * add an instance
	 * 
	 * @param entityName
	 * @param ins
	 * @return
	 */
	public static final IBaseObj addObj(String entityName, IBaseObj ins) {
		Session session = Server.getInstance().openSession();
		try {
			session.beginTransaction();
			try {
				IBaseObj iBaseObj = (IBaseObj) session.get(entityName,
						session.save(entityName, ins));
				session.getTransaction().commit();
				return iBaseObj;
			} catch (Exception e) {
				session.getTransaction().rollback();
				logger.error(
						"插入对象时出现异常,对象id:" + ins.getId() + " 对象标题:"
								+ ins.getCaption(), e);
				if (e instanceof RuntimeException) {
					throw (RuntimeException) e;
				}
				throw new RuntimeException(e);
			}
		} finally {
			session.close();
		}

	}

	/**
	 * remove an instance
	 * 
	 * @param id
	 * @return
	 */
	public static final boolean removeObj(String entityName, IBaseObj object) {
		Session session = Server.getInstance().openSession();
		try {
			session.beginTransaction();
			try {
				session.delete(entityName, object);
				session.getTransaction().commit();
				return true;
			} catch (Exception e) {
				session.getTransaction().rollback();
				logger.error("插入对象时出现异常,对象id:" + object.getId() + " 对象类型:"
						+ entityName, e);
				if (e instanceof RuntimeException) {
					throw (RuntimeException) e;
				}
				throw new RuntimeException(e);
			}
		} finally {
			session.close();
		}
	}

	/**
	 * update an instance
	 * 
	 * @param entityName
	 * @param id
	 * @param obj
	 * @return
	 */
	public static final boolean updateObj(String entityName, String id,
			IBaseObj obj) {
		Session session = Server.getInstance().openSession();
		try {
			session.beginTransaction();
			try {
				session.saveOrUpdate(entityName, obj);
				session.getTransaction().commit();
				return true;
			} catch (Exception e) {
				session.getTransaction().rollback();
				logger.error("插入对象时出现异常,对象id:" + obj.getId() + " 对象类型:"
						+ entityName, e);
				if (e instanceof RuntimeException) {
					throw (RuntimeException) e;
				}
				throw new RuntimeException(e);
			}

		} finally {
			session.close();
		}
	}
}
