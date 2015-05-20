package org.toursys.repository.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum GroupsType implements Serializable {

	BASIC("B"), FINAL("F"), PLAY_OFF("P");
		
	private static final long serialVersionUID = 1L;
	
	private static Map<String, GroupsType> identifierMap = new HashMap<String, GroupsType>();

	static {
		for (GroupsType value : GroupsType.values()) {
			identifierMap.put(value.getValue(), value);
		}
	}

	private String value;

	private GroupsType(String value) {
		this.value = value;
	}

	public static GroupsType fromValue(String value) {
		GroupsType result = identifierMap.get(value);
		if (result == null) {
			throw new IllegalArgumentException("No GroupsType for value: " + value);
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
