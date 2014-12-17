package com.ylp.date.security.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

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

	public RolePmChecker getChecker(String role) {
		checkerMap.putIfAbsent(role, new RolePmChecker(role));
		return checkerMap.get(role);
	}
}
