package com.ylp.date.controller.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ylp.date.controller.BaseController;
import com.ylp.date.controller.ControlUtil;
import com.ylp.date.mgr.msg.IMessage;
import com.ylp.date.mgr.msg.impl.Message;
import com.ylp.date.server.Server;
import com.ylp.date.util.CollectionTool;

/**
 * 
 * @author Qiaolin Pan
 * 
 */
@Controller
@RequestMapping("/user/msg")
public class UserMessageController extends BaseController {

	@Override
	protected String hanldleReq(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		res.setContentType("application/json; charset=utf-8");
		String action = req.getParameter("action");
		if (StringUtils.equals(action, "listnew")) {
			listNewMsg(req, res);
			return null;
		}
		if (StringUtils.equals(action, "add")) {
			addNewMsg(req, res);
			return null;
		}
		return null;
	}

	private void addNewMsg(HttpServletRequest req, HttpServletResponse res) {
		String receiver = req.getParameter("receiver");
		if (!StringUtils.isNotEmpty(receiver)) {
			throw new RuntimeException("接受者不能为空！");
		}
		Message msg = new Message();
		msg.setCaption(req.getParameter("content"));
		msg.setReceiver(receiver);
		msg.setSender(ControlUtil.getLogin(req).getUser().getId());
		Server.getInstance().getMsgMgr().add(msg);
	}

	private void listNewMsg(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String sender = req.getParameter("sender");
		if (!StringUtils.isNotEmpty(sender)) {
			throw new RuntimeException("发送者不能为空");
		}
		String loginId = ControlUtil.getLogin(req).getUser().getId();
		List<IMessage> msgs = Server.getInstance().getMsgMgr()
				.listUnRead(sender, loginId);
		if (CollectionTool.checkNull(msgs)) {
			return;
		}
		JSONArray arr = new JSONArray(msgs);
		res.getWriter().print(arr.toString());
	}

}
