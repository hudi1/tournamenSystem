package org.toursys.processor.comparators;

import java.util.Comparator;

import org.toursys.repository.model.Game;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.Result;

public class SkParticipantComparator implements Comparator<Participant> {

    public SkParticipantComparator() {
    }

    public int compare(Participant participant1, Participant participant2) {

        if (participant1.getPoints() > participant2.getPoints()) {
            return -1;
        }
        if (participant1.getPoints() < participant2.getPoints()) {
            return 1;
        }

        Game playerGame = findGame(participant1, participant2);

        if (playerGame == null || playerGame.getResult() == null) {
            return 0;
        }

        int homeWinnerCount = 0;
        int awayWinnerCount = 0;
        for (Result result : playerGame.getResult().getResults()) {
            if (result.getLeftSide() > result.getRightSide()) {
                homeWinnerCount++;
            } else if (result.getLeftSide() < result.getRightSide()) {
                awayWinnerCount++;
            }
        }

        if (homeWinnerCount > awayWinnerCount) {
            return -1;
        }
        if (homeWinnerCount < awayWinnerCount) {
            return 1;
        }

        System.out.println("ccccccccccccccccccc1" + participant1.getPlayer());
        System.out.println("ccccccccccccccccccc2" + participant2.getPlayer());
        System.out.println("ccccccccccccccccccc1" + participant1.getScore());
        System.out.println("ccccccccccccccccccc2" + participant2.getScore());

        if ((participant1.getScore().getLeftSide() - participant1.getScore().getRightSide()) > (participant2.getScore()
                .getLeftSide() - participant2.getScore().getRightSide())) {
            return -1;
        }
        if ((participant1.getScore().getLeftSide() - participant1.getScore().getRightSide()) < (participant2.getScore()
                .getLeftSide() - participant2.getScore().getRightSide())) {
            return 1;
        }

        if (participant1.getScore().getLeftSide() > participant2.getScore().getLeftSide()) {
            return -1;
        }
        if (participant1.getScore().getLeftSide() < participant2.getScore().getLeftSide()) {
            return 1;
        }
        int homeWinnerCount1 = 0;
        int homeWinnerCount2 = 0;

        for (Game game : participant1.getGames()) {
            if (game.getResult() == null) {
                continue;
            }
            for (Result result : game.getResult().getResults()) {
                if (result.getLeftSide() > result.getRightSide()) {
                    homeWinnerCount1++;
                }
            }
        }
        for (Game game : participant2.getGames()) {
            if (game.getResult() == null) {
                continue;
            }
            for (Result result : game.getResult().getResults()) {
                if (result.getLeftSide() > result.getRightSide()) {
                    homeWinnerCount2++;
                }
            }
        }
        if (homeWinnerCount1 > homeWinnerCount2) {
            return -1;
        } else if (homeWinnerCount1 < homeWinnerCount2) {
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