package org.tahom.processor.schedule;

import java.util.List;

import org.tahom.processor.service.game.dto.GameDto;
import org.tahom.repository.model.Game;
import org.tahom.repository.model.Groups;
import org.tahom.repository.model.Participant;
import org.tahom.repository.model.Player;

public abstract class RoundRobinSchedule {

	protected List<GameDto> schedule;
	protected Groups group;

	public RoundRobinSchedule(Groups group) {
		this.group = group;
	}

	public List<GameDto> getSchedule() {
		if (schedule == null) {
			createSchedule();
		}
		return schedule;
	}

	protected void addGameToSchedule(Participant homePlayer, Participant awayPlayer) {
		if (!homePlayer.isTemp() && !awayPlayer.isTemp()) {
			for (Game game : homePlayer.getGames()) {
				if (game.getAwayParticipant().equals(awayPlayer)) {
					game._setHomeParticipant(homePlayer)._setAwayParticipant(awayPlayer);
					GameDto gameDto = createGameDto(game);
					gameDto.setRound(getActualRound());
					gameDto.setHockey(getActualHockey());
					schedule.add(gameDto);
					break;
				}
			}
		}
	}

	protected void addEmptyGameToSchedule() {
		GameDto game = new GameDto();
		game.setRound(getActualRound());
		game.setHockey(getActualHockey());
		schedule.add(game);
	}

	protected Integer getActualRound() {
		return schedule.size() / group.getNumberOfHockey() + 1;
	}

	protected Integer getActualHockey() {
		return schedule.size() % group.getNumberOfHockey() + group.getIndexOfFirstHockey();
	}

	protected abstract void createSchedule();

	protected GameDto createGameDto(Game game) {
		GameDto gameDto = new GameDto();
		if (game.getHomeParticipant() != null) {
			gameDto.setPlayerName(getPlayerName(game.getHomeParticipant().getPlayer()));
			gameDto.setHomeParticipantId(game.getHomeParticipant().getId());
		}
		if (game.getAwayParticipant() != null) {
			gameDto.setOpponentName(getPlayerName(game.getAwayParticipant().getPlayer()));
			gameDto.setAwayParticipantId(game.getAwayParticipant().getId());
		}
		gameDto.setGameId(game.getId());
		gameDto.setResult(game.getResult());
		return gameDto;
	}

	private String getPlayerName(Player player) {
		if (player != null) {
			return player.getName() + " " + player.getSurname();
		}
		return "";
	}

}
