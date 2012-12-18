package org.toursys.processor.comparators;

import java.util.Comparator;

import org.toursys.repository.model.Game;
import org.toursys.repository.model.PlayerResult;

public class BasicComparator implements Comparator<PlayerResult> {

    public int compare(PlayerResult o1, PlayerResult o2) {

        if (o1.getPoints() > o2.getPoints()) {
            return -1;
        }
        if (o1.getPoints() < o2.getPoints()) {
            return 1;
        } else {

            Game playerGame = null;

            for (Game game : o1.getGames()) {
                if (game.getAwayPlayerResult().equals(o2)) {
                    playerGame = game;
                    break;
                }
            }
            if (playerGame == null) {
                return 0;
            }
            if (playerGame.getHomeScore() != null || playerGame.getAwayScore() != null) {
                if (playerGame.getHomeScore() > playerGame.getAwayScore()) {
                    return -1;
                }
                if (playerGame.getHomeScore() < playerGame.getAwayScore()) {
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
                            return 0;
                        }
                    }
                }
            } else {
                if (o1.getScore().getLeftSide() > o2.getScore().getLeftSide()) {
                    return -1;
                }
                if (o1.getScore().getLeftSide() < o2.getScore().getLeftSide()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }

    }

}
