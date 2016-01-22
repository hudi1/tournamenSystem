package org.toursys.repository.model;

import java.util.Map;

@SuppressWarnings("all")
public enum PlayOffGameStatus {
  DRAW("DRAW"),
  
  LOSE("LOSE"),
  
  WIN("WIN");
  private static Map<String, PlayOffGameStatus> identifierMap =  identifierMapBuild();
  
  public static Map<String, PlayOffGameStatus> identifierMapBuild() {
    Map<String, PlayOffGameStatus> _identifierMap = new java.util.HashMap<String, PlayOffGameStatus>();
    for (PlayOffGameStatus value : PlayOffGameStatus.values()) {
    	_identifierMap.put(value.getValue(), value);
    }
    return _identifierMap;
  }
  
  private String value;
  
  private PlayOffGameStatus(final String value) {
    this.value = value;
  }
  
  public static PlayOffGameStatus fromValue(final String value) {
    PlayOffGameStatus result = identifierMap.get(value);
    if (result == null) {
    	throw new IllegalArgumentException("No PlayOffGameStatus for value: " + value);
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
