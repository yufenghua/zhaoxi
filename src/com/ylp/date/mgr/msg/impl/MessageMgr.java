package com.ylp.date.mgr.msg.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
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
import com.ylp.date.server.Server;
import com.ylp.date.server.SpringNames;

@Component(SpringNames.MessageMgr)
@DependsOn(SpringNames.Server)
@Lazy(false)
public class MessageMgr extends BaseObjMgr implements IMsgMgr {
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
			condition.eq("sender", sender);
			condition.eq("receiver", receiver);
			condition.eq("readed", false);
			pair.setFirst(condition);
			List result = list(null, pair, session);
			String hql = "update Message set readed=? where readed=? and sender=? and receiver=?";
			executeUpdate(session, hql, new Object[] { true, false, sender,
					receiver });
			session.getTransaction().commit();
			return result;
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
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

}
