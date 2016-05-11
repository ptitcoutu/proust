package org.proust.core.service.impl.im;

import java.util.Set;

import org.proust.core.model.BizCawin;
import org.proust.core.model.BizEvent;
import org.proust.core.service.BizCawinService;

public class IMBizCawinService implements BizCawinService {
	public IMBizCawinService() {
	}

	@Override
	public <T> BizCawin<T> getCawin(String id) {
		return null;
	}

	@Override
	public <T> void addCawin(BizCawin<T> cawin) {
	}

	@Override
	public <T> Set<BizCawin<T>> getCawinsRelatedTo(BizEvent<T> evt) {
		return null;
	}
}
