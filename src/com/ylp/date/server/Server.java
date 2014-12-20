package com.ylp.date.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

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
import com.ylp.date.mgr.relation.impl.RelationBldMgr;
import com.ylp.date.mgr.relation.impl.RelationMgr;
import com.ylp.date.mgr.relation.impl.UserRelation;
import com.ylp.date.mgr.tag.impl.UserTagMgr;
import com.ylp.date.mgr.tag.impl.UserTagSugMgr;
import com.ylp.date.mgr.user.IUserMgr;
import com.ylp.date.mgr.user.impl.UserMgr;
import com.ylp.date.security.impl.RolePmcheckMgr;
import com.ylp.date.service.LineService;

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
	private ExecutorService service;

	private SessionFactory fct;
	private ScheduledExecutorService scheduledService;

	public Server() {

	}

	/**
	 * 获取定时执行线程池
	 * 
	 * @return
	 */
	public ScheduledExecutorService getScheduledService() {
		return scheduledService;
	}

	public void init() {
		ins = this;
		logger.debug("Server init");
		AnnotationConfiguration conf = (new AnnotationConfiguration())
				.configure();
		fct = conf.buildSessionFactory();
		service = Executors.newCachedThreadPool();
		scheduledService = Executors.newScheduledThreadPool(5);
	}

	/**
	 * 销毁方法
	 */
	public void destroy() {
		try {
			fct.close();
		} catch (Exception e) {
			logger.error("关闭hibernate工厂出现错误", e);
		}
		try {
			service.shutdown();
		} catch (Exception e) {
			logger.error("关闭线程池出现错误", e);
		}
		ins = null;
	}

	/**
	 * 获取线程池对象
	 * 
	 * @return
	 */
	public ExecutorService getThreadPoolService() {
		return service;
	}

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
	 * 
	 * @return IUserMgr object .Actually return an UserMgr instance
	 */
	public IUserMgr userMgr() {
		return ApplicationListener.getApplicationContext().getBean(
				SpringNames.UserMgr, UserMgr.class);
	}

	/**
	 * get role pmchecker
	 * 
	 * @return
	 */
	public RolePmcheckMgr getRoleCheckerMgr() {
		return ApplicationListener.getApplicationContext().getBean(
				SpringNames.RolePmChecker, RolePmcheckMgr.class);
	}

	public RelationMgr getRelationMgr() {
		return ApplicationListener.getApplicationContext().getBean(
				SpringNames.RelationMgr, RelationMgr.class);
	}

	public UserTagMgr getUserTagMgr() {
		return ApplicationListener.getApplicationContext().getBean(
				SpringNames.TagMgr, UserTagMgr.class);
	}

	public UserTagSugMgr getUserTagSugMgr() {
		return ApplicationListener.getApplicationContext().getBean(
				SpringNames.TagSugMgr, UserTagSugMgr.class);
	}

	public RelationBldMgr getRelationBuilderMgr() {
		return ApplicationListener.getApplicationContext().getBean(
				SpringNames.RelationBuilderMgr, RelationBldMgr.class);
	}

	public ServerConfigRation getConfigRation() {
		return ApplicationListener.getApplicationContext().getBean(
				SpringNames.ServerConfigRation, ServerConfigRation.class);
	}

	public LineService getLineService() {
		return ApplicationListener.getApplicationContext().getBean(
				SpringNames.LineService, LineService.class);
	}

	/**
	 * global method to handle exception
	 * 
	 * @param e
	 */
	public void handleException(Exception e) {
		if (e instanceof RuntimeException) {
			throw (RuntimeException) e;
		}
		throw new RuntimeException(e);

	}

}
