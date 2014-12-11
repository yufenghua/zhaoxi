package com.ylp.date.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ylp.date.mgr.TestUser;
import com.ylp.date.mgr.user.IUser;
import com.ylp.date.mgr.user.IUserMgr;
import com.ylp.date.mgr.user.impl.User;
import com.ylp.date.server.Server;
import com.ylp.date.util.StringTools;
@Controller
@RequestMapping("/testuser")
public class TestUserController extends BaseController{

	@Override
	protected String hanldleReq(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		IUserMgr userMgr = Server.getInstance().userMgr();
		for (int i = 0; i < 100; i++) {
			User user = getUser();
			user.setId("a"+i);
			user.setCaption("潘巧林"+i);
			user.setGender(i % 2 == 0 ? IUser.MALE : IUser.FEMALE);
			try{
				userMgr.add(user);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}

	private User getUser() throws IOException {
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

}
