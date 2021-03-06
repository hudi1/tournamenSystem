package org.tahom.processor.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.tahom.processor.service.game.dto.GameDto;
import org.tahom.repository.model.Game;
import org.tahom.repository.model.Groups;
import org.tahom.repository.model.Participant;
import org.tahom.repository.model.Player;
import org.tahom.repository.model.impl.Surname;

@RunWith(value = Parameterized.class)
public class BasicLessHockeyRoundScheduleTest extends AbstractScheduleTest {

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
			participants.add(new Participant()._setPlayer(
			        new Player(i + 1 + "", new Surname(i + 1 + ""), null))._setId(i + 1));
		}

		for (Participant participant1 : participants) {
			for (Participant participant2 : participants) {
				participant1.getGames().add(
				        new Game()._setHomeParticipant(participant1)._setAwayParticipant(participant2));
			}
		}

		schedule = new BasicLessHockeyRoundRobinSchedule(group, participants);

	}

	public BasicLessHockeyRoundScheduleTest(int playerCount, int hockeyCount) {
		this.playerCount = playerCount;
		this.hockeyCount = hockeyCount;
	}

	@Parameters
	public static List<Object[]> data() {
		final List<Object[]> parametry = new ArrayList<Object[]>();
		for (int i = 4; i <= MAX_PLAYER_COUNT; i++) {
			for (int j = 2; j <= i / 2 - 1; j++) {
				parametry.add(new Object[] { i, j });
			}
		}
		return parametry;
	}

	@Test
	public void test() throws Exception {
		List<GameDto> games = schedule.getSchedule();

		Assert.assertFalse(games.isEmpty());
		Assert.assertEquals(games.size(), playerCount * (playerCount - 1) / 2);

		Assert.assertTrue(games.get(games.size() - 1).getRound().intValue() <= ((games.size() / hockeyCount) + 1));

		Set<Participant> players = new HashSet<Participant>();
		Set<Game> gamesInSchedule = new HashSet<Game>();

		int round = 1;
		for (GameDto gameDto : games) {
			checkConstainsInSchedule(gameDto, gamesInSchedule);
			if (gameDto.getRound() == round) {
				checkConstainsInRound(gameDto.getAwayParticipantId(), players);
				checkConstainsInRound(gameDto.getHomeParticipantId(), players);
			} else {
				players.clear();
				round++;
				checkConstainsInRound(gameDto.getAwayParticipantId(), players);
				checkConstainsInRound(gameDto.getHomeParticipantId(), players);
			}
		}
	}
}