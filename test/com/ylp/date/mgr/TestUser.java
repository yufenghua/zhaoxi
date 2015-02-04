package com.ylp.date.mgr;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.ylp.date.TestBase;
import com.ylp.date.app.TestContextInitor;
import com.ylp.date.mgr.tag.impl.UserTag;
import com.ylp.date.mgr.tag.impl.UserTagMgr;
import com.ylp.date.mgr.user.IUser;
import com.ylp.date.mgr.user.IUserMgr;
import com.ylp.date.mgr.user.impl.User;
import com.ylp.date.server.Server;
import com.ylp.date.util.StringTools;

import junit.framework.TestCase;

public class TestUser extends TestBase {
	@Test
	public void testAdd() throws Exception {
		TestContextInitor.init();
		IUserMgr userMgr = Server.getInstance().userMgr();
		for (int i = 0; i < 100; i++) {
			User user = getUser();
			String id = "a" + i;
			user.setId(id);
			user.setCaption("潘巧林" + i);
			user.setGender(i % 2 == 0 ? IUser.MALE : IUser.FEMALE);
			user.setAgeRange(User.AGE_23_26);
			userMgr.add(user);
			List<IBaseObj> list = Server.getInstance().getUserTagSugMgr()
					.list();
			UserTagMgr userTagMgr = Server.getInstance().getUserTagMgr();
			for (IBaseObj iBaseObj : list) {
				UserTag tag = new UserTag();
				tag.setSugId(iBaseObj.getId());
				tag.setUserId(id);
				if (StringUtils.equals(iBaseObj.getCaption(), "偶像")) {
					tag.setTagContent("周杰伦");
					userTagMgr.add(tag);
				}
				if (StringUtils.equals("爱好", iBaseObj.getCaption())) {
					tag.setTagContent("羽毛球");
					userTagMgr.add(tag);
				}
			}
		}
		// assertNotNull(userMgr.getObj(user.getId()));
	}

	private User getUser() throws Exception {
		User user = new User();
		String string = UUID.randomUUID().toString();
		user.setId(string);
		user.setBirth(new Date());
		user.setPwd(StringTools.encryptPassword("111"));
		user.setCreateDate(new Date());
		user.setEmail("yufenghuapan@163.com");
		user.setFlower(5);
		InputStream resourceAsStream = this.getClass().getResourceAsStream(
				"/default.jpg");
		try {
			byte[] img = new byte[resourceAsStream.available()];
			resourceAsStream.read(img);
			user.setImg(img);
		} finally {
			resourceAsStream.close();
		}
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
	public void testRemove() throws Exception {
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
	public void testList() throws Exception {
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
