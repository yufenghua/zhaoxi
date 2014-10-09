package com.ylp.date.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ylp.date.login.Login;

public class ControlUtil {
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
}
