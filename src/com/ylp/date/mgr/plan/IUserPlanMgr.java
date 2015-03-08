package com.ylp.date.mgr.plan;

import java.util.List;

import com.ylp.date.mgr.IMgrBase;

public interface IUserPlanMgr extends IMgrBase {
	List<IUserPlan> listByUser(String userId);

	List<IUserPlan> listByUser(String userId, boolean isValid);

	List<IUserPlan> listNew(String newId, int size);

	List<IUserPlan> listOld(String newId, int size);
}
