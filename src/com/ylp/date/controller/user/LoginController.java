package com.ylp.date.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.ParameterMethodNameResolver;

import com.ylp.date.controller.ControlUtil;
import com.ylp.date.login.Login;
@Controller
@RequestMapping("/user/login")
public class LoginController extends ParameterMethodNameResolver {
	public LoginController() {
		super.setDefaultMethodName("login");
	}

	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response) {
		String username = request.getParameter("username");
		String password=request.getParameter("password");
		Login login=ControlUtil.getLogin(request);
		if(!login.isLogined()){
			if(!login.login(username, password)){
				throw new RuntimeException("用户名或密码错误！");
			}
		}
		return new ModelAndView("pages/match");
	}
}
