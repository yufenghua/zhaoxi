package com.ylp.date.controller.user;

import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ylp.date.controller.BaseController;
import com.ylp.date.controller.ControlUtil;
import com.ylp.date.login.Login;
import com.ylp.date.mgr.IBaseObj;
import com.ylp.date.mgr.tag.ITag;
import com.ylp.date.mgr.tag.impl.UserTag;
import com.ylp.date.mgr.tag.impl.UserTagMgr;
import com.ylp.date.mgr.user.IUser;
import com.ylp.date.mgr.user.IUserMgr;
import com.ylp.date.mgr.user.impl.User;
import com.ylp.date.server.Server;
import com.ylp.date.util.HttpServletReqEx;
import com.ylp.date.util.StringTools;

@Controller
@RequestMapping("/user/userinfo")
public class UserInfoController extends BaseController {
	@Override
	protected String hanldleReq(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		String action = req.getParameter("action");
		String userId = req.getParameter("userid");
		if (StringUtils.isNotEmpty(userId) && !isMultipart) {
			req.setAttribute("userid", userId);
			req.setAttribute("user",
					Server.getInstance().userMgr().getObj(userId));
			List<IBaseObj> list = Server.getInstance().getUserTagSugMgr()
					.list();
			handleTag(list, req, userId);
			req.setAttribute("sugList", list);
			if (!StringUtils.isNotEmpty(action)) {
				return "pages/detail";
			}
		}
		if (isMultipart) {
			HttpServletReqEx ex = new HttpServletReqEx(req);
			action = ex.getParameter("action");
			if (StringUtils.equals("update", action)) {
				return update(ex, res);
			}
		}
		return null;
	}

	private void handleTag(List<IBaseObj> list, HttpServletRequest req,
			String userId) {
		if (list == null || list.isEmpty()) {
			return;
		}
		List<ITag> tags = Server.getInstance().getUserTagMgr()
				.getTagsByUser(userId);
		for (IBaseObj iBaseObj : list) {
			String id = iBaseObj.getId();
			ITag tag = getTag(tags, id);
			if (tag == null) {
				continue;
			}
			req.setAttribute(id, tag.getCaption());
		}
	}

	private String update(HttpServletReqEx ex, HttpServletResponse res)
			throws Exception {
		String userid = ex.getParameter("userid");
		if (!StringUtils.isNotEmpty(userid)) {
			throw new RuntimeException("用户账号不能为空！");
		}
		IUserMgr userMgr = Server.getInstance().userMgr();
		User user = (User) userMgr.getObj(userid);
		String genderStr = ex.getParameter("gender");
		int gender = Integer.valueOf(genderStr);
		user.setGender(gender);
		String ageRange = ex.getParameter("age");
		int agerange = Integer.valueOf(ageRange);
		user.setAgeRange(agerange);
		Map<String, String> params = ex.getParams();
		UserTagMgr userTagMgr = Server.getInstance().getUserTagMgr();
		List<ITag> tags = userTagMgr.getTagsByUser(userid);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			String key = entry.getKey();
			if (StringUtils.startsWith(key, "sugid:")) {
				String sugId = key.substring(6);
				UserTag tag = getTag(tags, sugId);
				if (tag == null) {
					tag = new UserTag();
					tag.setSugId(sugId);
					tag.setTagContent(entry.getValue());
					tag.setUserId(userid);
					userTagMgr.add(tag);
				} else {
					tag.setTagContent(entry.getValue());
					userTagMgr.update(tag.getId(), tag);
				}
			}
		}
		List<FileItem> items = ex.getItems();
		Iterator<FileItem> itr = items.iterator();
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			// 检查当前项目是普通表单项目还是上传文件。
			if (item.isFormField()) {// 如果是普通表单项目，显示表单内容。
				continue;
			}
			InputStream stm = item.getInputStream();
			try {
				byte[] img = new byte[stm.available()];
				int read = stm.read(img);
				user.setImg(img);
				userMgr.update(userid, user);
				return null;

			} finally {
				stm.close();
			}
		}
		return null;
	}

	private UserTag getTag(List<ITag> tags, String sugId) {
		if (tags == null || tags.isEmpty())
			return null;
		for (ITag iTag : tags) {
			if (StringUtils.equals(iTag.getTagSug(), sugId)) {
				return (UserTag) iTag;
			}
		}
		return null;
	}
}