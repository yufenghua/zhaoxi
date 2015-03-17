package com.ylp.date.controller.user;

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
import com.ylp.date.mgr.plan.IUserPlan;
import com.ylp.date.mgr.plan.impl.UserPlan;
import com.ylp.date.mgr.relation.IRelation;
import com.ylp.date.mgr.relation.impl.RelationMgr;
import com.ylp.date.mgr.user.IUser;
import com.ylp.date.server.Server;
import com.ylp.date.util.CollectionTool;

@Controller
@RequestMapping("user/plan")
public class UserPlanController extends BaseController {

	@Override
	protected String hanldleReq(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String action = req.getParameter("action");
		if (StringUtils.isEmpty(action)) {
			return "pages/plan";
		}
		if (StringUtils.equals(action, "add")) {
			add(req, res);
			return null;
		}
		if (StringUtils.equals(action, "listnew")) {
			listNew(req, res);
			return null;
		}
		if (StringUtils.equals(action, "listold")) {
			listOld(req, res);
			return null;
		}
		if (StringUtils.equals(action, "agree")) {
			agree(req, res);
			return null;
		}
		return null;
	}

	private void agree(HttpServletRequest req, HttpServletResponse res) {
		String planId = req.getParameter("plan");
		if (StringUtils.isEmpty(planId)) {
			throw new RuntimeException("没有指定计划");
		}
		IUserPlan plan = (IUserPlan) Server.getInstance().getPlanMgr()
				.getObj(planId);
		String userId = ControlUtil.getLogin(req).getUserId();
		try {
			RelationMgr relationMgr = Server.getInstance().getRelationMgr();
			String planUserId = plan.getUserId();
			List<IRelation> list = relationMgr.getRelationBetween(
					IRelation.TYPE_PLAN, userId, planUserId, planId);
			if (!CollectionTool.checkNull(list)) {
				return;
			}
			relationMgr.buildRelation(IRelation.TYPE_PLAN, userId, planUserId,
					userId, planId);
		} catch (Exception e) {
			Server.getInstance().handleException(e);
		}
	}

	private void listOld(HttpServletRequest req, HttpServletResponse res) {
		String oldId = req.getParameter("id");
		if (StringUtils.isEmpty(oldId)) {
			return;
		}
		int size = Integer.valueOf(req.getParameter("size"));
		List<IUserPlan> plans = Server.getInstance().getPlanMgr()
				.listOld(oldId, size);
		listToJsonArray(plans, res);
	}

	private void listToJsonArray(List<IUserPlan> plans, HttpServletResponse res) {
		res.setContentType("application/json; charset=utf-8");
		try {
			JSONArray arr = new JSONArray();
			for (IUserPlan iUserPlan : plans) {
				arr.put(planToJson(iUserPlan));
			}
			res.getWriter().print(arr.toString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private JSONObject planToJson(IUserPlan iUserPlan) throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put("id", iUserPlan.getId());
		obj.put("content", iUserPlan.getContent());
		obj.put("type", iUserPlan.getType());
		String userId = iUserPlan.getUserId();
		obj.put("userId", userId);
		IUser obj2 = Server.getInstance().userMgr().getObj(userId);
		obj.put("usercaption", obj2 == null ? userId : obj2.getCaption());
		return obj;
	}

	private void listNew(HttpServletRequest req, HttpServletResponse res) {
		String oldId = req.getParameter("id");
		int size = Integer.valueOf(req.getParameter("size"));
		List<IUserPlan> plans = Server.getInstance().getPlanMgr()
				.listNew(oldId, size);
		listToJsonArray(plans, res);
	}

	private void add(HttpServletRequest req, HttpServletResponse res) {
		String content = req.getParameter("content");
		if (StringUtils.isEmpty(content)) {
			return;
		}
		UserPlan plan = new UserPlan();
		plan.setCaption(content);
		plan.setType(IUserPlan.TYPE_NEWYEAR);
		plan.setUserId(ControlUtil.getLogin(req).getUser().getId());
		Server.getInstance().getPlanMgr().add(plan);
	}
}
