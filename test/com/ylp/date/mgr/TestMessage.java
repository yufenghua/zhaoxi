package com.ylp.date.mgr;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.ylp.date.TestBase;
import com.ylp.date.app.TestContextInitor;
import com.ylp.date.mgr.msg.IMessage;
import com.ylp.date.mgr.msg.impl.Message;
import com.ylp.date.server.Server;

public class TestMessage extends TestBase {
	@Test
	public void testMessage() throws Exception {
		TestContextInitor.init();
		Message msg = new Message();
		msg.setSender("a27");
		msg.setReceiver("a14");
		msg.setCaption("hello 你好");
		Server.getInstance().getMsgMgr().add(msg);
		Thread.sleep(1000);
		msg = new Message();
		msg.setSender("a27");
		msg.setReceiver("a14");
		msg.setCaption("hello 你好,这是后面插入的");
		Server.getInstance().getMsgMgr().add(msg);
	}

	@Test
	public void testGetter() throws JSONException {
		TestContextInitor.init();
		List<IMessage> listUnRead = Server.getInstance().getMsgMgr()
				.listUnRead("a1", "a11");
		JSONArray arr = new JSONArray();
		for (IMessage iMessage : listUnRead) {
			JSONObject obj = new JSONObject();
			obj.put("sender", iMessage.getSender());
			obj.put("receiver", iMessage.getReceiver());
			obj.put("caption", iMessage.getCaption());
			obj.put("date", iMessage.getDate());
			arr.put(obj);
		}
		System.out.println(arr.toString());
		assertTrue(listUnRead.size() > 0);
	}

	@Test
	public void testOrder() {
		TestContextInitor.init();
		List<IMessage> list = Server.getInstance().getMsgMgr()
				.listUnRead("a1", "a11");
		for (IMessage iMessage : list) {
			System.out.println(iMessage.getCaption());
		}
	}
}
