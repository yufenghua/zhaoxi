package com.ylp.date.security.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.ylp.date.security.NothingChecker;
import com.ylp.date.security.Pmchecker;
import com.ylp.date.server.SpringNames;

@Component(SpringNames.RolePmChecker)
@DependsOn(SpringNames.Server)
@Lazy(false)
public class RolePmcheckMgr {
	private ConcurrentMap<String, RolePmChecker> checkerMap;

	public void init() {
		checkerMap = new ConcurrentHashMap<String, RolePmChecker>();
	}

	public void destroy() {
		checkerMap.clear();
		checkerMap = null;
	}

	public Pmchecker getChecker(String role) {
		if(StringUtils.isNotEmpty(role)){
			checkerMap.putIfAbsent(role, new RolePmChecker(role));
			return checkerMap.get(role);
		}
		return new NothingChecker();
	}
}
