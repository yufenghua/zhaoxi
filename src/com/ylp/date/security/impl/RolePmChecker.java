package com.ylp.date.security.impl;

import org.apache.commons.lang.StringUtils;

import com.ylp.date.security.Pmchecker;

public class RolePmChecker implements Pmchecker {

	private static final String ROLE_AUDITOR = "auditor";
	private String role;

	public RolePmChecker(String role) {
		this.role = role;
	}

	public boolean check(String oper, Object obj) {
		return StringUtils.equals(role, ROLE_AUDITOR);
	}

	public boolean check(String oper, Object obj, boolean throwex) {
		return StringUtils.equals(role, ROLE_AUDITOR);
	}

	public boolean check(String oper) {
		return StringUtils.equals(role, ROLE_AUDITOR);
	}

	public boolean check(String oper, boolean throwex) {
		return StringUtils.equals(role, ROLE_AUDITOR);
	}

}
