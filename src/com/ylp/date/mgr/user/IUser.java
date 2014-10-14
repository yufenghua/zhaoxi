package com.ylp.date.mgr.user;

import java.io.File;
import java.util.Date;

import com.ylp.date.mgr.IBaseObj;

public interface IUser extends IBaseObj {
	int MALE = 10;
	int FEMALE = 11;

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

}
