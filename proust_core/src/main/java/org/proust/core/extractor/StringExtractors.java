package org.proust.core.extractor;

import java.util.List;

import org.proust.core.model.BizTopic;

public class StringExtractors {
    public static final Extractor<String> CSVExtrator = new Extractor<String>() {
		
		@Override
		public String getDescription(BizTopic<String> topic, String payload) {
			String[] payloadParts = payload.split(",");
			List<String> dataPaths = topic.getDataPaths();
			String separator = "";
			String res = "";
			for(String dataPath : dataPaths) {
				int position = new Integer(dataPath);
				res = res+separator+payloadParts[position];
				separator = ",";
			}
			return res;
		}
		@Override
		public String getDescriptionDigest(String description) {
			return description;
		}
	};
	
}
