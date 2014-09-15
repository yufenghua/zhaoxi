package com.ylp.date.mgr;

/**
 * business exception 。the code will help to know what‘s wrong
 * 
 * @author Qiaolin Pan
 * 
 */
public class BusinessException extends RuntimeException {
	/**
	 * 连线已成功 无法再次连线
	 */
	public static final int CODE_LINE_FULL = 301;
	/**
	 * 当前创建者已对二人进行过连线
	 */
	public static final int BUILD_DONE = 302;
	/**
	 * 添加丘比特值时 发生错误
	 */
	public static final int CODE_ADD_CUPID = 303;
	private int code;

	public BusinessException(int code) {
		super(getErrorMsg(code));
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	private static String getErrorMsg(int code2) {
		return "系统内部错误！错误代码" + code2;
	}
}
