package org.toursys.processor.comparators;

import java.util.Comparator;

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
            if ((o1.getResultScore().getLeftSide() - o1.getResultScore().getRightSide()) > (o2.getResultScore()
                    .getLeftSide() - o2.getResultScore().getRightSide())) {
                return -1;
            }
            if ((o1.getResultScore().getLeftSide() - o1.getResultScore().getRightSide()) < (o2.getResultScore()
                    .getLeftSide() - o2.getResultScore().getRightSide())) {
                return 1;
            } else {
                if (o1.getResultScore().getLeftSide() > o2.getResultScore().getLeftSide()) {
                    return -1;
                }
                if (o1.getResultScore().getLeftSide() < o2.getResultScore().getLeftSide()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }
}