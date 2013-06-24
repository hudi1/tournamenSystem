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
import org.toursys.repository.model.Tournament;

import com.itextpdf.tool.xml.exceptions.NotImplementedException;

public class AdvancedRoundRobinSchedule implements RoundRobinSchedule {

	@SpringBean(name = "tournamentService")
	private TournamentService tournamentService;

	private Groups group;
	private Tournament tournament;
	private List<PlayerResult> finalPlayers;
	private LinkedList<List<PlayerResult>> playerByGroup;
	private List<GameImpl> schedule;
	private int playerCount;

	public AdvancedRoundRobinSchedule(final Tournament tournament,
			final Groups group, final List<PlayerResult> finalPlayers,
			LinkedList<List<PlayerResult>> playerByGroup) {
		this.tournament = tournament;
		this.group = group;
		this.finalPlayers = finalPlayers;
		this.playerByGroup = playerByGroup;
		System.out.println(playerByGroup.isEmpty());
		for (List<PlayerResult> list : playerByGroup) {
			for (PlayerResult playerResult : list) {
				System.out.println(playerResult);
			}
		}
		
		
	}

	public void createSchedule() {
		schedule = new ArrayList<GameImpl>();
		if (playerByGroup.isEmpty()) {
			return;
		}
		checkPlayerCount();

		int groupCount = playerByGroup.size() - 1;

		for (int i = 0; i < groupCount; i++) {
			addGroupRound();
			rotatePlayerByGroup();
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

		for (int i = 0; i < playerByGroup.size() - 1; i++) {
			if (playerByGroup.get(i).size() != playerByGroup.get(i + 1).size()) {
				throw new TournamentException(
						"Count of players in groups where is counted previous result is not same");
			}
		}
		playerCount = playerByGroup.get(0).size();
	}

	private void addGroupRound() {
		if (playerByGroup.size() % 2 == 0) {
			List<Round> rounds = new ArrayList<Round>();
			for (int i = 0; i < playerByGroup.size() / 2; i++) {
				rounds.add(new Round(playerByGroup.get(i), playerByGroup
						.get(playerByGroup.size() - i - 1)));
			}
			for (int i = 0; i < playerCount; i++) {
				for (Round round : rounds) {
					round.addNextGames();
				}
			}
		} else {
			throw new NotImplementedException("");
		}
	}

	private void rotatePlayerByGroup() {
		List<PlayerResult> lastGroupPlayer = playerByGroup.removeLast();
		playerByGroup.add(1, lastGroupPlayer);
	}

	public class Round {

		private List<PlayerResult> playerResut1;
		private List<PlayerResult> playerResut2;

		public Round(List<PlayerResult> playerResut1,
				List<PlayerResult> playerResult2) {
			this.playerResut1 = playerResut1;
			this.playerResut2 = playerResult2;
		}

		public void addNextGames() {
			for (int i = 0; i < playerResut2.size(); i++) {
				addNewGame(playerResut1.get(i), playerResut2.get(i));
			}
			Collections.rotate(playerResut2, 1);
		}

		private void addNewGame(PlayerResult homePlayer, PlayerResult awayPlayer) {
			if (homePlayer.getId() != null && awayPlayer.getId() != null) {
				for (Game game : homePlayer.getGames()) {
					if (game.getAwayPlayerResult().equals(awayPlayer)) {
						GameImpl gameImpl = new GameImpl(game);
						gameImpl.setHockey(schedule.size()
								% group.getNumberOfHockey()
								+ group.getIndexOfFirstHockey());
						gameImpl.setRound(schedule.size()
								/ group.getNumberOfHockey() + 1);
						schedule.add(gameImpl);
						break;
					}
				}
			}
		}
	}
}
