package org.toursys.processor.sorter;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.Result;
import org.toursys.repository.model.Score;
import org.toursys.repository.model.Tournament;

public abstract class TournamentSorter {

    protected Tournament tournament;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Transactional
    public abstract void sort(List<Participant> participants);

    protected void calculateParticipants(List<Participant> participants) {
        logger.debug("Calculate participant: " + Arrays.toString(participants.toArray()));
        long time = System.currentTimeMillis();

        for (Participant participant : participants) {
            int points = 0;
            Integer homeScore = 0;
            Integer awayScore = 0;
            for (Game game : participant.getGames()) {
                // TODO zistit preco ked participant nema ziadnu hru tak tam
                // nacitava jednu z id 0 ???
                if (game.getId() != null && game.getId() != 0) {
                    if (game.getResult() != null) {
                        for (Result result : game.getResult().getResults()) {
                            if (result.getLeftSide() > result.getRightSide()) {
                                points += tournament.getWinPoints();
                            } else if (result.getLeftSide() == result.getRightSide()) {
                                points += 1;
                            }

                            homeScore += result.getLeftSide();
                            awayScore += result.getRightSide();
                        }
                    }
                }
            }

            participant.setPoints(points);
            participant.setScore(new Score(homeScore, awayScore));
            participant.setEqualRank(null);
        }

        time = System.currentTimeMillis() - time;
        logger.debug("End: Calculate participant: " + time + " ms");
    }

    protected void calculateInnerParticipantsPoint(List<Participant> participants) {
        for (Participant participant1 : participants) {
            int points = 0;
            int left = 0;
            int right = 0;
            for (Game game : participant1.getGames()) {
                if (participants.contains(game.getAwayParticipant())) {
                    if (game.getId() != null && game.getId() != 0) {
                        if (game.getResult() != null) {
                            for (Result result : game.getResult().getResults()) {
                                left += result.getLeftSide();
                                right += result.getRightSide();
                                if (result.getLeftSide() > result.getRightSide()) {
                                    points += tournament.getWinPoints();
                                } else if (result.getLeftSide() == result.getRightSide()) {
                                    points += 1;
                                }
                            }
                        }
                    }
                }
            }
            participant1.setPoints(points);
            participant1.setScore(new Score(left, right));
        }
    }
}
