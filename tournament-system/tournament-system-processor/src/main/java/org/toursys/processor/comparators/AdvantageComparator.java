package org.toursys.processor.comparators;

import java.util.Comparator;

import org.toursys.processor.SamePlayerRankException;
import org.toursys.repository.model.PlayerResult;

/**
 * 
 * @author Hudi
 */
public class AdvantageComparator implements Comparator<PlayerResult> {

    public int compare(PlayerResult o1, PlayerResult o2) {

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
                        throw new SamePlayerRankException(o1, o2);
                    } else {
                        return o1.getEqualRank().compareTo(o2.getEqualRank());
                    }
                }
            }
        }
    }
}
