package com.ylp.date.controller.user;

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
import com.ylp.date.mgr.relation.impl.RelationMgr;
import com.ylp.date.mgr.tag.ITag;
import com.ylp.date.mgr.tag.impl.UserTagSugMgr;
import com.ylp.date.mgr.user.IUser;
import com.ylp.date.server.Server;
import com.ylp.date.util.CollectionTool;

/**
 * 
 * @author Qiaolin Pan
 * 
 */
@Controller
@RequestMapping("user/partner")
public class UserPartnerController extends BaseController {
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");

	@Override
	protected String hanldleReq(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String action = req.getParameter("action");
		if (StringUtils.isEmpty(action)) {
			return "pages/my-partner";
		}
		if (StringUtils.equals(action, "list")) {
			res.setContentType("application/json; charset=utf-8");
			listPartners(req, res);
			return null;
		}
		return null;
	}

	private void listPartners(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		Login login = ControlUtil.getLogin(req);
		String userId = login.getUser().getId();
		RelationMgr relationMgr = Server.getInstance().getRelationMgr();
		List<IRelation> list = relationMgr.listRelation(userId,
				IRelation.TYPE_PLAN, -1);
		if (CollectionTool.checkNull(list)) {
			return;
		}
		JSONObject jso = new JSONObject();
		JSONArray arr = new JSONArray();
		for (IRelation iRelation : list) {
			arr.put(handleWithItem(userId, iRelation, req));
		}
		jso.put("matchs", arr);
		relationMgr.recognize(IRelation.TYPE_LINE, userId);
		res.getWriter().print(jso.toString());
	}

	private JSONObject handleWithItem(String userId, IRelation iRelation,
			HttpServletRequest req) throws JSONException {
		JSONObject obj = new JSONObject();
		Date okTime = iRelation.getOkTime();
		if (okTime != null) {
			obj.put("time", format.format(okTime));
		}
		String other = iRelation.getOther(userId);
		obj.put("img", ControlUtil.getImgUrl(req, other));

		// 用户信息
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
		// 未读消息数
		obj.put("msgcount", CollectionTool.checkNull(listUnRead) ? 0
				: listUnRead.size());
		return obj;
	}
}
