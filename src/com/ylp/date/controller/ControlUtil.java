package com.ylp.date.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ylp.date.login.Login;

public class ControlUtil {
	private static final Logger logger=LoggerFactory.getLogger(ControlUtil.class);
	private static final int COOKIE_EXPIRETIME = 1000*60*120;
	private static final String DATE_LOGIN_USER = "DATE_LOGIN_USER";
	private static final String DATE_LOGIN = "DATE_LOGIN";

	public static final Login getLogin(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Login login = (Login) session.getAttribute(DATE_LOGIN);
		if (login == null) {
			login = new Login();
			session.setAttribute(DATE_LOGIN, login);
		}
		return login;
	}
	public static final String getImgUrl(HttpServletRequest req,String userId){
		String contextPath = req.getContextPath();
		if(!StringUtils.isNotEmpty(contextPath)){
			contextPath="/";
		}
		if(!StringUtils.endsWith(contextPath, "/")){
			contextPath=contextPath+"/";
		}
		return contextPath+"user/userinfo.do?action=img&userid="+userId;
	}
	/**
	 * 将登陆信息写入cookie
	 * @param login
	 * @param res
	 */
	public static final void addCookieForLogin(Login login,HttpServletResponse res){
		if(!login.isLogined()){
			return;
		}
		Cookie cookie=new Cookie(DATE_LOGIN_USER, login.getUser().getId());
		cookie.setMaxAge(COOKIE_EXPIRETIME);
		res.addCookie(cookie);
	}
	/**
	 * 
	 * @param res
	 */
	public static final boolean checkCookie(HttpServletRequest req){
		Login login=ControlUtil.getLogin(req);
		if(login.isLogined()){
			return true;
		}
		Cookie[] cookies = req.getCookies();
		if(cookies==null){
			return false;
		}
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie=cookies[i];
			if(StringUtils.equals(DATE_LOGIN_USER, cookie.getName())){
				String userName=cookie.getValue();
				if(!login.isLogined()){
					try{
						return login.loginWithoutPwd(userName);
					}catch(Exception e){
						logger.error("登陆失败", e);
						return false;
					}
				}
			}
		}
		return false;
	}
	public static void removeLoginCookie(HttpServletResponse res) {
		Cookie cookie=new Cookie(DATE_LOGIN_USER, null);
		cookie.setMaxAge(0);
		res.addCookie(cookie);
	}
}
