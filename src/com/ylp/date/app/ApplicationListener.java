package com.ylp.date.app;

import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
/**
 * listener to get Spring context
 * @author Qiaolin Pan
 *
 */
public class ApplicationListener implements ServletContextListener {
	private static final Logger logger=LoggerFactory.getLogger(ApplicationListener.class);
	private static WebApplicationContext context;

	public void contextDestroyed(ServletContextEvent arg0) {
		context = WebApplicationContextUtils.getWebApplicationContext(arg0
				.getServletContext());
		logger.debug("get the Spring context"+context);

	}

	public void contextInitialized(ServletContextEvent arg0) {
		context = null;
	}

	public static final WebApplicationContext getWebApplicationContext() {
		if (context == null) {
			throw new RuntimeException("服务器初始化尚未完成");
		}
		return context;
	}
}
