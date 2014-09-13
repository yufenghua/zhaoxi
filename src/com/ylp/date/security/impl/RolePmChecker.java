package com.ylp.date.security.impl;

import com.ylp.date.security.Pmchecker;

public class RolePmChecker implements Pmchecker {

	private String role;

	public RolePmChecker(String role) {
		this.role = role;
	}

	public boolean check(String oper, Object obj) {
		return true;
	}

	public boolean check(String oper, Object obj, boolean throwex) {
		return true;
	}

	public boolean check(String oper) {
		return true;
	}

	public boolean check(String oper, boolean throwex) {
		return true;
	}

}
