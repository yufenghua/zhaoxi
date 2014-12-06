package com.ylp.date.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.ParameterMethodNameResolver;

import com.ylp.date.controller.BaseController;
import com.ylp.date.controller.ControlUtil;
import com.ylp.date.login.Login;

@Controller
@RequestMapping("/user/login")
public class LoginController extends BaseController {

	public String login(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Login login = ControlUtil.getLogin(request);
		if (!login.isLogined()) {
			if (!login.login(username, password)) {
				throw new RuntimeException("用户名或密码错误！");
			}
			//登陆成功之后写入cookie
			ControlUtil.addCookieForLogin(login, response);
		}
		return "pages/match";
	}

	@Override
	protected String hanldleReq(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String action = req.getParameter("action");
		if (StringUtils.equals(action, "login")) {
			return login(req, res);
		}
		if(StringUtils.equals(action, "logout")){
			logout(req,res);
		}
		return null;
	}

	private void logout(HttpServletRequest req, HttpServletResponse res) {
		ControlUtil.getLogin(req).logout();
	}
}
