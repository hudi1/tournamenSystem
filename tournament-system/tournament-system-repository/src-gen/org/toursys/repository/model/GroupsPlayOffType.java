package org.toursys.repository.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum GroupsPlayOffType implements Serializable {

	FINAL("F"), LOWER("L"), CROSS("C");
		
	private static final long serialVersionUID = 1L;
	
	private static Map<String, GroupsPlayOffType> identifierMap = new HashMap<String, GroupsPlayOffType>();

	static {
		for (GroupsPlayOffType value : GroupsPlayOffType.values()) {
			identifierMap.put(value.getValue(), value);
		}
	}

	private String value;

	private GroupsPlayOffType(String value) {
		this.value = value;
	}

	public static GroupsPlayOffType fromValue(String value) {
		GroupsPlayOffType result = identifierMap.get(value);
		if (result == null) {
			throw new IllegalArgumentException("No GroupsPlayOffType for value: " + value);
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
