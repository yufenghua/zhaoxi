package com.ylp.date.security;

/**
 * permission checker。basiclly it will check one has or has not the permission
 * to do something to some object。 however ，now we only keep eyes on oper
 * 
 * @author Qiaolin Pan
 * 
 */
public interface Pmchecker {
	/**
	 * check permission
	 * 
	 * @param oper
	 * @param obj
	 * @return
	 */
	public boolean check(String oper, Object obj);

	/**
	 * check permission with the option to throw exception
	 * 
	 * @param oper
	 * @param obj
	 * @param throwex
	 * @return
	 */
	public boolean check(String oper, Object obj, boolean throwex);

	/**
	 * ignore oper object
	 * @param oper
	 * @return
	 */
	public boolean check(String oper);

	/**
	 * 
	 * @param oper
	 * @param throwex
	 * @return
	 */
	public boolean check(String oper, boolean throwex);
}
