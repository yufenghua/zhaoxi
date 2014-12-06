package com.ylp.date.app;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.ylp.date.controller.ControlUtil;
import com.ylp.date.login.Login;

public class LoginFilter implements Filter {

	private static final String[] SEARCH_STRINGS = new String[] { ".css",
			".js", ".html", ".jpg", ".png" };

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) arg0;
		StringBuffer url = req.getRequestURL();
		String string = url.toString();
		if (StringUtils.endsWithAny(string, SEARCH_STRINGS)
				|| StringUtils.contains(string, "login.do")
				|| StringUtils.contains(string, "join.do")) {
			arg2.doFilter(arg0, arg1);
			return;
		}
		Login login = ControlUtil.getLogin(req);
		//尝试使用cookie登陆
		ControlUtil.checkCookie(req);
		if (login.isLogined()) {
			arg2.doFilter(arg0, arg1);
			return;
		}
		HttpServletResponse res = (HttpServletResponse) arg1;
		res.sendRedirect(req.getContextPath() + "/user/join.do");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
