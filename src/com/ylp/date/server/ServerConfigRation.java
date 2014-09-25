package com.ylp.date.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 服务器配置信息
 * 
 * @author Qiaolin Pan
 * 
 */
@Component(SpringNames.ServerConfigRation)
@Lazy(false)
public class ServerConfigRation {
	private static final Logger logger=LoggerFactory.getLogger(ServerConfigRation.class);

	public int getLineLength() {
		return 5;
	}
	public void init(){
		logger.info("ServerConfigRation init");
	}

}
