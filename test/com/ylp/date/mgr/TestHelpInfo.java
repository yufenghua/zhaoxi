package com.ylp.date.mgr;

import org.junit.Test;

import com.ylp.date.TestBase;
import com.ylp.date.app.TestContextInitor;
import com.ylp.date.mgr.helpinfo.impl.HelpInfoMgr;
import com.ylp.date.server.Server;

public class TestHelpInfo extends TestBase {
	@Test
	public void testRead() {
		TestContextInitor.init();
		assertFalse(Server
				.getInstance()
				.getHelpInfoMgr()
				.checkRead(
						"a11",
						Server.getInstance().getConfigRation()
								.getHelpInfoVersion()));

	}

	@Test
	public void testSetRead() {
		TestContextInitor.init();
		HelpInfoMgr helpInfoMgr = Server.getInstance().getHelpInfoMgr();
		helpInfoMgr.read("a11", Server.getInstance().getConfigRation()
				.getHelpInfoVersion());
		assertFalse(!helpInfoMgr.checkRead("a11", Server.getInstance()
				.getConfigRation().getHelpInfoVersion()));
	}
}
