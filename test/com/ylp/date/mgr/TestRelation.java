package com.ylp.date.mgr;

import java.util.List;

import org.junit.Test;

import com.ylp.date.TestBase;
import com.ylp.date.app.TestContextInitor;
import com.ylp.date.mgr.relation.impl.RelationMgr;
import com.ylp.date.mgr.user.IUser;
import com.ylp.date.server.Server;

public class TestRelation extends TestBase {
	@Test
	public void testFlower() throws Exception {
		TestContextInitor.init();
		PageCondition page = new PageCondition();
		page.setLength(2);
		page.setStart(0);
		List<IBaseObj> users = Server.getInstance().userMgr().list(page);
		if (users.size() != 2) {
			throw new Exception("用户数量过少，无法测试");
		}
		RelationMgr relationMgr = Server.getInstance().getRelationMgr();
		String sender = users.get(0).getId();
		String receive = users.get(1).getId();
		relationMgr.sendFlower(sender, receive);
		assertNotNull(relationMgr.getFlowerBetween(sender, receive));
	}
}
