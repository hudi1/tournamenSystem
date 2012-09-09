package org.toursys.processor.comparators;

import java.util.Comparator;

import org.toursys.repository.model.Game;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Result;

public class BasicComparator implements Comparator<PlayerResult> {

    public int compare(PlayerResult o1, PlayerResult o2) {

        if (o1.getPoints() > o2.getPoints()) {
            return -1;
        }
        if (o1.getPoints() < o2.getPoints()) {
            return 1;
        } else {

            Result result = null;// = tournamentAggregationDao.findGame(new GameForm(o1, o2)).get(0).getResult();

            for (Game game : o1.getGames()) {
                if (game.getOpponent().equals(o2)) {
                    result = game.getResult();
                }
            }
            if (result == null) {
                return 0;
            }
            if (result.getLeftSide() != null || result.getRightSide() != null) {
                if (result.getLeftSide() > result.getRightSide()) {
                    return -1;
                }
                if (result.getLeftSide() < result.getRightSide()) {
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
