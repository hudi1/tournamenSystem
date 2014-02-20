package org.toursys.processor.schedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.toursys.repository.model.GameImpl;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Participant;

public class BasicRoundRobinSchedule extends RoundRobinSchedule {
    private int playerCount;
    private int roundCount;
    private List<Participant> participants;

    public BasicRoundRobinSchedule(Groups group, List<Participant> participants) {
        super(group);
        this.participants = new ArrayList<Participant>(participants);
        this.roundCount = participants.size();
        if (this.participants.size() % 2 == 1) {
            this.participants.add(new Participant()._setTemp(true));
        }
        this.playerCount = this.participants.size();
        this.roundCount = playerCount - 1;
    }

    protected void changeTables() {
        Integer round = 1;
        List<GameImpl> gameInRound = new ArrayList<GameImpl>();
        List<GameImpl> newSchedule = new ArrayList<GameImpl>();
        for (GameImpl game : schedule) {
            if (game.getRound().equals(round)) {
                gameInRound.add(game);
            } else {
                Collections.shuffle(gameInRound, new Random(group.getId().longValue()));
                newSchedule.addAll(gameInRound);
                gameInRound.clear();
                gameInRound.add(game);
                round++;
            }
        }
        newSchedule.addAll(gameInRound);
        schedule = new ArrayList<GameImpl>(newSchedule);
    }

    protected void createSchedule() {
        schedule = new ArrayList<GameImpl>();
        for (int i = 0; i < roundCount; i++) {
            addRoundGames(i % 2 == 1);
            rotate(participants);
        }
    }

    private void rotate(List<Participant> participants) {
        Participant temp = participants.remove(participants.size() - 1);
        participants.add(1, temp);
    }

    private void addRoundGames(boolean rotate) {
        for (int i = 0; i < playerCount / 2; i++) {
            if ((i == 0 && rotate) || (i % 2 == 1)) {
                addGameToSchedule(participants.get(playerCount - i - 1), participants.get(i));
            } else {
                addGameToSchedule(participants.get(i), participants.get(playerCount - i - 1));
            }
        }
    }

}
