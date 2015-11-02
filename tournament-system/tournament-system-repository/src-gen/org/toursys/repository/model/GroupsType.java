package org.toursys.repository.model;

import java.util.Map;

@SuppressWarnings("all")
public enum GroupsType {
  BASIC("B"),
  
  FINAL("F"),
  
  PLAY_OFF("P");
  private static Map<String, GroupsType> identifierMap =  identifierMapBuild();
  
  public static Map<String, GroupsType> identifierMapBuild() {
    Map<String, GroupsType> _identifierMap = new java.util.HashMap<String, GroupsType>();
    for (GroupsType value : GroupsType.values()) {
    	_identifierMap.put(value.getValue(), value);
    }
    return _identifierMap;
  }
  
  private String value;
  
  private GroupsType(final String value) {
    this.value = value;
  }
  
  public static GroupsType fromValue(final String value) {
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
