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
		boolean equals = StringUtils.equals(role, ROLE_AUDITOR);
		if (!equals) {
			throw new RuntimeException("没有权限!");
		}
		return equals;
	}

	public boolean check(String oper, Object obj, boolean throwex) {
		boolean equals = StringUtils.equals(role, ROLE_AUDITOR);
		if (!equals) {
			throw new RuntimeException("没有权限!");
		}
		return equals;
	}

	public boolean check(String oper) {
		return StringUtils.equals(role, ROLE_AUDITOR);
	}

	public boolean check(String oper, boolean throwex) {
		boolean equals = StringUtils.equals(role, ROLE_AUDITOR);
		if (!equals) {
			throw new RuntimeException("没有权限!");
		}
		return equals;
	}

}
