package org.tahom.repository.model;

import java.util.Map;

@SuppressWarnings("all")
public enum TournamentType {
  A("A"),
  
  B("B");
  private static Map<String, TournamentType> identifierMap =  identifierMapBuild();
  
  public static Map<String, TournamentType> identifierMapBuild() {
    Map<String, TournamentType> _identifierMap = new java.util.HashMap<String, TournamentType>();
    for (TournamentType value : TournamentType.values()) {
    	_identifierMap.put(value.getValue(), value);
    }
    return _identifierMap;
  }
  
  private String value;
  
  private TournamentType(final String value) {
    this.value = value;
  }
  
  public static TournamentType fromValue(final String value) {
    TournamentType result = identifierMap.get(value);
    if (result == null) {
    	throw new IllegalArgumentException("No TournamentType for value: " + value);
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
