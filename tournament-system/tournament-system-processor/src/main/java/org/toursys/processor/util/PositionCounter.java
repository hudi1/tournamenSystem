package org.toursys.processor.util;

import java.util.ArrayList;
import java.util.List;

public class PositionCounter {

    private int playerCount;

    private final List<Integer> list1 = new ArrayList<Integer>() {
        {
            add(1);
        }
    };

    private final List<Integer> list2 = new ArrayList<Integer>() {
        {
            add(1);
            add(2);
        }
    };

    private final List<Integer> list4 = new ArrayList<Integer>() {
        {
            add(1);
            add(4);
            add(3);
            add(2);
        }
    };

    private final List<Integer> list8 = new ArrayList<Integer>() {
        {
            add(1);
            add(8);
            add(5);
            add(4);
            add(3);
            add(6);
            add(7);
            add(2);
        }
    };

    private final List<Integer> list16 = new ArrayList<Integer>() {
        {
            add(1);
            add(16);
            add(9);
            add(8);
            add(5);
            add(12);
            add(13);
            add(4);
            add(3);
            add(14);
            add(11);
            add(6);
            add(7);
            add(10);
            add(15);
            add(2);
        }
    };

    public PositionCounter(int playerCount) {
        this.playerCount = playerCount;
    }

    public Integer getPosition(int index) {
        switch (playerCount) {
        case 2:
            return list1.get(index);
        case 4:
            return list2.get(index);
        case 8:
            return list4.get(index);
        case 16:
            return list8.get(index);
        case 32:
            return list16.get(index);
        }

        return index;
    }
}
