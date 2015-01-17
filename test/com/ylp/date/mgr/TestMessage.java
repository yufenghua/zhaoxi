package com.ylp.date.mgr;

import java.util.List;

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
		msg.setSender("a1");
		msg.setReceiver("a11");
		msg.setCaption("hello 你好");
		Server.getInstance().getMsgMgr().add(msg);
		Thread.sleep(1000);
		msg = new Message();
		msg.setSender("a1");
		msg.setReceiver("a11");
		msg.setCaption("hello 你好,这是后面插入的");
		Server.getInstance().getMsgMgr().add(msg);
	}

	@Test
	public void testGetter() {
		TestContextInitor.init();
		assertTrue(Server.getInstance().getMsgMgr().listUnRead("a1", "a11")
				.size() > 0);
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
