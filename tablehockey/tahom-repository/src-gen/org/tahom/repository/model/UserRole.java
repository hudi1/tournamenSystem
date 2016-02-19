package org.tahom.repository.model;

import java.util.Map;

@SuppressWarnings("all")
public enum UserRole {
  ADMIN("ADMIN"),
  
  USER("USER");
  private static Map<String, UserRole> identifierMap =  identifierMapBuild();
  
  public static Map<String, UserRole> identifierMapBuild() {
    Map<String, UserRole> _identifierMap = new java.util.HashMap<String, UserRole>();
    for (UserRole value : UserRole.values()) {
    	_identifierMap.put(value.getValue(), value);
    }
    return _identifierMap;
  }
  
  private String value;
  
  private UserRole(final String value) {
    this.value = value;
  }
  
  public static UserRole fromValue(final String value) {
    UserRole result = identifierMap.get(value);
    if (result == null) {
    	throw new IllegalArgumentException("No UserRole for value: " + value);
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
