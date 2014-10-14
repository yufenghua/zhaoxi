package com.ylp.date.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

public abstract class BaseController {
	@RequestMapping
	public String action(HttpServletRequest req, HttpServletResponse res) {
		try {
			if (StringUtils.isNotEmpty(req.getCharacterEncoding())) {
				req.setCharacterEncoding("UTF-8");
			}
			return hanldleReq(req, res);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	protected abstract String hanldleReq(HttpServletRequest req,
			HttpServletResponse res) throws Exception;
}
