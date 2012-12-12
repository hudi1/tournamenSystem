package org.toursys.repository.model;

public enum GroupType {

    B, // table in basic group
    F, // table in final group
    P; // play off

    public String value() {
        return name();
    }

    public static GroupType fromValue(String v) {
        return valueOf(v);
    }
}
