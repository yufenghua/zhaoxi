package com.ylp.date.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
	private static final Logger logger = LoggerFactory
			.getLogger(ServerConfigRation.class);
	private Map<String, Object> props = new ConcurrentHashMap<String, Object>();

	/**
	 * 
	 * @return
	 */
	public int getLineLength() {
		return 5;
	}

	/**
	 * 
	 * @return
	 */
	public int getDefaultUserCupidValue() {
		return 5;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public Object getValue(String key) {
		return props.get(key);
	}

	public void init() {
		logger.info("ServerConfigRation init");
	}

	public String getHelpInfoVersion() {
		// TODO Auto-generated method stub
		return "1.0";
	}

}
