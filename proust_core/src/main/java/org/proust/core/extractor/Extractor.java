package org.proust.core.extractor;

import org.proust.core.model.BizTopic;

public interface Extractor<T> {
	public T getDescription(BizTopic<T> topic, T payload);
	public String getDescriptionDigest(T description);
}
