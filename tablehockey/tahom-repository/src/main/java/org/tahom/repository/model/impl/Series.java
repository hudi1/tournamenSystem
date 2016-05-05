package org.tahom.repository.model.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Series implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String SERIES_DELIMETER = ",";
	
	private List<String> series = new ArrayList<>();

	public Series(String value) {
		if (value != null) {
			for (int i = 0; i < value.split(SERIES_DELIMETER).length; i++) {
				series.add(value.split(SERIES_DELIMETER)[i]);
			}
		}
	}

	public List<String> getSeries() {
		return series;
	}

	public void setSeries(List<String> series) {
		this.series = series;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (String serie : series) {
			if (!builder.toString().isEmpty()) {
				builder.append(SERIES_DELIMETER);
			}
			builder.append(serie);
		}

		return builder.toString();
	}

}
