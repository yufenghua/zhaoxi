package com.ylp.date.util;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * 字符串工具类
 * 
 * @author Qiaolin Pan
 * 
 */
public class StringTools {
	/**
	 * 对密码进行加密
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
	public static final boolean validatePwd(String password,String hash){
		try {
			return PasswordHash.validatePassword(password, hash);
		} catch(Exception e){
			return false;
		}
	}
}
