package com.ylp.date.controller.match;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ylp.date.controller.BaseController;
import com.ylp.date.controller.ControlUtil;
import com.ylp.date.login.Login;
import com.ylp.date.mgr.tag.ITag;
import com.ylp.date.mgr.user.IUser;
import com.ylp.date.server.Server;
import com.ylp.date.service.LineService;
import com.ylp.date.service.LineUsersObj;

@Controller
@RequestMapping("/match")
public class MatchController extends BaseController {

	@Override
	protected String hanldleReq(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String action = req.getParameter("action");
		if (!StringUtils.isNotEmpty(action)) {
			return "pages/match";
		}
		if (StringUtils.equals(action, "getMatchInfo")) {
			res.setContentType("application/json; charset=utf-8");
			JSONObject obj = new JSONObject();
			LineService service = Server.getInstance().getLineService();
			Login login = ControlUtil.getLogin(req);
			LineUsersObj lineUser = service.getLineUser(login);
			handleLineUser(lineUser, obj, login);
			res.getWriter().print(obj.toString());
			return null;
		}
		if(StringUtils.equals(action, "MatchUser")){
			res.setContentType("application/json; charset=utf-8");
			matchUsers(req,res);
			return null;
		}
		return null;
	}

	private void matchUsers(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String userid1=req.getParameter("user");
		String other=req.getParameter("other");
		Login login = ControlUtil.getLogin(req);
		Server.getInstance().getRelationMgr().buildLine(login.getUser().getId(), userid1, other);
	}

	private void handleLineUser(LineUsersObj lineUser, JSONObject obj,
			Login login) throws Exception {
		if (lineUser == null) {
			return;
		}
		obj.put("id", lineUser.getKey());
		IUser user = login.getUser();
		JSONObject userInfo = new JSONObject();
		userInfo.put("userid", user.getId());
		obj.put("user", userInfo);
		List<IUser> same, opsite;
		if (user.getGender() == IUser.MALE) {
			same = lineUser.getMale();
			opsite = lineUser.getFemale();
		} else {
			same = lineUser.getFemale();
			opsite = lineUser.getMale();
		}
		obj.put("opposite", handleWithList(opsite));
		obj.put("same", handleWithList(same));
	}

	private JSONArray handleWithList(List<IUser> opsite) throws Exception {
		JSONArray arr = new JSONArray();
		if (opsite != null && !opsite.isEmpty()) {
			for (IUser iUser : opsite) {
				arr.put(handleWithSingleUser(iUser));
			}
		}
		return arr;
	}

	private JSONObject handleWithSingleUser(IUser iUser) throws Exception {
		JSONObject obj = new JSONObject();
		String id = iUser.getId();
		obj.put("id", id);
		obj.put("name", iUser.getCaption());
		obj.put("img", "userinfo.do?action=img&userid=" + id);
		List<ITag> tags = Server.getInstance().getUserTagMgr()
				.getTagsByUser(id);
		JSONArray arr = new JSONArray();
		for (ITag iTag : tags) {
			arr.put(iTag.getCaption());
		}
		obj.put("tags", arr);
		return obj;
	}
}
