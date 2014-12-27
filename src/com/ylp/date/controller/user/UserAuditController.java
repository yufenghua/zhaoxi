package com.ylp.date.controller.user;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ylp.date.controller.BaseController;
import com.ylp.date.controller.ControlUtil;
import com.ylp.date.login.Login;
import com.ylp.date.mgr.tag.ITag;
import com.ylp.date.mgr.tag.impl.UserTagSugMgr;
import com.ylp.date.mgr.user.IUser;
import com.ylp.date.mgr.user.IUserMgr;
import com.ylp.date.mgr.user.impl.User;
import com.ylp.date.security.UserOper;
import com.ylp.date.server.Server;
import com.ylp.date.util.CollectionTool;

@Controller
@RequestMapping("user/audit")
public class UserAuditController extends BaseController {

	@Override
	protected String hanldleReq(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		Login login = ControlUtil.getLogin(req);
		login.getPmckecker().check(UserOper.OP_AUDIT_USER, true);
		String action = req.getParameter("action");
		if (!StringUtils.isNotEmpty(action)) {
			return "pages/userlist";
		}
		if (StringUtils.equals(action, "listunaudit")) {
			listUnAudit(req, res);
			return null;
		}
		if (StringUtils.equals("audit", action)) {
			auditUser(req, res);
			return null;
		}
		return null;
	}

	private void auditUser(HttpServletRequest req, HttpServletResponse res)
			throws JSONException, IOException {
		res.setContentType("application/json; charset=utf-8");
		String userId = req.getParameter("userid");
		IUserMgr userMgr = Server.getInstance().userMgr();
		IUser user = userMgr.getObj(userId);
		JSONObject obj = new JSONObject();
		if (user == null) {
			obj.put("suc", false);
			obj.put("msg", "该用户不存在！");
		} else if (user.getStatus() == IUser.STATE_AUDITED) {
			obj.put("suc", false);
			obj.put("msg", "该用户已通过认证！");
		} else {
			User user2 = (User) user;
			user2.setStatus(IUser.STATE_AUDITED);
			user2.setModeratorId(ControlUtil.getLogin(req).getUser().getId());
			try {
				userMgr.update(userId, user2);
				obj.put("suc", true);
			} catch (Exception e) {
				obj.put("suc", false);
				obj.put("msg", "服务器出现错误:" + e.getMessage());
			}
		}
		res.getWriter().print(obj.toString());
	}

	private void listUnAudit(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		List<IUser> unauditUsers = Server.getInstance().userMgr()
				.listUnAuditUsers(null);
		if (CollectionTool.checkNull(unauditUsers)) {
			return;
		}
		res.setContentType("application/json; charset=utf-8");
		JSONObject json = new JSONObject();
		JSONArray arr = new JSONArray();
		for (IUser iUser : unauditUsers) {
			JSONObject obj = new JSONObject();
			obj.put("id", iUser.getId());
			obj.put("caption", iUser.getCaption());
			obj.put("img", ControlUtil.getCardImgUrl(req, iUser.getId()));
			obj.put("agerender", iUser.getAgeRange());
			obj.put("tags", getSugs(iUser));
			arr.put(obj);
		}
		json.put("users", arr);
		res.getWriter().print(json.toString());
	}

	private JSONArray getSugs(IUser iUser) throws Exception {
		String userId=iUser.getId();
		List<ITag> sugs = Server.getInstance().getUserTagMgr().getTagsByUser(userId);
		JSONArray arr=new JSONArray();
		if(CollectionTool.checkNull(sugs)){
			return arr;
		}
		UserTagSugMgr userTagSugMgr = Server.getInstance().getUserTagSugMgr();
		for (ITag iTag : sugs) {
			JSONObject obj=new JSONObject();
			obj.put("tag", iTag.getCaption());
			obj.put("sug", userTagSugMgr.getObj(iTag.getTagSug()).getCaption());
			arr.put(obj);
		}
		return arr;
	}

}
