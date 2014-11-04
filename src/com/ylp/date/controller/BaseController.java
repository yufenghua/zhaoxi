package com.ylp.date.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

public abstract class BaseController {
	public static final String DATE_SERVER_EXCEPTION = "date_server_exception";

	@RequestMapping
	public String action(HttpServletRequest req, HttpServletResponse res) {
		try {
			if (StringUtils.isNotEmpty(req.getCharacterEncoding())) {
				req.setCharacterEncoding("UTF-8");
			}
			res.setCharacterEncoding("UTF-8");
			return hanldleReq(req, res);
		} catch (Exception e) {
			req.setAttribute(DATE_SERVER_EXCEPTION, e);
			if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			}
			throw new RuntimeException(e);
		}
	}

	protected abstract String hanldleReq(HttpServletRequest req,
			HttpServletResponse res) throws Exception;
}
