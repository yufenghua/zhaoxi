package com.ylp.date.mgr.user.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.ylp.date.login.Login;
import com.ylp.date.mgr.BaseObjMgr;
import com.ylp.date.mgr.BusinessException;
import com.ylp.date.mgr.IBaseObj;
import com.ylp.date.mgr.PageCondition;
import com.ylp.date.mgr.condtion.ConditionPair;
import com.ylp.date.mgr.condtion.impl.Condition;
import com.ylp.date.mgr.condtion.impl.SimglePair;
import com.ylp.date.mgr.user.IUser;
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

	@Override
	public IBaseObj add(IBaseObj obj) {
		String id = obj.getId();
		Object lock = getLock(id);
		synchronized (lock) {
			IBaseObj old = getObj(id);
			if (old != null) {
				throw new BusinessException(BusinessException.CODE_OBJ_EXISTS,
						id);
			}
			return super.add(obj);
		}
	}

	public List<IUser> listUser(PageCondition page, ConditionPair pair) {
		List<IBaseObj> list = list(page, pair);
		if (list.isEmpty()) {
			return Collections.emptyList();
		}
		List<IUser> result = new ArrayList<IUser>(list.size());
		for (IBaseObj iBaseObj : list) {
			result.add((IUser) iBaseObj);
		}
		return result;
	}

	/**
	 * @throws Exception
	 * 
	 */
	public void addCupidValue(String userId, String id) throws Exception {
		Object lock = getLock(userId);
		synchronized (lock) {
			User user = getObj(userId);
			user.setCupidvalue(user.getCupidvalue() + calcScore(userId, id));
			update(userId, user);
		}
	}

	/**
	 * 
	 * @param userId
	 * @param id
	 * @return
	 */
	public int calcScore(String userId, String id) {
		return 1;
	}

	@Override
	public List<IUser> listUnAuditUsers(PageCondition condition) {
		SimglePair pair = new SimglePair();
		Condition first = new Condition();
		first.eq("status", 0);
		pair.setFirst(first);
		// 擦除泛型
		List list = list(condition, pair);
		return list;
	}
}
