package org.toursys.repository.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum UserRole implements Serializable {

  ADMIN("ADMIN"), USER("USER");
  
  private static final long serialVersionUID = 1L;
  
  private static Map<String, UserRole> identifierMap = new HashMap<String, UserRole>();

    static {
        for (UserRole value : UserRole.values()) {
            identifierMap.put(value.getValue(), value);
        }
    }

    private String value;

    private UserRole(String value) {
        this.value = value;
    }

    public static UserRole fromValue(String value) {
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
