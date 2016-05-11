package org.proust.core.model;

public class BizCawin<T> {
	private String id;
	private String eventId;
	private boolean active;
	private BizCallback<T> callback;
	
	public BizCawin() {
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public BizCallback<T> getCallback() {
		return callback;
	}
	public void setCallback(BizCallback<T> callback) {
		this.callback = callback;
	}
}
