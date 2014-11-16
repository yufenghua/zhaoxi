package com.ylp.date.controller.user;

import java.io.IOException;
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
import com.ylp.date.mgr.relation.IRelation;
import com.ylp.date.mgr.user.IUser;
import com.ylp.date.mgr.user.IUserMgr;
import com.ylp.date.server.Server;
import com.ylp.date.util.CollectionTool;
import com.ylp.date.util.StringTools;

@Controller
@RequestMapping("/user/userline")
public class UserLineController extends BaseController {

	@Override
	protected String hanldleReq(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String action = req.getParameter("action");
		if (!StringUtils.isNotEmpty(action)) {
			return "pages/my-link";
		}
		if (StringUtils.equals(action, "list")) {
			res.setContentType("application/json; charset=utf-8");
			getInfo(req, res);
			return null;
		}
		return null;
	}

	private void getInfo(HttpServletRequest req, HttpServletResponse res)
			throws JSONException, IOException {
		JSONObject obj = new JSONObject();
		// 1.丘比特值
		IUser user = ControlUtil.getLogin(req).getUser();
		obj.put("cupidValue", user.getCupidvalue());
		List<IRelation> list = Server.getInstance().getRelationBuilderMgr()
				.getRelation(user.getId(), IRelation.TYPE_LINE);
		JSONArray arr = new JSONArray();
		if (!CollectionTool.checkNull(list)) {
			for (IRelation iRelation : list) {
				if (iRelation.getRecognition() != IRelation.RECOG_LINE) {
					continue;
				}
				JSONObject json = new JSONObject();
				Date okTime = iRelation.getOkTime();
				json.put("time",
						okTime == null ? "" : StringTools.formateDate(okTime));
				String one = iRelation.getOne();
				IUserMgr userMgr = Server.getInstance().userMgr();
				if (StringUtils.isNotEmpty(one)) {
					IUser oneUser = userMgr.getObj(one);
					obj.put("one", oneUser.getCaption());
					obj.put("oneImg", "userinfo.do?action=img&userid=" + one);
				}
				String other = iRelation.getOtherOne();
				if (StringUtils.isNotEmpty(other)) {
					obj.put("other", userMgr.getObj(other).getCaption());
					obj.put("otherImg", "userinfo.do?action=img&userid=" + other);
				}
				arr.put(json);
			}
		}
		obj.put("lines", arr);
		res.getWriter().print(obj.toString());

	}

}
