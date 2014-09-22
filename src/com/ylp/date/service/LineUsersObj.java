package com.ylp.date.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang.StringUtils;

import com.ylp.date.mgr.user.IUser;

public class LineUsersObj {
	private static final int MAX_MALE = 4;
	private String key = String.valueOf(System.currentTimeMillis())
			+ String.valueOf(this.hashCode());
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	private Lock read = lock.readLock();
	private Lock write = lock.writeLock();

	/**
	 * 对象的唯一标示
	 * 
	 * @return
	 */
	public String getKey() {
		return key;
	}

	private List<IUser> males = new ArrayList<IUser>(4);
	private List<IUser> females = new ArrayList<IUser>(4);

	/**
	 * 获取男性成员 list的最大长度为4 可能为空的list
	 * 
	 * @return
	 */
	public List<IUser> getMale() {
		read.lock();
		try {
			return new ArrayList<IUser>(males);
		} finally {
			read.unlock();
		}
	}

	/**
	 * 获取女性成员 list的最大长度为4 可能为空的list
	 * 
	 * @return
	 */
	public List<IUser> getFemale() {
		read.lock();
		try {
			return new ArrayList<IUser>(females);
		} finally {
			read.unlock();
		}
	}

	/**
	 * 是否包含某个成员
	 * 
	 * @param id
	 * @return
	 */
	public boolean contains(String id) {
		read.lock();
		try {
			if (StringUtils.isNotEmpty(id)) {
				for (IUser user : males) {
					if (StringUtils.equals(id, user.getId())) {
						return true;
					}
				}
				for (IUser user : females) {
					if (StringUtils.equals(id, user.getId())) {
						return true;
					}
				}
			}
			return false;
		} finally {
			read.unlock();
		}
	}

	/**
 * 
 */
	public void addUser(IUser user) {
		write.lock();
		try {
			int gender = user.getGender();
			if (gender == IUser.MALE && males.size() < MAX_MALE) {
				males.add(user);
			} else {
				females.add(user);
			}
		} finally {
			write.unlock();
		}
	}

	public void addUserList(List<IUser> users, boolean isMale) {
		write.lock();
		try {
			if (isMale) {
				males.clear();
				males.addAll(users);
			} else {
				females.clear();
				females.addAll(users);
			}
		} finally {
			write.unlock();
		}
	}
}
