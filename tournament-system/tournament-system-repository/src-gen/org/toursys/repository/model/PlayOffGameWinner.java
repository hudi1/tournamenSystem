package org.toursys.repository.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum PlayOffGameWinner implements Serializable {

  HOME("HOME"), AWAY("AWAY");
  
  private static final long serialVersionUID = 1L;
  
  private static Map<String, PlayOffGameWinner> identifierMap = new HashMap<String, PlayOffGameWinner>();

    static {
        for (PlayOffGameWinner value : PlayOffGameWinner.values()) {
            identifierMap.put(value.getValue(), value);
        }
    }

    private String value;

    private PlayOffGameWinner(String value) {
        this.value = value;
    }

    public static PlayOffGameWinner fromValue(String value) {
        PlayOffGameWinner result = identifierMap.get(value);
        if (result == null) {
            throw new IllegalArgumentException("No PlayOffGameWinner for value: " + value);
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
