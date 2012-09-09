package org.toursys.repository.model;

public enum TableType {

    B, // table in basic group
    F, // table in final group
    P; // play off

    public String value() {
        return name();
    }

    public static TableType fromValue(String v) {
        return valueOf(v);
    }
}
