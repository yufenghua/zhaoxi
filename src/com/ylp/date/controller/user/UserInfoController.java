package com.ylp.date.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ylp.date.controller.BaseController;
import com.ylp.date.server.Server;

@Controller
@RequestMapping("/user/userinfo")
public class UserInfoController extends BaseController {

	@Override
	protected String hanldleReq(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String action = req.getParameter("action");
		String userId = req.getParameter("userid");
		if (StringUtils.isNotEmpty(userId)) {
			req.setAttribute("userid", userId);
			req.setAttribute("user",
					Server.getInstance().userMgr().getObj(userId));
		}
		if (!StringUtils.isNotEmpty(action)) {
			return "pages/detail";
		}
		return null;
	}

}
