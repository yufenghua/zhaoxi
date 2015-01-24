package com.ylp.date.controller.user;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.ylp.date.mgr.msg.IMessage;
import com.ylp.date.mgr.relation.IRelation;
import com.ylp.date.mgr.relation.IRelationBuilder;
import com.ylp.date.mgr.relation.impl.RelationMgr;
import com.ylp.date.mgr.relation.impl.UserRelation;
import com.ylp.date.mgr.tag.ITag;
import com.ylp.date.mgr.tag.impl.UserTagSugMgr;
import com.ylp.date.mgr.user.IUser;
import com.ylp.date.server.Server;
import com.ylp.date.util.CollectionTool;

@Controller
@RequestMapping("/user/userflower")
public class UserFlowerController extends BaseController {
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");

	@Override
	protected String hanldleReq(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String action = req.getParameter("action");
		if (!StringUtils.isNotEmpty(action)) {
			return "pages/my-flower";
		}
		if (StringUtils.equals(action, "list")) {
			res.setContentType("application/json; charset=utf-8");
			listMatch(req, res);
			return null;
		}
		if (StringUtils.equals(action, "recognize")) {
			recognizeFlower(req, res);
			return null;
		}
		return null;
	}

	private void listMatch(HttpServletRequest req, HttpServletResponse res)
			throws JSONException, IOException {
		Login login = ControlUtil.getLogin(req);
		String userId = login.getUser().getId();
		RelationMgr relationMgr = Server.getInstance().getRelationMgr();
		List<IRelation> list = relationMgr.listFlower(userId);
		if (CollectionTool.checkNull(list)) {
			return;
		}
		JSONObject jso = new JSONObject();
		JSONArray arr = new JSONArray();
		for (IRelation iRelation : list) {
			arr.put(handleWithItem(userId, iRelation, req));
		}
		jso.put("matchs", arr);
		relationMgr.recognize(IRelation.TYPE_FLOWER, userId);
		res.getWriter().print(jso.toString());
	}

	private JSONObject handleWithItem(String userId, IRelation iRelation,
			HttpServletRequest req) throws JSONException {
		JSONObject obj = new JSONObject();
		List<IRelationBuilder> builders = Server.getInstance()
				.getRelationBuilderMgr().getAllBuilders(iRelation.getId());
		Date okTime = CollectionTool.checkNull(builders) ? null : builders.get(
				0).getCreateTime();
		obj.put("time", okTime == null ? "未知时间" : format.format(okTime));
		// 用户信息
		String other = iRelation.getOther(userId);
		obj.put("img", ControlUtil.getImgUrl(req, other));
		IUser iUser = Server.getInstance().userMgr().getObj(other);
		obj.put("userCaption", iUser.getCaption());
		obj.put("age", iUser.getAgeRange());
		List<ITag> tags = Server.getInstance().getUserTagMgr()
				.getTagsByUser(other);
		JSONArray arr = new JSONArray();
		for (ITag iTag : tags) {
			JSONObject json = new JSONObject();
			UserTagSugMgr userTagSugMgr = Server.getInstance()
					.getUserTagSugMgr();
			json.put("tagsug", userTagSugMgr.getObj(iTag.getTagSug())
					.getCaption());
			json.put("tagInfo", iTag.getCaption());
			arr.put(json);
		}
		obj.put("tags", arr);

		obj.put("otherid", other);
		List<IMessage> listUnRead = Server.getInstance().getMsgMgr()
				.listUnRead(other, userId);
		//未读消息数
		obj.put("msgcount", CollectionTool.checkNull(listUnRead) ? 0
				: listUnRead.size());
		obj.put("success", iRelation.getRecognition() == IRelation.RECOG_FLOWER);
		List<IRelationBuilder> list = Server.getInstance()
				.getRelationBuilderMgr().getAllBuilders(iRelation.getId());
		if (!CollectionTool.checkNull(list)) {
			// 理论上来说 这个地方是不能为空的
			IRelationBuilder builder = list.get(0);
			obj.put("isSend", StringUtils.equals(userId, builder.getUserId()));
		}
		return obj;
	}

	private void recognizeFlower(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String userId = req.getParameter("user");
		if (!StringUtils.isNotEmpty(userId)) {
			throw new RuntimeException("用户id不能为空");
		}
		Login login = ControlUtil.getLogin(req);
		RelationMgr relationMgr = Server.getInstance()
				.getRelationMgr();
		UserRelation flower = (UserRelation) relationMgr
				.getFlowerBetween(userId, login.getUser().getId());
		if (flower == null) {
			throw new RuntimeException("没有收到相对应的花");
		}
		flower.setRecognition(IRelation.RECOG_FLOWER);
		flower.setOkTime(new Date());
		relationMgr.update(flower.getId(), flower);
		
	}
}
