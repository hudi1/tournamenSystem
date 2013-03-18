package org.toursys.processor.schedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.toursys.repository.model.Game;
import org.toursys.repository.model.GameImpl;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.PlayerResult;

public class BasicRoundRobinSchedule implements RoundRobinSchedule {
    private Groups group;
    private List<PlayerResult> playerResults;
    private int playerCount;
    private List<GameImpl> schedule;

    public BasicRoundRobinSchedule(Groups group, List<PlayerResult> playerResults) {
        this.group = group;
        this.playerResults = new ArrayList<PlayerResult>(playerResults);
        if (this.playerResults.size() % 2 == 1) {
            this.playerResults.add(new PlayerResult());
        }
        this.playerCount = this.playerResults.size();
    }

    public List<GameImpl> getSchedule() {
        if (schedule == null) {
            createSchedule();
        }
        return schedule;
    }

    private void createSchedule() {
        int roundCount = playerCount;
        schedule = new ArrayList<GameImpl>();

        for (int i = 0; i < roundCount; i++) {
            if (i == 0 || i < roundCount / 2) {
                addFirstHalfRoundGames();
            } else {
                addSecondHalfRoundGames();
            }
            Collections.rotate(playerResults, 1);
        }
    }

    private void addFirstHalfRoundGames() {
        for (int i = 0; i < playerCount / 2; i++) {
            addNewGame(playerResults.get(i), playerResults.get(playerCount - i - 1));
        }
    }

    private void addSecondHalfRoundGames() {
        for (int i = 0; i < playerCount / 2; i++) {
            if (i != 0) {
                addNewGame(playerResults.get(i), playerResults.get(playerCount - i));
            } else {
                addPausedGame();
            }
        }
    }

    private void addPausedGame() {
        GameImpl gameImpl = new GameImpl();
        gameImpl.setHockey(schedule.size() % group.getNumberOfHockey() + group.getIndexOfFirstHockey());
        gameImpl.setRound(schedule.size() / group.getNumberOfHockey() + 1);
        schedule.add(gameImpl);
    }

    private void addNewGame(PlayerResult homePlayer, PlayerResult awayPlayer) {
        if (homePlayer.getId() != null && awayPlayer.getId() != null) {
            for (Game game : homePlayer.getGames()) {
                if (game.getAwayPlayerResult().equals(awayPlayer)) {
                    GameImpl gameImpl = new GameImpl(game);
                    gameImpl.setHockey(schedule.size() % group.getNumberOfHockey() + group.getIndexOfFirstHockey());
                    gameImpl.setRound(schedule.size() / group.getNumberOfHockey() + 1);
                    schedule.add(gameImpl);
                    break;
                }
            }
        }
    }
}
