package com.ylp.date.mgr;

/**
 * business exception 。the code will help to know what‘s wrong
 * 
 * @author Qiaolin Pan
 * 
 */
public class BusinessException extends RuntimeException {
	/**
	 * 用户已存在
	 */
	public static final int CODE_OBJ_EXISTS = 102;
	/**
	 * 用户不存在
	 */
	public static final int CODE_NO_SUCH_USER = 101;
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
	/**
	 * 没有可用的鲜花
	 */
	public static final int CODE_NO_FLOWER = 304;
	/**
	 * 已经存在关系
	 */
	public static final int CODE_RELATION_EXIST = 305;
	/**
	 * 已经送过花了
	 */
	public static final int CODE_SEND_FLOWER = 306;
	/**
	 * 查询老消息时，没有执行最新的消息id
	 */
	public static final int PLAN_LISTOLD_PLANID_NULL = 401;
	private int code;
	private String objId;

	public BusinessException(int code) {
		super(getErrorMsg(code));
		this.code = code;
	}

	public BusinessException(int code, String objId) {
		this.code = code;
		this.objId = objId;
	}

	public int getCode() {
		return code;
	}

	private static String getErrorMsg(int code2) {
		return "系统内部错误！错误代码" + code2;
	}
}
