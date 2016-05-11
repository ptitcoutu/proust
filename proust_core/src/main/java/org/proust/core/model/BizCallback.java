package org.proust.core.model;

public interface BizCallback<T> {
	public void eventOccured(BizEvent<T> event);
}
