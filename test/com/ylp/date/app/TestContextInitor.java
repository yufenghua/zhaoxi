package com.ylp.date.app;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestContextInitor {
	public static void init() {
		if (ApplicationListener.getContext() == null) {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					"applicationContext.xml");
			ApplicationListener.setContext(context);
		}
	}
}
