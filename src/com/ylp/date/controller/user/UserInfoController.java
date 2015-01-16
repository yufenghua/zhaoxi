package com.ylp.date.controller.user;

import java.io.IOException;
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
import org.omg.CORBA.Request;
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
import com.ylp.date.security.UserOper;
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
		if (!StringUtils.isNotEmpty(userId)) {
			userId = ControlUtil.getLogin(req).getUser().getId();
		}
		if (StringUtils.isNotEmpty(userId) && !isMultipart) {
			req.setAttribute("userid", userId);
			req.setAttribute("user",
					Server.getInstance().userMgr().getObj(userId));
			String fromLogin = req.getParameter("fromLogin");
			req.setAttribute("fromLogin",
					StringUtils.isNotEmpty(fromLogin) ? true : false);
			List<IBaseObj> list = Server.getInstance().getUserTagSugMgr()
					.list();
			handleTag(list, req, userId);
			req.setAttribute("sugList", list);
			if (!StringUtils.isNotEmpty(action)
					|| StringUtils.equals(action, "setting")) {
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
		if (StringUtils.equals(action, "img")) {
			handleImg(req, res);
			return null;
		}
		if (StringUtils.equals(action, "cardImg")) {
			handleCardImg(req, res);
		}
		return null;
	}

	/**
	 * 证件照
	 * 
	 * @param req
	 * @param res
	 * @throws IOException
	 */
	private void handleCardImg(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		String userId = req.getParameter("userid");
		Login login = ControlUtil.getLogin(req);
		if (!StringUtils.equals(login.getUser().getId(), userId)) {
			// 如果不是本人，需要确认权限
			login.getPmckecker().check(UserOper.OP_AUDIT_USER, true);
		}
		User user = (User) Server.getInstance().userMgr().getObj(userId);
		byte[] img = user.getCardImgBytes();
		if (img == null || img.length == 0) {
			return;
		}
		res.setContentType("image/jpg");
		res.getOutputStream().write(img, 0, img.length);
	}

	private void handleImg(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String userId = req.getParameter("userid");
		User user = (User) Server.getInstance().userMgr().getObj(userId);
		byte[] img = user.getImg();
		if (img == null || img.length == 0) {
			img = user.getCardImgBytes();
		}
		if (img == null || img.length == 0) {
			InputStream resourceAsStream = this.getClass().getResourceAsStream(
					"/default.jpg");
			try {
				img = new byte[resourceAsStream.available()];
				resourceAsStream.read(img);
			} finally {
				resourceAsStream.close();
			}
		}
		res.setContentType("image/jpg");
		res.getOutputStream().write(img, 0, img.length);
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
				req.setAttribute(id, "");
				continue;
			}
			req.setAttribute(id, tag.getCaption());
		}
	}

	private String update(HttpServletReqEx ex, HttpServletResponse res)
			throws Exception {
		String userid = ex.getParameter("userid");
		String from = ex.getParameter("from");
		if (!StringUtils.isNotEmpty(userid)) {
			throw new RuntimeException("用户账号不能为空！");
		}
		IUserMgr userMgr = Server.getInstance().userMgr();
		User user = (User) userMgr.getObj(userid);
		String genderStr = ex.getParameter("gender");
		int gender = IUser.MALE;
		//处理年龄和性别的默认值
		try {
			gender = Integer.valueOf(genderStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		user.setGender(gender);
		String ageRange = ex.getParameter("age");
		int agerange = IUser.AGE_20_23;
		try {
			agerange = Integer.valueOf(ageRange);
		} catch (Exception e) {
			e.printStackTrace();
		}
		user.setAgeRange(agerange);
		String userCaption=ex.getParameter("usercaption");
		user.setCaption(userCaption);
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
				if (stm.available() > 0) {

					byte[] img = new byte[stm.available()];
					int read = stm.read(img);
					user.setImg(img);
				}

			} finally {
				stm.close();
			}
			// 允许没有图片的情况下 更新用户数据
			userMgr.update(userid, user);
			if (!StringUtils.isNotEmpty(from)) {
				res.sendRedirect(ex.getContextPath() + "/match.do");
			} else {
				ex.getSession().setAttribute("savemsg", "保存成功！");
				res.sendRedirect(ex.getContextPath()
						+ "/user/userinfo.do?action=setting");
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
