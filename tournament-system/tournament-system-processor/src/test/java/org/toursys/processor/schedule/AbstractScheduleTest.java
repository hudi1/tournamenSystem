package org.toursys.processor.schedule;

import java.util.Set;

import org.junit.Assert;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.GameImpl;
import org.toursys.repository.model.Participant;

public abstract class AbstractScheduleTest {

    protected void checkConstainsInRound(Participant playerResult, Set<Participant> players) {
        if (playerResult != null) {
            boolean contains = !players.add(playerResult);
            if (contains) {
                Assert.fail("Player: " + playerResult + " is already in this round.");
            }
        }
    }

    protected void checkConstainsInSchedule(GameImpl gameImpl, Set<Game> gamesInSchedule) {
        if (gameImpl != null && (gameImpl.getAwayParticipant() != null || gameImpl.getAwayParticipant() != null)) {
            for (Game game : gamesInSchedule) {
                if ((game.getAwayParticipant().equals(gameImpl.getHomeParticipant()) || game.getAwayParticipant()
                        .equals(gameImpl.getHomeParticipant()))
                        && (game.getHomeParticipant().equals(gameImpl.getHomeParticipant()) || game
                                .getHomeParticipant().equals(gameImpl.getAwayParticipant()))) {
                    Assert.fail("Game: " + gameImpl + " is already in this schedule.");
                }
            }
            gamesInSchedule.add(gameImpl);
        }
    }
}