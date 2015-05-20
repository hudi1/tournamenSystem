package org.toursys.repository.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum GameStatus implements Serializable {

	WIN("WIN"), LOSE("LOSE"), DRAW("DRAW");
		
	private static final long serialVersionUID = 1L;
	
	private static Map<String, GameStatus> identifierMap = new HashMap<String, GameStatus>();

	static {
		for (GameStatus value : GameStatus.values()) {
			identifierMap.put(value.getValue(), value);
		}
	}

	private String value;

	private GameStatus(String value) {
		this.value = value;
	}

	public static GameStatus fromValue(String value) {
		GameStatus result = identifierMap.get(value);
		if (result == null) {
			throw new IllegalArgumentException("No GameStatus for value: " + value);
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
