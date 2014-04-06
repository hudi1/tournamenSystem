package org.toursys.processor.comparators;

import java.util.Comparator;

import org.toursys.repository.model.Game;
import org.toursys.repository.model.Participant;

public class SkParticipantComparator implements Comparator<Participant> {

    private final boolean ignoreCommonGame;
    private final boolean commonSort;

    public SkParticipantComparator() {
        this.ignoreCommonGame = false;
        this.commonSort = false;
    }

    public SkParticipantComparator(boolean ignoreCommonGame, boolean commonSort) {
        this.ignoreCommonGame = ignoreCommonGame;
        this.commonSort = commonSort;
    }

    public int compare(Participant participant1, Participant participant2) {

        if (participant1.getPoints() > participant2.getPoints()) {
            return -1;
        }
        if (participant1.getPoints() < participant2.getPoints()) {
            return 1;
        }

        if (!ignoreCommonGame) {
            Game playerGame = findGame(participant1, participant2);

            if (playerGame == null || playerGame.getHomeScore() != null || playerGame.getAwayScore() != null) {
                return 0;
            }
            if (playerGame.getHomeScore() > playerGame.getAwayScore()) {
                return -1;
            }
            if (playerGame.getHomeScore() < playerGame.getAwayScore()) {
                return 1;
            }
        }

        if ((participant1.getScore().getLeftSide() - participant1.getScore().getRightSide()) > (participant2.getScore()
                .getLeftSide() - participant2.getScore().getRightSide())) {
            return -1;
        }
        if ((participant1.getScore().getLeftSide() - participant1.getScore().getRightSide()) < (participant2.getScore()
                .getLeftSide() - participant2.getScore().getRightSide())) {
            return 1;
        }

        if (commonSort) {
            return 0;
        }

        if (participant1.getScore().getLeftSide() > participant2.getScore().getLeftSide()) {
            return -1;
        }
        if (participant1.getScore().getLeftSide() < participant2.getScore().getLeftSide()) {
            return 1;
        }
        int winnerCount1 = 0;
        int winnerCount2 = 0;
        for (Game game : participant1.getGames()) {
            if (game.getHomeScore() != null && game.getAwayParticipant() != null) {
                if (game.getHomeScore() > game.getAwayScore()) {
                    winnerCount1++;
                }
            }
        }
        for (Game game : participant2.getGames()) {
            if (game.getHomeScore() != null && game.getAwayScore() != null) {
                if (game.getHomeScore() > game.getAwayScore()) {
                    winnerCount2++;
                }
            }
        }
        if (winnerCount1 > winnerCount2) {
            return -1;
        } else if (winnerCount1 < winnerCount2) {
            return 1;
        }

        if (participant1.getEqualRank() == null && participant1.getPoints() > 0) {
            participant1.setEqualRank(0);
        }
        if (participant2.getEqualRank() == null && participant2.getPoints() > 0) {
            participant2.setEqualRank(0);
        }
        return 0;
    }

    private Game findGame(Participant participant1, Participant participant2) {
        for (Game game : participant1.getGames()) {
            if (game.getAwayParticipant().equals(participant2)) {
                return game;
            }
        }
        return null;
    }
}