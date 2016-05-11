package org.proust.core.service;

import java.util.Map;

import org.proust.core.service.impl.im.InMemoryBizService;

public class BizServiceFactory {
	static public BizService getBizService(Map<String,String> conf) {
		return new InMemoryBizService();
	}
}
