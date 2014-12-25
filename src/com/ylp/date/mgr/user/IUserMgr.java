package com.ylp.date.mgr.user;

import java.util.List;

import com.ylp.date.login.Login;
import com.ylp.date.mgr.IBaseObj;
import com.ylp.date.mgr.IMgrBase;
import com.ylp.date.mgr.PageCondition;
import com.ylp.date.mgr.condtion.ConditionPair;

public interface IUserMgr extends IMgrBase {
	IUser getObj(String id);

	List<IUser> listUser(PageCondition condition, ConditionPair pair);

	void addCupidValue(String userId, String id) throws Exception;

	List<IUser> listUnAuditUsers(PageCondition condition);

}
