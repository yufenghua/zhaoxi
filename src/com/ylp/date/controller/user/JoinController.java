package com.ylp.date.controller.user;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.ParameterMethodNameResolver;

import com.ylp.date.controller.BaseController;
import com.ylp.date.controller.ControlUtil;
import com.ylp.date.login.Login;
import com.ylp.date.mgr.user.impl.User;
import com.ylp.date.server.Server;
import com.ylp.date.util.HttpServletReqEx;
import com.ylp.date.util.StringTools;

@Controller
@RequestMapping("/user/join")
public class JoinController extends BaseController {

	/**
	 * 注册方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String register(HttpServletRequest req, HttpServletResponse response)
			throws Exception {
		HttpServletReqEx request = (HttpServletReqEx) req;
		String username = request.getParameter("username");
		if (!StringUtils.isNotEmpty(username)) {
			throw new RuntimeException("用户名不能为空");
		}
		String password = request.getParameter("password");
		if (!StringUtils.isNotEmpty(password)) {
			throw new RuntimeException("密码不能为空");
		}
		String email = request.getParameter("email");
		if (!StringUtils.isNotEmpty(email)) {
			throw new RuntimeException("邮箱不能为空");
		}
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);// 检查输入请求是否为multipart表单数据。
		if (isMultipart == true) {
			List<FileItem> items = request.getItems();
			Iterator<FileItem> itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				// 检查当前项目是普通表单项目还是上传文件。
				if (item.isFormField()) {// 如果是普通表单项目，显示表单内容。
					continue;
				}
				InputStream stm = item.getInputStream();
				try {
					User user = new User();
					user.setId(username);
					user.setEmail(email);
					user.setCreateDate(new Date());
					user.setPwd(StringTools.encryptPassword(password));
					byte[] img = new byte[stm.available()];
					user.setCardImgBytes(img);
					Server.getInstance().userMgr().add(user);
					Login login = ControlUtil.getLogin(request);
					if (!login.isLogined()) {
						login.login(username, password);
					}
					String contextPath = req.getContextPath();
					if (!StringUtils.isNotEmpty(contextPath)) {
						contextPath = "/";
					}
					if (!StringUtils.endsWith(contextPath, "/")) {
						contextPath = contextPath + "/";
					}
					response.sendRedirect(contextPath
							+ "user/userinfo.do?userid=" + username);
					return null;

				} finally {
					stm.close();
				}
			}
		}
		return null;
	}

	@Override
	protected String hanldleReq(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		if (isMultipart) {
			req = new HttpServletReqEx(req);
		}
		String action = req.getParameter("action");
		if (!StringUtils.isNotEmpty(action)) {
			return "pages/join";
		}
		if (StringUtils.equals(action, "reg")) {
			return register(req, res);
		}
		if (StringUtils.equals(action, "checkexist")) {
			checkIdExist(req, res);
			return null;
		}
		return null;
	}

	private void checkIdExist(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		res.setContentType("application/json; charset=utf-8");
		String id = req.getParameter("id");
		JSONObject obj = new JSONObject();
		obj.put("exist", Server.getInstance().userMgr().getObj(id) != null);
		res.getWriter().print(obj.toString());
	}
}
