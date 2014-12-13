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
			handleLineUser(lineUser, obj, login, req);
			res.getWriter().print(obj.toString());
			return null;
		}
		if (StringUtils.equals(action, "MatchUser")) {
			res.setContentType("application/json; charset=utf-8");
			matchUsers(req, res);
			return null;
		}
		if (StringUtils.equals(action, "flower")) {
			sendFlower(req, res);
			return null;
		}
		return null;
	}

	/**
	 * 送花
	 * 
	 * @param req
	 * @param res
	 */
	private void sendFlower(HttpServletRequest req, HttpServletResponse res) {
		String userId = req.getParameter("target");
		if (!StringUtils.isNotEmpty(userId)) {
			throw new RuntimeException("目标用户不能为空!");
		}
		Login login = ControlUtil.getLogin(req);
		IUser sender = login.getUser();
		IUser receiver = Server.getInstance().userMgr().getObj(userId);
		if (receiver == null) {
			throw new RuntimeException("用户" + userId + "不存在!");
		}
		Server.getInstance().getRelationMgr()
				.sendFlower(sender.getId(), userId);
	}

	private void matchUsers(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String userid1 = req.getParameter("user");
		String other = req.getParameter("other");
		Login login = ControlUtil.getLogin(req);
		String id = login.getUser().getId();
		JSONObject obj = new JSONObject();
		try {
			Server.getInstance().getRelationMgr().buildLine(id, userid1, other);
			// 建立关系之后，相关组将不会再当前用户获得展示
			Server.getInstance().getLineService().markBuild(userid1, other, id);
			obj.put("suc", true);
			res.getWriter().print(obj.toString());
		} catch (Exception e) {
			obj.put("suc", false);
			obj.put("msg", e.getMessage());
			res.getWriter().print(obj.toString());
		}
	}

	private void handleLineUser(LineUsersObj lineUser, JSONObject obj,
			Login login, HttpServletRequest req) throws Exception {
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
		obj.put("opposite", handleWithList(opsite, req));
		obj.put("same", handleWithList(same, req));
	}

	private JSONArray handleWithList(List<IUser> opsite, HttpServletRequest req)
			throws Exception {
		JSONArray arr = new JSONArray();
		if (opsite != null && !opsite.isEmpty()) {
			for (IUser iUser : opsite) {
				arr.put(handleWithSingleUser(iUser, req));
			}
		}
		return arr;
	}

	private JSONObject handleWithSingleUser(IUser iUser, HttpServletRequest req)
			throws Exception {
		JSONObject obj = new JSONObject();
		String id = iUser.getId();
		obj.put("id", id);
		obj.put("name", iUser.getCaption());
		obj.put("img", ControlUtil.getImgUrl(req, id));
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
