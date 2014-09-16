package com.ylp.date.mgr;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.ylp.date.TestBase;
import com.ylp.date.app.TestContextInitor;
import com.ylp.date.mgr.user.IUser;
import com.ylp.date.mgr.user.IUserMgr;
import com.ylp.date.mgr.user.impl.User;
import com.ylp.date.server.Server;

import junit.framework.TestCase;

public class TestUser extends TestBase {
	@Test
	public void testAdd() {
		TestContextInitor.init();
		User user = getUser();

		Server.getInstance().userMgr().add(user);
		assertNotNull(Server.getInstance().userMgr().getObj(user.getId()));
	}

	private User getUser() {
		User user = new User();
		String string = UUID.randomUUID().toString();
		user.setId(string);
		user.setBirth(new Date());
		user.setCaption("潘巧林");
		user.setCreateDate(new Date());
		user.setEmail("yufenghuapan@163.com");
		user.setFlower(5);
		return user;
	}

	@Test
	public void testUpdate() throws Exception {
		TestContextInitor.init();
		IUserMgr userMgr = Server.getInstance().userMgr();
		List<IBaseObj> list = userMgr.list();
		if (list.isEmpty()) {
			userMgr.add(getUser());
			list = userMgr.list();
		}
		User user = (User) list.get(0);
		String caption = user.getCaption();
		user.setCaption(caption + "1");
		String id = user.getId();
		userMgr.update(id, user);
		IUser iuser = userMgr.getObj(id);
		assertTrue((!caption.equals(user.getCaption()))
				&& id.equals(iuser.getId()));
	}

	@Test
	public void testRemove() {
		TestContextInitor.init();
		IUserMgr userMgr = Server.getInstance().userMgr();
		List<IBaseObj> list = userMgr.list();
		if (list.isEmpty()) {
			userMgr.add(getUser());
			list = userMgr.list();
		}
		User user = (User) list.get(0);
		String id = user.getId();
		userMgr.remove(id);
		IUser iuser = userMgr.getObj(id);
		assertNull(iuser);
	}

	@Test
	public void testList() {
		TestContextInitor.init();
		IUserMgr userMgr = Server.getInstance().userMgr();
		List<IBaseObj> list = userMgr.list();
		if (list.isEmpty()) {
			userMgr.add(getUser());
			list = userMgr.list();
		}
		PageCondition condtion = new PageCondition();
		condtion.setStart(0);
		condtion.setLength(1);
		assertEquals(userMgr.list(condtion).size(), 1);
	}

	@Test
	public void testCondition() {
		// TODO
	}
}
