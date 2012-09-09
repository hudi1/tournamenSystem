package org.toursys.processor.comparators;

import java.util.Comparator;

import org.toursys.repository.model.PlayerResult;

public class RankComparator implements Comparator<PlayerResult> {

    public int compare(PlayerResult o1, PlayerResult o2) {

        if (o1.getRank() < o2.getRank()) {
            return -1;
        }
        if (o1.getRank() > o2.getRank()) {
            return 1;
        } else {
            return 0;

        }
    }
}
