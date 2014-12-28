package com.ylp.date.mgr.user;

import java.io.File;
import java.util.Collection;
import java.util.Date;

import com.ylp.date.mgr.IBaseObj;

public interface IUser extends IBaseObj {
	int MALE = 10;
	int FEMALE = 11;
	int STATE_UNAUDIT = 0;
	int STATE_AUDITED = 1;
	int STATE_AUDITBACK=-1;
	// 相关常量和方法暴露在父类中
	public static final int AGE_18_20 = 1;
	public static final int AGE_20_23 = 2;
	public static final int AGE_23_26 = 3;
	public static final int AGE_26_30 = 4;
	public static final int AGE_30_OLD = 5;
	/**
	 * get password the password is encrypted
	 * 
	 * @return encrypted password
	 */
	String getPwd();

	/**
	 * 
	 * @return
	 */
	Date getCreateDate();

	/**
	 * 
	 * @return
	 */
	String getEmail();

	/**
	 * 
	 * @return
	 */
	int getCardType();

	File getCardImg();

	int getCupidvalue();

	int getGender();

	int getStatus();

	String getModeratorId();

	String getInviterId();

	Date getBirth();

	File getImge();

	int getShowNum();

	int getLastShowNum();

	String getRole();

	IUser getInviter();

	/**
	 * the flower count that can be used
	 * 
	 * @return
	 */
	int getFlower();

	/**
	 * the time when the last line was builded success
	 * 
	 * @return
	 */
	Date getLastLine();

	int getAgeRange();

}
