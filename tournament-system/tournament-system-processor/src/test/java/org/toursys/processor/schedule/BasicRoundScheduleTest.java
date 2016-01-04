package org.toursys.processor.schedule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
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
public class BasicRoundScheduleTest extends AbstractScheduleTest {

	private Groups group;
	private List<Participant> participants;
	private RoundRobinSchedule schedule;
	private int playerCount;
	private int hockeyCount;

	private static final int MAX_PLAYER_COUNT = 40;

	@Before
	public void setup() {
		group = new Groups();
		group.setNumberOfHockey(hockeyCount);
		group.setIndexOfFirstHockey(1);

		participants = new ArrayList<Participant>();

		for (int i = 0; i < playerCount; i++) {
			participants
			        .add(new Participant()._setPlayer(new Player(i + 1 + "", i + 1 + "", null, null))._setId(i + 1));
		}

		for (Participant participant1 : participants) {
			for (Participant participant2 : participants) {
				participant1.getGames().add(
				        new Game()._setHomeParticipant(participant1)._setAwayParticipant(participant2));
			}
		}

		schedule = new BasicRoundRobinSchedule(group, participants);

	}

	public BasicRoundScheduleTest(int playerCount, int hockeyCount) {
		this.playerCount = playerCount;
		this.hockeyCount = hockeyCount;
	}

	@Parameters
	public static List<Object[]> data() {
		final List<Object[]> parametry = new ArrayList<Object[]>();
		for (int i = 2; i <= MAX_PLAYER_COUNT; i++) {
			parametry.add(new Object[] { i, i / 2 });
		}
		return parametry;
	}

	@Test
	public void test() throws Exception {
		List<GameImpl> games = schedule.getSchedule();

		Assert.assertFalse(games.isEmpty());

		Assert.assertEquals(games.size(), playerCount * hockeyCount);

		Assert.assertEquals(games.get(games.size() - 1).getRound().intValue(), ((games.size() / hockeyCount)));

		Set<Participant> playersInRound = new HashSet<Participant>();
		Set<Game> gamesInSchedule = new HashSet<Game>();

		int round = 1;
		for (GameImpl gameImpl : games) {
			checkConstainsInSchedule(gameImpl, gamesInSchedule);
			if (gameImpl.getRound() == round) {
				checkConstainsInRound(gameImpl.getAwayParticipant(), playersInRound);
				checkConstainsInRound(gameImpl.getHomeParticipant(), playersInRound);
			} else {
				playersInRound.clear();
				round++;
				checkConstainsInRound(gameImpl.getAwayParticipant(), playersInRound);
				checkConstainsInRound(gameImpl.getHomeParticipant(), playersInRound);
			}
		}
	}
}