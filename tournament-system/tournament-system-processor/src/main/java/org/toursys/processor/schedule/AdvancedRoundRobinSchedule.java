package org.toursys.processor.schedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.toursys.processor.TournamentException;
import org.toursys.processor.service.TournamentService;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.GameImpl;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.PlayerResult;

public class AdvancedRoundRobinSchedule implements RoundRobinSchedule {

    @SpringBean(name = "tournamentService")
    private TournamentService tournamentService;

    private Groups finalGroup;
    private LinkedList<List<PlayerResult>> playerPerBasicGroup;
    private List<GameImpl> schedule;
    private int playerCount;

    public AdvancedRoundRobinSchedule(final Groups finalGroup, LinkedList<List<PlayerResult>> playerPerBasicGroup) {
        this.finalGroup = finalGroup;
        this.playerPerBasicGroup = playerPerBasicGroup;
    }

    public void createSchedule() {
        schedule = new ArrayList<GameImpl>();
        if (playerPerBasicGroup.isEmpty()) {
            return;
        }

        // TODO vyhodit a osetrit aby k tomu nemohlo nastat
        checkPlayerCount();

        int groupCount = playerPerBasicGroup.size();

        if (groupCount % 2 == 0) {
            for (int i = 0; i < groupCount; i++) {
                createEvenGroupRound();
                rotatePlayerPerBasicGroup();
            }
        } else {
            // TODO neparny pocet skupin
            // createOddGroupRound();
        }
    }

    @Override
    public List<GameImpl> getSchedule() {
        if (schedule == null) {
            createSchedule();
        }

        return schedule;
    }

    private void checkPlayerCount() {
        for (int i = 0; i < playerPerBasicGroup.size() - 1; i++) {
            if (playerPerBasicGroup.get(i).size() != playerPerBasicGroup.get(i + 1).size()) {
                throw new TournamentException("Count of players in groups where is counted previous result is not same");
            }
        }
        playerCount = playerPerBasicGroup.get(0).size();
    }

    private void createEvenGroupRound() {
        List<Round> rounds = new ArrayList<Round>();
        for (int i = 0; i < playerPerBasicGroup.size() / 2; i++) {
            rounds.add(new Round(playerPerBasicGroup.get(i),
                    playerPerBasicGroup.get(playerPerBasicGroup.size() - i - 1)));
        }

        for (int i = 0; i < playerCount; i++) {
            for (Round round : rounds) {
                round.addNextGames();
            }
        }
    }

    private void createOddGroupRound() {
        List<Round> rounds = new ArrayList<Round>();

        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < playerPerBasicGroup.size(); i++) {

                if (playerPerBasicGroup.size() % 2 == 1) {
                    if (j % 2 == 0) {
                        if (i % 2 == 0) {
                            if (i == playerPerBasicGroup.size() - 1) {
                                fillVerticalGroup(rounds, i, playerPerBasicGroup);
                            } else {
                                fillUpHorizontalGroup(rounds, i, playerPerBasicGroup);
                            }
                        } else {
                            fillDownHorizontalGroup(rounds, i, playerPerBasicGroup);
                        }
                    } else {
                        if (i % 2 == 0) {
                            if (i == 0) {
                                fillVerticalGroup(rounds, i, playerPerBasicGroup);
                            } else {
                                fillDownHorizontalGroup(rounds, i, playerPerBasicGroup);
                            }
                        } else {
                            fillUpHorizontalGroup(rounds, i, playerPerBasicGroup);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < playerCount; i++) {
            for (Round round : rounds) {
                round.addNextGames();
            }
        }
    }

    private void rotatePlayerPerBasicGroup() {
        List<PlayerResult> lastGroupPlayer = playerPerBasicGroup.removeLast();
        playerPerBasicGroup.add(1, lastGroupPlayer);
    }

    private void fillUpHorizontalGroup(List<Round> rounds, int i, LinkedList<List<PlayerResult>> playerByGroup) {
        List<PlayerResult> playerResults1 = playerByGroup.get(i).subList(0, playerByGroup.get(i).size() / 2);
        int index = i + 1;
        if (index == playerByGroup.size()) {
            index = 0;
        }
        List<PlayerResult> playerResults2 = playerByGroup.get(index).subList(0, playerByGroup.get(i).size() / 2);
        rounds.add(new Round(playerResults1, playerResults2));

    }

    private void fillDownHorizontalGroup(List<Round> rounds, int i, LinkedList<List<PlayerResult>> playerByGroup) {
        List<PlayerResult> playerResults1 = playerByGroup.get(i).subList(playerByGroup.get(i).size() / 2,
                playerByGroup.get(i).size());
        int index = i + 1;
        if (index == playerByGroup.size()) {
            index = 0;
        }
        List<PlayerResult> playerResults2 = playerByGroup.get(index).subList(playerByGroup.get(i).size() / 2,
                playerByGroup.get(i).size());
        rounds.add(new Round(playerResults1, playerResults2));
    }

    private void fillVerticalGroup(List<Round> rounds, int i, LinkedList<List<PlayerResult>> playerByGroup) {
        List<PlayerResult> playerResults1 = playerByGroup.get(i).subList(0, playerByGroup.get(i).size() / 2);
        int index = i + 1;
        if (index == playerByGroup.size()) {
            index = 0;
        }
        List<PlayerResult> playerResults2 = playerByGroup.get(index).subList(playerByGroup.get(i).size() / 2,
                playerByGroup.get(i).size());
        rounds.add(new Round(playerResults1, playerResults2));
    }

    public class Round {

        private List<PlayerResult> playerResults1;
        private List<PlayerResult> playerResults2;

        public Round(List<PlayerResult> playerResults1, List<PlayerResult> playerResults2) {
            this.playerResults1 = playerResults1;
            this.playerResults2 = playerResults2;
        }

        public void addNextGames() {
            for (int i = 0; i < playerResults2.size(); i++) {
                addNewGame(playerResults1.get(i), playerResults2.get(i));
            }
            Collections.rotate(playerResults2, 1);
        }

        private void addNewGame(PlayerResult homePlayer, PlayerResult awayPlayer) {
            if (homePlayer.getId() != null && awayPlayer.getId() != null) {
                for (Game game : homePlayer.getGames()) {
                    if (game.getAwayPlayerResult().equals(awayPlayer)) {
                        game._setHomePlayerResult(homePlayer)._setAwayPlayerResult(awayPlayer);
                        GameImpl gameImpl = new GameImpl(game);
                        gameImpl.setHockey(schedule.size() % finalGroup.getNumberOfHockey()
                                + finalGroup.getIndexOfFirstHockey());
                        gameImpl.setRound(schedule.size() / finalGroup.getNumberOfHockey() + 1);
                        schedule.add(gameImpl);
                        break;
                    }
                }
            }
        }
    }
}