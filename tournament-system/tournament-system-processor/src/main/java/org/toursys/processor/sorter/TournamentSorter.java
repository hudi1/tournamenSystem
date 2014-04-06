package org.toursys.processor.sorter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.Score;
import org.toursys.repository.model.Tournament;

public abstract class TournamentSorter {

    protected Tournament tournament;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Transactional
    public abstract void sort(List<Participant> participants);

    protected void calculateParticipant(Participant participant) {
        long time = System.currentTimeMillis();
        logger.debug("Calculate participant: " + participant);
        int points = 0;
        Integer homeScore = 0;
        Integer awayScore = 0;
        for (Game game : participant.getGames()) {
            // TODO zistit preco ked participant nema ziadnu hru tak tam
            // nacitava jednu z id 0 ???
            if (game.getId() != null && game.getId() != 0) {
                if (game.getHomeScore() != null && game.getAwayScore() != null) {
                    if (game.getHomeScore() > game.getAwayScore()) {
                        points += tournament.getWinPoints();
                    } else if (game.getHomeScore().equals(game.getAwayScore())) {
                        points += 1;
                    }
                    homeScore += game.getHomeScore();
                    awayScore += game.getAwayScore();
                }
            }
        }

        participant.setPoints(points);
        participant.setScore(new Score(homeScore, awayScore));
        participant.setEqualRank(null);
        time = System.currentTimeMillis() - time;
        logger.debug("End: Calculate participant: " + time + " ms");
    }

    protected Participant cloneParticipant(Participant participant) {
        logger.debug("Clone participant: " + participant);
        Participant clone = new Participant();
        clone._setGroup(participant.getGroup())._setId(participant.getId())._setPlayer(participant.getPlayer())
                ._setPoints(participant.getPoints())._setRank(participant.getRank())._setScore(participant.getScore())
                ._setEqualRank(participant.getEqualRank());
        clone.getGames().addAll(participant.getGames());
        return clone;
    }

}
