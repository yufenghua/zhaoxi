package com.ylp.date.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

public abstract class BaseController {
	private Logger logger=LoggerFactory.getLogger(getClass());
	public static final String DATE_SERVER_EXCEPTION = "date_server_exception";

	@RequestMapping
	public String action(HttpServletRequest req, HttpServletResponse res) {
		try {
			if (StringUtils.isNotEmpty(req.getCharacterEncoding())) {
				req.setCharacterEncoding("UTF-8");
			}
			logger.info("接收到请求");
			res.setCharacterEncoding("UTF-8");
			return hanldleReq(req, res);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("请求出现错误", e);
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
