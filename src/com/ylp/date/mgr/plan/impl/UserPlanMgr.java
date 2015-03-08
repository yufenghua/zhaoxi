package com.ylp.date.mgr.plan.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.ylp.date.mgr.BaseObjMgr;
import com.ylp.date.mgr.BusinessException;
import com.ylp.date.mgr.IBaseObj;
import com.ylp.date.mgr.condtion.impl.Condition;
import com.ylp.date.mgr.condtion.impl.SimglePair;
import com.ylp.date.mgr.plan.IUserPlan;
import com.ylp.date.mgr.plan.IUserPlanMgr;
import com.ylp.date.server.SpringNames;

@Component(SpringNames.UserPlanMgr)
@DependsOn(SpringNames.Server)
@Lazy(false)
public class UserPlanMgr extends BaseObjMgr implements IUserPlanMgr {

	private static final String HQL_NEW_NULL = "from UserPlan order by date desc";
	private static final String HQL_OLD_ID = "from UserPlan  where date< (select date from UserPlan where id=?) order by date desc";
	private static final String HQL_NEW_ID = "from UserPlan  where date> (select date from UserPlan where id=?) order by date desc";

	@Override
	public List<IUserPlan> listByUser(String userId) {
		Condition cond = new Condition();
		cond.eq("userId", userId);
		SimglePair pair = new SimglePair();
		pair.setFirst(cond);
		@SuppressWarnings("rawtypes")
		List list = list(null, pair);
		return list;
	}

	@Override
	public List<IUserPlan> listByUser(String userId, boolean isValid) {
		Condition cond = new Condition();
		cond.eq("userId", userId);
		SimglePair pair = new SimglePair();
		pair.setFirst(cond);
		@SuppressWarnings("rawtypes")
		List list = list(null, pair);// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IUserPlan> listNew(String newId, int size) {
		boolean empty = StringUtils.isEmpty(newId);
		String hql = empty ? HQL_NEW_NULL : HQL_NEW_ID;
		List executeQuery = executeQuery(hql, empty?null:new Object[] { newId }, size);
		return executeQuery;
	}

	@Override
	public List<IUserPlan> listOld(String newId, int size) {
		if (StringUtils.isEmpty(newId)) {
			throw new BusinessException(
					BusinessException.PLAN_LISTOLD_PLANID_NULL);
		}
		List executeQuery = executeQuery(HQL_OLD_ID, new Object[] { newId },
				size);
		return executeQuery;
	}

	@Override
	protected Class getBean() {
		// TODO Auto-generated method stub
		return UserPlan.class;
	}

}
