package com.ylp.date.server;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.ylp.date.app.ApplicationListener;
import com.ylp.date.mgr.user.IUserMgr;
import com.ylp.date.mgr.user.impl.UserMgr;

/**
 * Application root object。 singleton
 * 
 * @author Qiaolin Pan
 * 
 */
@Component(SpringNames.Server)
@Lazy(false)
public class Server {
	private static final Logger logger = LoggerFactory.getLogger(Server.class);
	private static Server ins;

	public Server() {

	}

	public void init() {
		ins = this;
		logger.debug("Server init");
		AnnotationConfiguration conf = (new AnnotationConfiguration())
				.configure();
		fct = conf.buildSessionFactory();
		// new SchemaExport(conf).create(true, false);
	}

	private SessionFactory fct;

	/**
	 * get hibernate sessionfactory
	 * 
	 * @return
	 */
	public SessionFactory getSessionFactory() {
		return fct;
	}

	/**
	 * get
	 * 
	 * @return
	 */
	public Session getCurentSession() {
		org.hibernate.classic.Session currentSession = fct.getCurrentSession();
		currentSession.beginTransaction();
		return currentSession;
	}

	/**
	 * 
	 * @return
	 */
	public Session openSession() {
		return fct.openSession();
	}

	/**
	 * 
	 * @return
	 */
	public static Server getInstance() {
		return ins;
	}

	/**
	 * get usermgr from spring context
	 * @return IUserMgr object .Actually return an UserMgr instance
	 */
	public IUserMgr userMgr() {
		return ApplicationListener.getWebApplicationContext().getBean(
				SpringNames.UserMgr, UserMgr.class);
	}

}
