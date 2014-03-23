package org.toursys.processor.schedule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.GameImpl;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.Player;

@RunWith(value = Parameterized.class)
public class AdvancedRoundScheduleTest extends AbstractScheduleTest {

    private Groups group;
    private RoundRobinSchedule schedule;
    private int playerCount;
    private int hockeyCount;
    private int basicGroupsCount;

    private static final int MAX_PROMOTING_PLAYERS = 6;
    private static final int MAX_BASIC_GROUPS_COUNT = 4;

    @Before
    public void setup() {
        group = new Groups();
        group.setNumberOfHockey(hockeyCount);
        group.setIndexOfFirstHockey(1);

        LinkedList<List<Participant>> playerPerBasicGroup = new LinkedList<List<Participant>>();

        for (int i = 0; i < basicGroupsCount; i++) {
            playerPerBasicGroup.add(new LinkedList<Participant>());
            for (int j = 0; j < playerCount; j++) {
                int temp = j + 1;
                playerPerBasicGroup.get(i).add(
                        new Participant()._setPlayer(new Player()._setName(i + 1 + "_" + temp)
                                ._setSurname(i + 1 + "_" + temp)._setId(((i) * 6) + j + 1)));
            }
        }

        List<Participant> participants = new ArrayList<Participant>();

        for (List<Participant> list : playerPerBasicGroup) {
            participants.addAll(list);
        }

        for (Participant participant1 : participants) {
            for (Participant participant2 : participants) {
                participant1.getGames().add(new Game(participant1, participant2));
            }
        }

        schedule = new AdvancedRoundRobinSchedule(group, playerPerBasicGroup);
    }

    public AdvancedRoundScheduleTest(int playerCount, int hockeyCount, int basicGroupsCount) {
        this.playerCount = playerCount;
        this.hockeyCount = hockeyCount;
        this.basicGroupsCount = basicGroupsCount;
    }

    @Parameters
    public static List<Object[]> data() {
        final List<Object[]> parametry = new ArrayList<Object[]>();
        for (int i = 4; i <= MAX_PROMOTING_PLAYERS; i++) {
            for (int k = 1; k <= MAX_BASIC_GROUPS_COUNT; k++) {
                parametry.add(new Object[] { i, i * k / 2, k });

            }
        }
        return parametry;
    }

    @Test
    public void test() throws Exception {
        System.out.println("Start Players: " + playerCount + " HockeyCount: " + hockeyCount + " Groups: "
                + basicGroupsCount);

        List<GameImpl> games = schedule.getSchedule();
        Set<Game> gamesInSchedule = new HashSet<Game>();

        Set<Participant> players = new HashSet<Participant>();
        int round = 1;
        for (GameImpl gameImpl : games) {
            checkConstainsInSchedule(gameImpl, gamesInSchedule);
            if (gameImpl.getRound() == round) {
                checkConstainsInRound(gameImpl.getAwayParticipant(), players);
                checkConstainsInRound(gameImpl.getHomeParticipant(), players);
            } else {
                players.clear();
                round++;
                checkConstainsInRound(gameImpl.getAwayParticipant(), players);
                checkConstainsInRound(gameImpl.getHomeParticipant(), players);
            }
        }
    }
}