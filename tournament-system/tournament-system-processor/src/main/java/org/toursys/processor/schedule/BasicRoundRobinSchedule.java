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
    private int playerCount;
    private int roundCount;
    private List<PlayerResult> playerResults;
    private List<GameImpl> schedule;

    public BasicRoundRobinSchedule(Groups group, List<PlayerResult> playerResults) {
        this.group = group;
        this.playerResults = new ArrayList<PlayerResult>(playerResults);
        this.roundCount = playerResults.size();
        if (this.playerResults.size() % 2 == 1) {
            this.playerResults.add(new PlayerResult()._setTemp(true));
        }
        this.playerCount = this.playerResults.size();
        this.roundCount = playerCount - 1;
    }

    public List<GameImpl> getSchedule() {
        if (schedule == null) {
            createSchedule();
            changeTables();
        }
        return schedule;
    }

    private void changeTables() {
        Integer round = 1;
        List<GameImpl> gameInRound = new ArrayList<GameImpl>();
        List<GameImpl> newSchedule = new ArrayList<GameImpl>();
        for (GameImpl game : schedule) {
            if (game.getRound().equals(round)) {
                gameInRound.add(game);
            } else {
                Collections.shuffle(gameInRound);
                newSchedule.addAll(gameInRound);
                gameInRound.clear();
                gameInRound.add(game);
                round++;
            }
        }
        // TODO vymysliet daco ine
        // Collections.shuffle(gameInRound);
        newSchedule.addAll(gameInRound);
        schedule = new ArrayList<GameImpl>(newSchedule);
    }

    private void createSchedule() {
        schedule = new ArrayList<GameImpl>();
        for (int i = 0; i < roundCount; i++) {
            addRoundGames(i % 2 == 1);
            rotate(playerResults);
        }
    }

    private void rotate(List<PlayerResult> playerResults) {
        PlayerResult temp = playerResults.remove(playerResults.size() - 1);
        playerResults.add(1, temp);
    }

    private void addRoundGames(boolean rotate) {
        for (int i = 0; i < playerCount / 2; i++) {
            if ((i == 0 && rotate) || (i % 2 == 1)) {
                addNewGame(playerResults.get(playerCount - i - 1), playerResults.get(i));
            } else {
                addNewGame(playerResults.get(i), playerResults.get(playerCount - i - 1));
            }
        }
    }

    private void addNewGame(PlayerResult homePlayer, PlayerResult awayPlayer) {
        if (!homePlayer.getTemp() && !awayPlayer.getTemp()) {
            for (Game game : homePlayer.getGames()) {
                if (game.getAwayPlayerResult().equals(awayPlayer)) {
                    game._setHomePlayerResult(homePlayer)._setAwayPlayerResult(awayPlayer);
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
