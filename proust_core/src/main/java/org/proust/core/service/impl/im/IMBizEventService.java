package org.proust.core.service.impl.im;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.proust.core.extractor.Extractor;
import org.proust.core.model.BizCawin;
import org.proust.core.model.BizEvent;
import org.proust.core.model.BizTopic;
import org.proust.core.model.EventStatus;
import org.proust.core.model.InvalidDescription;
import org.proust.core.model.UnspecifiedData;
import org.proust.core.service.BizCawinService;
import org.proust.core.service.BizEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IMBizEventService implements BizEventService {
	private Logger logger = LoggerFactory.getLogger(IMBizEventService.class);
	private Map<String, BizEvent> events = new HashMap<>();
	private Map<String, List<BizEvent>> eventDescriptionDigests = new HashMap<>();
	private InMemoryBizService bizService;

	IMBizEventService(InMemoryBizService bizService) {
		this.bizService = bizService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> BizEvent<T> getEvent(String id) {
		return events.get(id);
	}

	@Override
	public <T> void eventOccured(BizEvent<T> evt) {
		boolean isDebugEnabled = logger.isDebugEnabled();
		BizTopic<T> topic = evt.getTopic();
		if (topic == null) {
			throw new UnspecifiedData(
					"event " + ((evt != null && evt.getId() != null) ? evt.getId() : "") + " has no topic specified");
		}
		Extractor<T> extractor = topic.getExtractor();

		if (extractor == null) {
			throw new UnspecifiedData("topic " + ((topic != null && topic.getName() != null) ? topic.getName() : "")
					+ " has no extractor specified");
		}

		T evtPayload = evt.getPayload();
		if (evtPayload == null) {
			throw new UnspecifiedData(
					"event " + ((evt != null && evt.getId() != null) ? evt.getId() : "") + " has no payload specified");
		}
		if (isDebugEnabled) {
			logger.debug("event payload : " + ((evtPayload == null) ? "" : evtPayload.toString()));
		}
		T description = extractor.getDescription(topic, evtPayload);
		if (description == null) {
			throw new InvalidDescription(
					"event " + ((evt != null && evt.getId() != null) ? evt.getId() : "") + " has no description");
		}
		if (isDebugEnabled) {
			logger.debug("event description : " + description.toString());
		}
		String descriptionDigest = extractor.getDescriptionDigest(description);
		if (isDebugEnabled) {
			logger.debug("event description digest : " + descriptionDigest);
		}
		List<BizEvent> pendingEvents = eventDescriptionDigests.get(descriptionDigest);
		BizCawinService cawinService = bizService.getBizCawinService();
		for (BizEvent<T> pendingEvent : pendingEvents) {
			pendingEvent.setStatus(EventStatus.occured);
			pendingEvent.setPayload(evtPayload);
			Set<BizCawin<T>> relatedCawins = cawinService.getCawinsRelatedTo(pendingEvent);
			for(BizCawin<T> relatedCawin : relatedCawins) {
				relatedCawin.getCallback().eventOccured(pendingEvent);
			}
		}
	}

	@Override
	public Set<BizEvent> getEventsFromDescription(BizEvent evt) {
		return null;
	}

	@Override
	public void checkOrWaitForEvent(BizEvent evt, BizCawin cawin) {
		events.put(evt.getId(), evt);
		Object description = evt.getDescription();
		String evtDescriptionDigest = evt.getTopic().getExtractor().getDescriptionDigest(description);
		synchronized (eventDescriptionDigests) {
			if (!eventDescriptionDigests.containsKey(evtDescriptionDigest)) {
				eventDescriptionDigests.put(evtDescriptionDigest, Arrays.asList(evt));
			} else {
				List<BizEvent> existingEvents = eventDescriptionDigests.get(evtDescriptionDigest);
				for (BizEvent existingEvent : existingEvents) {
					switch (existingEvent.getStatus()) {
					case occured:
						cawin.getCallback().eventOccured(existingEvent);
						return;
					case invalidated:
						eventDescriptionDigests.put(evtDescriptionDigest, Arrays.asList(evt));
						break;
					case awaited:
						cawin.setEventId(existingEvent.getId());
					}
				}
			}
		}
	}

}
