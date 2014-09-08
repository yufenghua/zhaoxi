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

/**
 * Application root object。 singleton
 * 
 * @author Qiaolin Pan
 * 
 */
@Component(SpringNames.Server)
@Lazy(false)
public class Server {
	private static final Logger logger=LoggerFactory.getLogger(Server.class);
	private static Server ins;

	public Server() {

	}

	public void init() {
		ins = this;
		logger.debug("Server init");
		AnnotationConfiguration conf = (new AnnotationConfiguration()).configure();
		fct=conf.buildSessionFactory();
//		new SchemaExport(conf).create(true, false);
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

	public Session openSession() {
		return fct.openSession();
	}

	public static Server getInstance() {
		return ins;
	}

}
