package org.toursys.processor.schedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toursys.processor.BadOptionsTournamentException;
import org.toursys.processor.service.TournamentService;
import org.toursys.repository.model.GameImpl;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Participant;

public class AdvancedRoundRobinSchedule extends RoundRobinSchedule {

    @SpringBean(name = "tournamentService")
    private TournamentService tournamentService;

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private LinkedList<List<Participant>> playerPerBasicGroup;
    private int playerCount;

    // TODO optimalizovat pre menej hokej
    public AdvancedRoundRobinSchedule(final Groups finalGroup, LinkedList<List<Participant>> playerPerBasicGroup) {
        super(finalGroup);
        this.playerPerBasicGroup = playerPerBasicGroup;
    }

    @Override
    protected void createSchedule() {
        logger.debug("Creating advanced round robim schedule, playerCount: " + playerPerBasicGroup.get(0).size());
        schedule = new ArrayList<GameImpl>();
        if (playerPerBasicGroup.isEmpty()) {
            return;
        }

        // TODO vyhodit a osetrit aby k tomu nemohlo nastat
        checkPlayerCount();
        playerCount = playerPerBasicGroup.get(0).size();
        int groupCount = playerPerBasicGroup.size();

        if (groupCount % 2 == 0) {
            for (int i = 0; i < groupCount - 1; i++) {
                createEvenGroupRound();
                rotatePlayerPerBasicGroup();
            }
        } else {
            // TODO neparny pocet skupin
            createOddGroupRound();
        }
    }

    private void checkPlayerCount() {
        for (int i = 0; i < playerPerBasicGroup.size() - 1; i++) {
            if (playerPerBasicGroup.get(i).size() != playerPerBasicGroup.get(i + 1).size()) {
                throw new BadOptionsTournamentException();
            }
        }
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
        List<Participant> lastGroupPlayer = playerPerBasicGroup.removeLast();
        playerPerBasicGroup.add(1, lastGroupPlayer);
    }

    private void fillUpHorizontalGroup(List<Round> rounds, int i, LinkedList<List<Participant>> playerByGroup) {
        List<Participant> participants1 = playerByGroup.get(i).subList(0, playerByGroup.get(i).size() / 2);
        int index = i + 1;
        if (index == playerByGroup.size()) {
            index = 0;
        }
        List<Participant> participants2 = playerByGroup.get(index).subList(0, playerByGroup.get(i).size() / 2);
        rounds.add(new Round(participants1, participants2));

    }

    private void fillDownHorizontalGroup(List<Round> rounds, int i, LinkedList<List<Participant>> playerByGroup) {
        List<Participant> participants1 = playerByGroup.get(i).subList(playerByGroup.get(i).size() / 2,
                playerByGroup.get(i).size());
        int index = i + 1;
        if (index == playerByGroup.size()) {
            index = 0;
        }
        List<Participant> participants2 = playerByGroup.get(index).subList(playerByGroup.get(i).size() / 2,
                playerByGroup.get(i).size());
        rounds.add(new Round(participants1, participants2));
    }

    private void fillVerticalGroup(List<Round> rounds, int i, LinkedList<List<Participant>> playerByGroup) {
        List<Participant> participants1 = playerByGroup.get(i).subList(0, playerByGroup.get(i).size() / 2);
        int index = i + 1;
        if (index == playerByGroup.size()) {
            index = 0;
        }
        List<Participant> participants2 = playerByGroup.get(index).subList(playerByGroup.get(i).size() / 2,
                playerByGroup.get(i).size());
        rounds.add(new Round(participants1, participants2));
    }

    public class Round {

        private List<Participant> participants1;
        private List<Participant> participants2;

        public Round(List<Participant> participants1, List<Participant> participants2) {
            this.participants1 = participants1;
            this.participants2 = participants2;
        }

        public void addNextGames() {
            for (int i = 0; i < participants1.size(); i++) {
                addGameToSchedule(participants1.get(i), participants2.get(i));
            }
            Collections.rotate(participants1, 1);
        }

    }
}