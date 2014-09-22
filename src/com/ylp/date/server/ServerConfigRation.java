package com.ylp.date.server;

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

	public int getLineLength() {
		return 5;
	}

}
