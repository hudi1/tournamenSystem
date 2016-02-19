package org.tahom.processor.schedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.tahom.processor.service.game.dto.GameDto;
import org.tahom.repository.model.Game;
import org.tahom.repository.model.Groups;
import org.tahom.repository.model.Participant;

public class BasicLessHockeyRoundRobinSchedule extends RoundRobinSchedule {

	private int playerCount;
	private int roundCount;
	private List<Participant> evenRoundParticipants;
	private List<Participant> oddRoundParticipants;
	private Map<Integer, List<GameDto>> scheduleByRound;
	private Integer actualRound;

	public BasicLessHockeyRoundRobinSchedule(Groups group, List<Participant> participants) {
		super(group);

		if (participants.size() < 2) {
			throw new IllegalArgumentException("Unable to create schedule");
		}
		this.evenRoundParticipants = new ArrayList<Participant>(participants);
		if (this.evenRoundParticipants.size() % 2 == 1) {
			this.evenRoundParticipants.add(new Participant()._setTemp(true));
		}
		this.roundCount = participants.size();
		this.oddRoundParticipants = createOddRoundParticipants();
		this.playerCount = this.evenRoundParticipants.size();
		this.roundCount = playerCount - 1;
		this.scheduleByRound = new TreeMap<Integer, List<GameDto>>();
		this.actualRound = 1;
	}

	private List<Participant> createOddRoundParticipants() {
		List<Participant> oddRoundParticipants = new ArrayList<Participant>(evenRoundParticipants);
		Participant temp = oddRoundParticipants.remove(oddRoundParticipants.size() - 1);
		oddRoundParticipants.add((evenRoundParticipants.size() / 2) + 1, temp);
		Collections.rotate(oddRoundParticipants, -1);
		return oddRoundParticipants;
	}

	protected void createSchedule() {
		for (int i = 0; i < roundCount; i++) {
			if (i % 2 == 0) {
				addEvenRoundGames();
				rotateEven();
			} else {
				addOddRoundGames();
				rotateOdd();
			}
		}
		schedule = new ArrayList<GameDto>();
		for (List<GameDto> games : scheduleByRound.values()) {
			schedule.addAll(games);
		}
	}

	private void rotateEven() {
		Participant temp = evenRoundParticipants.remove(0);
		evenRoundParticipants.add(evenRoundParticipants.size() - 1, temp);
	}

	private void rotateOdd() {
		Collections.rotate(oddRoundParticipants, -1);
		Participant temp = oddRoundParticipants.remove(oddRoundParticipants.size() / 2 - 1);
		oddRoundParticipants.add(evenRoundParticipants.size() / 2, temp);
	}

	private void addEvenRoundGames() {
		for (int i = 0; i < playerCount / 2; i++) {
			addGameToSchedule(evenRoundParticipants.get(i), evenRoundParticipants.get(playerCount - i - 1));
		}
	}

	private void addOddRoundGames() {
		for (int i = 0; i < playerCount / 2; i++) {
			addGameToSchedule(oddRoundParticipants.get(playerCount - i - 1), oddRoundParticipants.get(i));
		}
	}

	@Override
	protected void addGameToSchedule(Participant homePlayer, Participant awayPlayer) {
		if (!homePlayer.isTemp() && !awayPlayer.isTemp()) {
			for (Game game : homePlayer.getGames()) {
				if (game.getAwayParticipant().equals(awayPlayer)) {
					game._setHomeParticipant(homePlayer)._setAwayParticipant(awayPlayer);

					GameDto gameDto = createGameDto(game);
					checkAndAddGame(actualRound, gameDto);
					if (scheduleByRound.get(actualRound).size() == group.getNumberOfHockey()) {
						actualRound++;
					}

					break;
				}
			}
		}
	}

	private void checkAndAddGame(Integer round, GameDto game) {
		if (isGamesPlayerInRound(game, round)) {
			checkAndAddGame(round + 1, game);
		} else {
			addGameToRound(round, game);
		}
	}

	private void addGameToRound(Integer round, GameDto game) {
		game.setRound(round);
		if (scheduleByRound.containsKey(round)) {
			game.setHockey(scheduleByRound.get(round).size() + group.getIndexOfFirstHockey());
			scheduleByRound.get(round).add(game);
		} else {
			scheduleByRound.put(round, new LinkedList<GameDto>());
			scheduleByRound.get(round).add(game);
			game.setHockey(0 + group.getIndexOfFirstHockey());
		}
	}

	private boolean isGamesPlayerInRound(GameDto game, Integer round) {

		if (!scheduleByRound.containsKey(round)) {
			return false;
		}

		List<GameDto> games = scheduleByRound.get(round);
		for (GameDto scheduleGameImpl : games) {
			if (game.getAwayParticipandId().equals(scheduleGameImpl.getAwayParticipandId())
			        || game.getAwayParticipandId().equals(scheduleGameImpl.getHomeParticipantId())
			        || game.getHomeParticipantId().equals(scheduleGameImpl.getHomeParticipantId())
			        || game.getHomeParticipantId().equals(scheduleGameImpl.getAwayParticipandId())) {
				return true;
			}
		}

		return false;
	}

}