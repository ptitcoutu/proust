package org.proust.core.service.impl.im;

import org.proust.core.service.BizCawinService;
import org.proust.core.service.BizEventService;
import org.proust.core.service.BizService;
import org.proust.core.service.BizTopicService;

public class InMemoryBizService implements BizService {
	private IMBizEventService evtService = new IMBizEventService(this);
	private IMBizTopicService topicService = new IMBizTopicService();
	private IMBizCawinService cawinService = new IMBizCawinService();
	
	public InMemoryBizService() {
	}

	@Override
	public BizEventService getEventService() {
		return evtService;
	}

	@Override
	public BizTopicService getBizTopicService() {
		return topicService;
	}
	
	@Override
	public BizCawinService getBizCawinService() {
		return cawinService;
	}
}
