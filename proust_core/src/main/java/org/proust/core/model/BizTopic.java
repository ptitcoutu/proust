package org.proust.core.model;

import java.util.List;

import org.proust.core.extractor.Extractor;

public class BizTopic<T> {
	private String name;
	private List<String> dataPaths;
	private Extractor<T> extractor;
	
	public BizTopic() {
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getDataPaths() {
		return dataPaths;
	}

	public void setDataPaths(List<String> dataPaths) {
		this.dataPaths = dataPaths;
	}

	public Extractor<T> getExtractor() {
		return extractor;
	}

	public void setExtractor(Extractor<T> extractor) {
		this.extractor = extractor;
	}
}
