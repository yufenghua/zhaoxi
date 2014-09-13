package com.ylp.date.security.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.ylp.date.server.SpringNames;

@Component(SpringNames.RolePmChecker)
@DependsOn(SpringNames.Server)
@Lazy(false)
public class RolePmcheckMgr {
	private Map<String, RolePmChecker> checkerMap;

	public void init() {
		checkerMap = new HashMap<String, RolePmChecker>();
	}

	public void destroy() {
		checkerMap.clear();
		checkerMap = null;
	}

	public synchronized RolePmChecker getChecker(String role) {
		if (checkerMap.containsKey(role)) {
			return checkerMap.get(role);
		}
		RolePmChecker checker = new RolePmChecker(role);
		checkerMap.put(role, checker);
		return checker;
	}
}
