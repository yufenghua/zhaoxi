package com.ylp.date.mgr;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.ylp.date.TestBase;
import com.ylp.date.app.TestContextInitor;
import com.ylp.date.mgr.plan.IUserPlan;
import com.ylp.date.mgr.plan.impl.UserPlan;
import com.ylp.date.server.Server;

public class TestUserPlan extends TestBase {
	@Test
	public void testAdd() {
		TestContextInitor.init();
		for(int i=0;i<70;i++){
			UserPlan obj = new UserPlan();
			obj.setCaption("我要去舟山"+(i+20));
			obj.setUserId("a9");
			obj.setType(IUserPlan.TYPE_NEWYEAR);
			Server.getInstance().getPlanMgr().add(obj);
		}
	}

	@Test
	public void testget() {
		TestContextInitor.init();
		List<IUserPlan> result = Server.getInstance().getPlanMgr()
				.listNew("402836814bf8545a014bf854705e0001", 10);
		Assert.assertFalse(result.size() == 0);
	}

	@Test
	public void testgetOld() {
		TestContextInitor.init();
		List<IUserPlan> result = Server.getInstance().getPlanMgr()
				.listOld("402836814bf854e7014bf854eac60001", 10);
		Assert.assertFalse(result.size() == 0);
	}

}
