package org.proust.core.service;

import java.util.Set;

import org.proust.core.model.BizCawin;
import org.proust.core.model.BizEvent;

public interface BizEventService {
	public <T> BizEvent<T> getEvent(String id);

	public <T> void checkOrWaitForEvent(BizEvent<T> evt, BizCawin<T> cawin);
	
	public <T> void eventOccured(BizEvent<T> evt);

	public <T> Set<BizEvent<T>> getEventsFromDescription(BizEvent<T> evt);
}
