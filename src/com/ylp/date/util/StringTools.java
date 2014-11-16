package com.ylp.date.util;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 字符串工具类
 * 
 * @author Qiaolin Pan
 * 
 */
public class StringTools {
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy年mm月dd日");

	/**
	 * 对密码进行加密
	 * 
	 * @param passworld
	 * @return
	 */
	public static final String encryptPassword(String passworld) {
		try {
			return PasswordHash.createHash(passworld);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param password
	 * @param hash
	 * @return
	 */
	public static final boolean validatePwd(String password, String hash) {
		try {
			return PasswordHash.validatePassword(password, hash);
		} catch (Exception e) {
			return false;
		}
	}

	public static final String formateDate(Date date) {
		return format.format(date);
	}
}
