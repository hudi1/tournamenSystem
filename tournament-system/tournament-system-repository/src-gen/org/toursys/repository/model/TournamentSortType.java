package org.toursys.repository.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum TournamentSortType implements Serializable {

	SK("SK"), CZ("CZ");
		
	private static final long serialVersionUID = 1L;
	
	private static Map<String, TournamentSortType> identifierMap = new HashMap<String, TournamentSortType>();

	static {
		for (TournamentSortType value : TournamentSortType.values()) {
			identifierMap.put(value.getValue(), value);
		}
	}

	private String value;

	private TournamentSortType(String value) {
		this.value = value;
	}

	public static TournamentSortType fromValue(String value) {
		TournamentSortType result = identifierMap.get(value);
		if (result == null) {
			throw new IllegalArgumentException("No TournamentSortType for value: " + value);
		}
		return result;
	}

	public String getValue() {
		return value;
	}

	public String getName() {
		return name();
	}
}
