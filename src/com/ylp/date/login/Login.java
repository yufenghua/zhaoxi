package com.ylp.date.login;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.ylp.date.mgr.user.IUser;
import com.ylp.date.mgr.user.impl.User;
import com.ylp.date.security.NothingChecker;
import com.ylp.date.security.Pmchecker;
import com.ylp.date.security.impl.RolePmcheckMgr;
import com.ylp.date.server.Server;
import com.ylp.date.util.StringTools;

/**
 * login对象 对应登陆者 login对象可以作为权限判断的入口
 * 
 * @author Qiaolin Pan
 * 
 */
public class Login implements Serializable {
	private Map<String, Object> props = Collections
			.synchronizedMap(new HashMap<String, Object>());
	private IUser user;

	public IUser getUser() {
		return Server.getInstance().userMgr().getObj(user.getId());
	}

	/**
	 * 
	 * @return
	 */
	public boolean isLogined() {
		return getUser() != null;
	}

	/**
	 * 
	 * @return
	 */
	public Pmchecker getPmckecker() {
		return isLogined() ? Server.getInstance().getRoleCheckerMgr()
				.getChecker(getUser().getRole()) : NothingChecker.Checker;
	}

	/**
	 * login
	 * 
	 * @param userId
	 * @param passWord
	 * @return
	 */
	public boolean login(String userId, String passWord) {
		IUser user = Server.getInstance().userMgr().getObj(userId);
		if (user == null) {
			return false;
		}
		if (StringTools.validatePwd(passWord, user.getPwd())) {
			this.user = user;
			return true;
		}
		return false;
	}

	/**
	 * 免密码登陆，主要用于cookie 验证
	 * 
	 * @param userId
	 * @return
	 */
	public boolean loginWithoutPwd(String userId) {
		IUser user = Server.getInstance().userMgr().getObj(userId);
		if (user == null) {
			return false;
		}
		this.user = user;
		return true;
	}

	/**
	 * log out
	 * 
	 * @return
	 */
	public boolean logout() {
		this.user = null;
		props.clear();
		return true;
	}

	/**
	 * get prop from login
	 * 
	 * @param name
	 * @return
	 */
	public Object getProp(String name) {
		return props.get(name);
	}

	/**
	 * set prop from login if logout the porpties will lost
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public Object setProp(String name, Object value) {
		props.put(name, value);
		return value;
	}

}
