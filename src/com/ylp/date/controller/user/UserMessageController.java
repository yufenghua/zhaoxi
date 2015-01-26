package com.ylp.date.controller.user;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
	private static final Logger logger = LoggerFactory
			.getLogger(UserMessageController.class);

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

	private void addNewMsg(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		logger.info("原始字符集"+req.getCharacterEncoding());
		req.setCharacterEncoding("UTF-8");
		logger.info("字符集编码UTF-8");
		logger.info("req字符集"+req.getCharacterEncoding());
		String receiver = req.getParameter("receiver");
		if (!StringUtils.isNotEmpty(receiver)) {
			throw new RuntimeException("接受者不能为空！");
		}
		Message msg = new Message();
		String content = URLDecoder.decode(req.getParameter("content"),"UTF-8");
		logger.info("离线消息内容：" + content);
		logger.info("iso"+new String(content.getBytes("ISO-8859-1"),"UTF-8"));
		logger.info("gbk"+new String(content.getBytes("GBK"),"UTF-8"));
		logger.info("gb2312"+new String(content.getBytes("GB2312"),"UTF-8"));
		msg.setCaption(content);
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
		Server.getInstance().getMsgMgr().read(sender, loginId);
		if (CollectionTool.checkNull(msgs)) {
			return;
		}
		JSONArray arr = new JSONArray();
		for (IMessage iMessage : msgs) {
			JSONObject obj = new JSONObject();
			obj.put("sender", iMessage.getSender());
			obj.put("receiver", iMessage.getReceiver());
			obj.put("caption", iMessage.getCaption());
			obj.put("date", iMessage.getDate());
			arr.put(obj);
		}
		res.getWriter().print(arr.toString());
	}

}
