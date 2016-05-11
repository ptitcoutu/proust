package org.proust.core.model;

public class BizEvent<T> {
	public String id;
	private T description;
	private BizTopic<T> topic;
	private T payload;
	private EventStatus status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public T getDescription() {
		return description;
	}

	public void setDescription(T description) {
		this.description = description;
	}

	public BizTopic<T> getTopic() {
		return topic;
	}

	public void setTopic(BizTopic<T> topic) {
		this.topic = topic;
	}

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

	public EventStatus getStatus() {
		return status;
	}

	public void setStatus(EventStatus status) {
		this.status = status;
	}
}
