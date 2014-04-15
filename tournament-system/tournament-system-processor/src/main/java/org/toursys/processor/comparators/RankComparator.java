package org.toursys.processor.comparators;

import java.util.Comparator;

import org.toursys.repository.model.Participant;

public class RankComparator implements Comparator<Participant> {

    public int compare(Participant o1, Participant o2) {

        if (o1.getRank() == null || o2.getRank() == null) {
            return 0;
        }
        if (o1.getRank() > o2.getRank()) {
            return 1;
        }
        if (o1.getRank() < o2.getRank()) {
            return -1;
        }
        return 0;
    }
}