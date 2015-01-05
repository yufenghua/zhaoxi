package com.ylp.date.controller.match;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ylp.date.controller.BaseController;
import com.ylp.date.controller.ControlUtil;
import com.ylp.date.login.Login;
import com.ylp.date.mgr.tag.ITag;
import com.ylp.date.mgr.tag.impl.UserTagSugMgr;
import com.ylp.date.mgr.user.IUser;
import com.ylp.date.server.Server;
import com.ylp.date.service.LineService;
import com.ylp.date.service.LineUsersObj;

@Controller
@RequestMapping("/match")
public class MatchController extends BaseController {
private static final Logger logger=LoggerFactory.getLogger(MatchController.class);
	@Override
	protected String hanldleReq(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String action = req.getParameter("action");
		if (!StringUtils.isNotEmpty(action)) {
			return "pages/match";
		}
		if (StringUtils.equals(action, "getMatchInfo")) {
			long start=System.currentTimeMillis();
			res.setContentType("application/json; charset=utf-8");
			JSONObject obj = new JSONObject();
			LineService service = Server.getInstance().getLineService();
			Login login = ControlUtil.getLogin(req);
			logger.debug("获取用户耗费时间！"+(System.currentTimeMillis()-start));
			LineUsersObj lineUser = service.getLineUser(login);
			handleLineUser(lineUser, obj, login, req);
			logger.debug("处理用户耗费时间！"+(System.currentTimeMillis()-start));
			res.getWriter().print(obj.toString());
			long end=System.currentTimeMillis();
			logger.debug("耗费时间"+(end-start));
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
		String key = lineUser.getKey();
		obj.put("id", key);
		IUser user = login.getUser();
		JSONObject userInfo = new JSONObject();
		String id = user.getId();
		obj.put("canMatch", Server.getInstance().getLineService().canBuild(key, id));
		userInfo.put("userid", id);
		obj.put("user", userInfo);
		List<String> same;
		List<String> opsite;
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

	private JSONArray handleWithList(List<String> opsite, HttpServletRequest req)
			throws Exception {
		JSONArray arr = new JSONArray();
		if (opsite != null && !opsite.isEmpty()) {
			for (String iUser : opsite) {
				arr.put(handleWithSingleUser(Server.getInstance().userMgr().getObj(iUser), req));
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
		obj.put("age", iUser.getAgeRange());
		List<ITag> tags = Server.getInstance().getUserTagMgr()
				.getTagsByUser(id);
		JSONArray arr = new JSONArray();
		for (ITag iTag : tags) {
			JSONObject json=new JSONObject();
			UserTagSugMgr userTagSugMgr = Server.getInstance().getUserTagSugMgr();
			json.put("tagsug", userTagSugMgr.getObj(iTag.getTagSug()).getCaption());
			json.put("tagInfo", iTag.getCaption());
			arr.put(json);
		}
		obj.put("tags", arr);
		return obj;
	}
}
