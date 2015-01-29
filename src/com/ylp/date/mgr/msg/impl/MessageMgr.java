package com.ylp.date.mgr.msg.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.ylp.date.mgr.BaseObjMgr;
import com.ylp.date.mgr.IBaseObj;
import com.ylp.date.mgr.PageCondition;
import com.ylp.date.mgr.condtion.ConditionPair;
import com.ylp.date.mgr.condtion.impl.Condition;
import com.ylp.date.mgr.condtion.impl.SimglePair;
import com.ylp.date.mgr.msg.IMessage;
import com.ylp.date.mgr.msg.IMsgMgr;
import com.ylp.date.mgr.relation.IRelation;
import com.ylp.date.server.Server;
import com.ylp.date.server.SpringNames;

@Component(SpringNames.MessageMgr)
@DependsOn(SpringNames.Server)
@Lazy(false)
public class MessageMgr extends BaseObjMgr implements IMsgMgr {
	private static final String unreadCount = "select count(*) from Message "
			+ "where "
			+ "(sender in (select distinct(one) from UserRelation where otherOne=? and type=? and recognition=?) "
			+ "or "
			+ "sender in (select distinct(otherOne) from UserRelation where one=? and type=? and recognition=?)) and readed=?";
	private static final Logger logger = LoggerFactory
			.getLogger(MessageMgr.class);

	public void init() {

	}

	public void destroy() {

	}

	@Override
	public List<IMessage> listUnRead(String sender, String receiver) {
		Session session = Server.getInstance().openSession();
		try {
			session.beginTransaction();
			SimglePair pair = new SimglePair();
			Condition condition = new Condition();
			if (StringUtils.isNotEmpty(sender)) {
				condition.eq("sender", sender);
			}
			if (StringUtils.isNotEmpty(receiver)) {
				condition.eq("receiver", receiver);
			}
			condition.eq("readed", false);
			pair.setFirst(condition);
			List result = list(null, pair, session);
			session.getTransaction().commit();
			return result;
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	protected void read(String sender, String receiver, Session session) {
		String hql = "update Message set readed=? where readed=? and sender=? and receiver=?";
		executeUpdate(session, hql, new Object[] { true, false, sender,
				receiver });
	}

	@Override
	protected List<IBaseObj> list(PageCondition page, ConditionPair cond,
			Session session) throws Exception {
		Criteria criteria = session.createCriteria(getBean());
		criteria.addOrder(Order.asc("date"));
		setPage(criteria, page);
		setCondition(criteria, cond);
		return criteria.list();
	}

	@Override
	protected Class getBean() {
		return Message.class;
	}

	@Override
	public void read(String sender, String receiver) {
		Session session = Server.getInstance().openSession();
		try {
			session.beginTransaction();
			read(sender, receiver, session);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	@Override
	public long getUnreadCountForLine(String id) {
		return getUnreadCount(id, IRelation.TYPE_LINE, IRelation.RECOG_LINE);
	}

	private long getUnreadCount(String id, int typeLine, int recog) {
		Session session = Server.getInstance().openSession();
		try {
			Query query = session.createQuery(unreadCount);
			// private static final String unreadCount =
			// "select count(*) from Message "
			// + "where "
			// +
			// "sender in (select distinct(one) from UserRelation where otherOne=? and type=? and recognition=?) "
			// + "or "
			// +
			// "sender in (select distinct(otherOne) from UserRelation where one=? and type=? and recognition=?)";
			query.setString(0, id);
			query.setInteger(1, typeLine);
			query.setInteger(2, recog);
			query.setString(3, id);
			query.setInteger(4, typeLine);
			query.setInteger(5, recog);
			query.setBoolean(6, false);
			return (Long) query.uniqueResult();
		} catch (Exception e) {
			Server.getInstance().handleException(e);
			logger.error("查询数据时发生异常", e);
		} finally {
			session.close();
		}
		return 0;
	}

	@Override
	public long getUnreadCountForFlower(String id) {

		return getUnreadCount(id, IRelation.TYPE_FLOWER, IRelation.RECOG_FLOWER);
	}

}
