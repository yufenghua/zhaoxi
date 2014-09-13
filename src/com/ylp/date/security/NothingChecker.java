package com.ylp.date.security;

public class NothingChecker implements Pmchecker {
	public static final NothingChecker Checker = new NothingChecker();

	public boolean check(String oper, Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean check(String oper, Object obj, boolean throwex) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean check(String oper) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean check(String oper, boolean throwex) {
		// TODO Auto-generated method stub
		return false;
	}

}
