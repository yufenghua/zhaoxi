package com.ylp.date.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ylp.date.controller.BaseController;
import com.ylp.date.controller.ControlUtil;
import com.ylp.date.server.Server;

/**
 * 
 * @author Qiaolin Pan
 * 
 */
@Controller
@RequestMapping("/user/helpinfo")
public class HelpInfoController extends BaseController {

	@Override
	protected String hanldleReq(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String action = req.getParameter("action");
		res.setContentType("application/json; charset=utf-8");
		if (StringUtils.equals(action, "check")) {
			checkRead(req, res);
			return null;
		}
		if (StringUtils.equals(action, "read")) {
			read(req, res);
			return null;
		}
		return null;
	}

	private void read(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String loginId = ControlUtil.getLogin(req).getUser().getId();
		String version = req.getParameter("version");
		if (!StringUtils.isNotEmpty(version)) {
			version = Server.getInstance().getConfigRation()
					.getHelpInfoVersion();
		}
		Server.getInstance().getHelpInfoMgr().read(loginId, version);
	}

	private void checkRead(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String loginId = ControlUtil.getLogin(req).getUser().getId();
		String version = req.getParameter("version");
		if (!StringUtils.isNotEmpty(version)) {
			version = Server.getInstance().getConfigRation()
					.getHelpInfoVersion();
		}
		JSONObject obj = new JSONObject();
		obj.put("read",
				Server.getInstance().getHelpInfoMgr()
						.checkRead(loginId, version));
		res.getWriter().print(obj.toString());

	}

}
