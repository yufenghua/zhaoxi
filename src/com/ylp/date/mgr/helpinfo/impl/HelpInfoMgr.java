package com.ylp.date.mgr.helpinfo.impl;

import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.ylp.date.mgr.BaseObjMgr;
import com.ylp.date.mgr.IBaseObj;
import com.ylp.date.mgr.condtion.impl.Condition;
import com.ylp.date.mgr.condtion.impl.SimglePair;
import com.ylp.date.mgr.helpinfo.IHelpInfo;
import com.ylp.date.mgr.helpinfo.IHelpMgr;
import com.ylp.date.server.SpringNames;
import com.ylp.date.util.CollectionTool;

@Component(SpringNames.HelpInfoMgr)
@DependsOn(SpringNames.Server)
@Lazy(false)
public class HelpInfoMgr extends BaseObjMgr implements IHelpMgr {
	public void init() {

	}

	public void destroy() {

	}

	@Override
	public boolean checkRead(String user, String version) {
		IHelpInfo info = getHelpInfo(user, version);
		return info == null ? false : info.isReaded();
	}

	public IHelpInfo getHelpInfo(String user, String version) {
		Condition condition = new Condition();
		condition.eq("user", user);
		condition.eq("version", version);
		SimglePair pair = new SimglePair();
		pair.setFirst(condition);
		List<IBaseObj> list = list(null, pair);
		if (CollectionTool.checkNull(list)) {
			return null;
		}
		IHelpInfo info = (IHelpInfo) list.get(0);
		return info;
	}

	@Override
	public boolean read(String user, String version) {
		HelpInfo helpInfo = (HelpInfo) getHelpInfo(user, version);
		try {
			if (helpInfo == null) {
				helpInfo = new HelpInfo();
				helpInfo.setReaded(true);
				helpInfo.setUser(user);
				helpInfo.setVersion(version);
				helpInfo.setReadTime(new Date());
				add(helpInfo);
			} else {
				helpInfo.setReaded(true);
				update(helpInfo.getId(), helpInfo);
			}
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected Class getBean() {
		return HelpInfo.class;
	}

}
