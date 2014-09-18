package com.ylp.date.service;

import java.util.List;

import com.ylp.date.mgr.user.IUser;

public class LineUsersObj {
	private String key;

	/**
	 * 对象的唯一标示
	 * 
	 * @return
	 */
	public String getKey() {
		return key;
	}

	/**
	 * 获取男性成员 list的最大长度为4 可能为空的list
	 * 
	 * @return
	 */
	public List<IUser> getMale() {
		return null;
	}

	/**
	 * 获取女性成员 list的最大长度为4 可能为空的list
	 * 
	 * @return
	 */
	public List<IUser> getFemale() {
		return null;
	}

	/**
	 * 是否包含某个成员
	 * 
	 * @param id
	 * @return
	 */
	public boolean contains(String id) {
		return true;
	}

	/**
 * 
 */
	public void addUser(IUser user) {

	}

	public void addUserList(List<IUser> users, boolean isMale) {

	}
}
