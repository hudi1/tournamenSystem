package org.toursys.processor.comparators;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import org.toursys.repository.model.Participant;

/**
 * 
 * @author Hudi
 */
public class AdvantageComparator implements Comparator<Participant> {

    Set<Participant> sameRankPlayers;

    public AdvantageComparator() {
        this.sameRankPlayers = new HashSet<Participant>();
    }

    public int compare(Participant o1, Participant o2) {

        if (o1.getPoints() > o2.getPoints()) {
            return -1;
        }
        if (o1.getPoints() < o2.getPoints()) {
            return 1;
        } else {
            if ((o1.getScore().getLeftSide() - o1.getScore().getRightSide()) > (o2.getScore().getLeftSide() - o2
                    .getScore().getRightSide())) {
                return -1;
            }
            if ((o1.getScore().getLeftSide() - o1.getScore().getRightSide()) < (o2.getScore().getLeftSide() - o2
                    .getScore().getRightSide())) {
                return 1;
            } else {
                if (o1.getScore().getLeftSide() > o2.getScore().getLeftSide()) {
                    return -1;
                }
                if (o1.getScore().getLeftSide() < o2.getScore().getLeftSide()) {
                    return 1;
                } else {
                    if (o1.getEqualRank() == null || o2.getEqualRank() == null) {
                        sameRankPlayers.add(o1);
                        sameRankPlayers.add(o2);
                        return 0;
                    } else {
                        return o1.getEqualRank().compareTo(o2.getEqualRank());
                    }
                }
            }
        }
    }

    public Set<Participant> getSameRankPlayers() {
        return sameRankPlayers;
    }

}
