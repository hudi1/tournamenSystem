package org.toursys.processor.schedule;

import java.util.List;

import org.toursys.repository.model.Game;
import org.toursys.repository.model.GameImpl;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Participant;

public abstract class RoundRobinSchedule {

    protected List<GameImpl> schedule;
    protected Groups group;

    public RoundRobinSchedule(Groups group) {
        this.group = group;
    }

    public List<GameImpl> getSchedule() {
        if (schedule == null) {
            createSchedule();
        }
        return schedule;
    }

    protected void changeTables() {
    }

    protected void addGameToSchedule(Participant homePlayer, Participant awayPlayer) {
        if (!homePlayer.getTemp() && !awayPlayer.getTemp()) {
            for (Game game : homePlayer.getGames()) {
                if (game.getAwayParticipant().equals(awayPlayer)) {
                    game._setHomeParticipant(homePlayer)._setAwayParticipant(awayPlayer);
                    GameImpl gameImpl = new GameImpl(game);
                    gameImpl.setRound(schedule.size() / group.getNumberOfHockey() + 1);
                    gameImpl.setHockey(schedule.size() % group.getNumberOfHockey() + group.getIndexOfFirstHockey());
                    schedule.add(gameImpl);
                    break;
                }
            }
        }
    }

    protected void addEmptyGameToSchedule() {
        GameImpl gameImpl = new GameImpl();
        gameImpl.setRound(schedule.size() / group.getNumberOfHockey() + 1);
        gameImpl.setHockey(schedule.size() % group.getNumberOfHockey() + group.getIndexOfFirstHockey());
        schedule.add(gameImpl);
    }

    protected abstract void createSchedule();
}
