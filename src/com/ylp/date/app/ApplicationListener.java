package com.ylp.date.app;

import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * listener to get Spring context
 * 
 * @author Qiaolin Pan
 * 
 */
public class ApplicationListener implements ServletContextListener {
	private static final Logger logger = LoggerFactory
			.getLogger(ApplicationListener.class);
	private static ApplicationContext context;

	public void contextDestroyed(ServletContextEvent arg0) {
		//sina appengine 上面获取的xml解析工厂竟然为空  增加一次判定
		context = WebApplicationContextUtils.getWebApplicationContext(arg0
				.getServletContext());
		logger.debug("get the Spring context" + context);

	}

	public void contextInitialized(ServletContextEvent arg0) {
		context = null;
	}

	public static final ApplicationContext getApplicationContext() {
		if (context == null) {
			throw new RuntimeException("服务器初始化尚未完成");
		}
		return context;
	}

	/**
	 * for test
	 * 
	 * @return
	 */
	protected static ApplicationContext getContext() {
		return context;
	}

	/**
	 * set application context。warnning ：this method only can be userd for
	 * testing
	 * 
	 * @param context
	 */
	protected static void setContext(ApplicationContext context) {
		ApplicationListener.context = context;
	}
}
