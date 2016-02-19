package org.tahom.repository.model;

import java.util.Map;

@SuppressWarnings("all")
public enum TournamentSortType {
  CZ("CZ"),
  
  SK("SK");
  private static Map<String, TournamentSortType> identifierMap =  identifierMapBuild();
  
  public static Map<String, TournamentSortType> identifierMapBuild() {
    Map<String, TournamentSortType> _identifierMap = new java.util.HashMap<String, TournamentSortType>();
    for (TournamentSortType value : TournamentSortType.values()) {
    	_identifierMap.put(value.getValue(), value);
    }
    return _identifierMap;
  }
  
  private String value;
  
  private TournamentSortType(final String value) {
    this.value = value;
  }
  
  public static TournamentSortType fromValue(final String value) {
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
