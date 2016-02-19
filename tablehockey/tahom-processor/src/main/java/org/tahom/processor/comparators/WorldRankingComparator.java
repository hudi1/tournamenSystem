package org.tahom.processor.comparators;

import java.util.Comparator;

import org.tahom.repository.model.Participant;

public class WorldRankingComparator implements Comparator<Participant> {

    public int compare(Participant o1, Participant o2) {

        if (o1.getPlayer() == null || o1.getPlayer().getWorldRanking() == null || o2.getPlayer() == null
                || o2.getPlayer().getWorldRanking() == null) {
            return 0;
        }
        if (o1.getPlayer().getWorldRanking() > o2.getPlayer().getWorldRanking()) {
            return 1;
        }
        if (o1.getPlayer().getWorldRanking() < o2.getPlayer().getWorldRanking()) {
            return -1;
        }
        return 0;
    }
}