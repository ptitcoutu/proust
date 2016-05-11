package org.proust.core.service;

import java.util.Set;

import org.proust.core.model.BizCawin;
import org.proust.core.model.BizEvent;

public interface BizCawinService {
	public <T> BizCawin<T> getCawin(String id);
	public <T> void addCawin(BizCawin<T> cawin);
	public <T> Set<BizCawin<T>> getCawinsRelatedTo(BizEvent<T> evt);
}
