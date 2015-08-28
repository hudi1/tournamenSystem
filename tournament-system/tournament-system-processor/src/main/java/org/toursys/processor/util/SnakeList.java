package org.toursys.processor.util;

import java.util.LinkedList;

public class SnakeList extends LinkedList<Integer> {

    private static final long serialVersionUID = 1L;

    public SnakeList(Integer maxlength) {
        for (int i = 1; i <= maxlength; i++) {
            add(i);
        }
        for (int i = maxlength; i > 0; i--) {
            add(i);
        }
    }
}
