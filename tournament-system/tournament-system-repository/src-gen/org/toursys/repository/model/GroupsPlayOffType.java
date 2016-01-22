package org.toursys.repository.model;

import java.util.Map;

@SuppressWarnings("all")
public enum GroupsPlayOffType {
  CROSS("C"),
  
  FINAL("F"),
  
  LOWER("L");
  private static Map<String, GroupsPlayOffType> identifierMap =  identifierMapBuild();
  
  public static Map<String, GroupsPlayOffType> identifierMapBuild() {
    Map<String, GroupsPlayOffType> _identifierMap = new java.util.HashMap<String, GroupsPlayOffType>();
    for (GroupsPlayOffType value : GroupsPlayOffType.values()) {
    	_identifierMap.put(value.getValue(), value);
    }
    return _identifierMap;
  }
  
  private String value;
  
  private GroupsPlayOffType(final String value) {
    this.value = value;
  }
  
  public static GroupsPlayOffType fromValue(final String value) {
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
