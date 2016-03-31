package org.tahom.processor.schedule;

import java.util.List;

import org.tahom.processor.service.game.GameModel;
import org.tahom.processor.service.game.dto.GameDto;
import org.tahom.repository.model.Game;
import org.tahom.repository.model.Groups;
import org.tahom.repository.model.Participant;

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

					schedule.add(gameDto);
					break;
				}
			}
		}
	}

	protected void addEmptyGameToSchedule() {
		GameDto game = createGameDto(null);
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
		GameDto gameDto = GameModel.createGameDto(game);
		gameDto.setRound(getActualRound());
		gameDto.setHockey(getActualHockey());
		return gameDto;
	}

}
