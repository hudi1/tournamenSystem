package org.tahom.web.util;

import java.util.LinkedList;

public class RegistrationOptions extends LinkedList<String> {

    private static final long serialVersionUID = 1L;
    public static final String SNAKE = "snake";
    public static final String MANUAL = "manual";

    public RegistrationOptions() {
        add(MANUAL);
        add(SNAKE);
    }

}
