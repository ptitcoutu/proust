package org.proust.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.proust.core.model.BizCawin;
import org.proust.core.model.BizEvent;

public class BizEventServiceTest {

	@Test
	public void testGetEvent() {
		BizService bizService = BizServiceFactory.getBizService(null);
		final BizEvent evt = new BizEvent();
		BizCawin cawin = new BizCawin();
		BizEventService evtService = bizService.getEventService();
		evtService.checkOrWaitForEvent(evt, cawin);
		BizEvent storedEvent = evtService.getEvent(evt.getId());
		assertEquals(evt, storedEvent);
		
	}

	@Test
	public void testEventOccured() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetEventsFromDescription() {
		fail("Not yet implemented");
	}

}
