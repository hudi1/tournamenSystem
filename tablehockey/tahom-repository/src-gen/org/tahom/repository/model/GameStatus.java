package org.tahom.repository.model;

import java.util.Map;

@SuppressWarnings("all")
public enum GameStatus {
  DRAW("DRAW"),
  
  LOSE("LOSE"),
  
  WIN("WIN");
  private static Map<String, GameStatus> identifierMap =  identifierMapBuild();
  
  public static Map<String, GameStatus> identifierMapBuild() {
    Map<String, GameStatus> _identifierMap = new java.util.HashMap<String, GameStatus>();
    for (GameStatus value : GameStatus.values()) {
    	_identifierMap.put(value.getValue(), value);
    }
    return _identifierMap;
  }
  
  private String value;
  
  private GameStatus(final String value) {
    this.value = value;
  }
  
  public static GameStatus fromValue(final String value) {
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
