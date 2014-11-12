package com.ylp.date.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ylp.date.controller.BaseController;
/**
 * 用户连接信息
 * @author Qiaolin Pan
 *
 */
@Controller
@RequestMapping("/user/usermatch")
public class UserMatchController extends BaseController {

	@Override
	protected String hanldleReq(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String action=req.getParameter("action");
		if(!StringUtils.isNotEmpty(action)){
			return "pages/my-match";
		}
		return null;
	}

}
