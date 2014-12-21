package com.ylp.date.service;

import java.util.ArrayList;
import java.util.Collections;
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
	private int size = MAX_MALE;

	/**
	 * 对象的唯一标示
	 * 
	 * @return
	 */
	public String getKey() {
		return key;
	}

	private List<String> males = new ArrayList<String>(4);
	private List<String> females = new ArrayList<String>(4);

	/**
	 * 获取男性成员 list的最大长度为4 可能为空的list
	 * 
	 * @return
	 */
	public List<String> getMale() {
		read.lock();
		try {
			return Collections.unmodifiableList(males);
		} finally {
			read.unlock();
		}
	}

	/**
	 * 获取女性成员 list的最大长度为4 可能为空的list
	 * 
	 * @return
	 */
	public List<String> getFemale() {
		read.lock();
		try {
			return Collections.unmodifiableList(females);
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
				return males.contains(id)||females.contains(id);
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
				males.add(user.getId());
			} else {
				females.add(user.getId());
			}
		} finally {
			write.unlock();
		}
	}

	public void addUserList(List<IUser> users, boolean isMale) {
		write.lock();
		try {
			List<String> lists=null;
			if (isMale) {
				males.clear();
				lists=males;
			} else {
				females.clear();
				lists=females;
			}
			for (IUser user : users) {
				lists.add(user.getId());
			}
		} finally {
			write.unlock();
		}
	}

	public boolean isFulled() {
		read.lock();
		try {
			return males.size() == size && females.size() == size;
		} finally {
			read.unlock();
		}
	}

	public boolean isMaleFulled() {
		read.lock();
		try {
			return males.size() == size;
		} finally {
			read.unlock();
		}
	}

	public boolean isFemaleFulled() {
		read.lock();
		try {
			return females.size() == size;
		} finally {
			read.unlock();
		}
	}

	/**
	 * 获取size
	 * 
	 * @return
	 */
	public int getSize() {
		return size;
	}

	/**
	 * 设置size
	 * 
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
	}
}
