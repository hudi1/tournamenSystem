package org.tahom.processor.schedule;

import java.util.Set;

import org.junit.Assert;
import org.tahom.processor.service.game.dto.GameDto;
import org.tahom.repository.model.Game;
import org.tahom.repository.model.Participant;

public abstract class AbstractScheduleTest {

	protected void checkConstainsInRound(Integer participantId, Set<Participant> players) {
		if (participantId != null) {
			boolean contains = !players.add(new Participant()._setId(participantId));
			if (contains) {
				Assert.fail("Player's id: " + participantId + " is already in this round.");
			}
		}
	}

	protected void checkConstainsInSchedule(GameDto gameDto, Set<Game> gamesInSchedule) {
		if (gameDto != null && (gameDto.getAwayParticipantId() != null || gameDto.getHomeParticipantId() != null)) {
			for (Game game : gamesInSchedule) {
				if ((game.getAwayParticipant().getId().equals(gameDto.getHomeParticipantId()) || game
				        .getAwayParticipant().getId().equals(gameDto.getAwayParticipantId()))
				        && (game.getHomeParticipant().getId().equals(gameDto.getHomeParticipantId()) || game
				                .getHomeParticipant().equals(gameDto.getAwayParticipantId()))) {
					Assert.fail("Game: " + gameDto + " is already in this schedule.");
				}
			}
			gamesInSchedule.add(createGameFromDto(gameDto));
		}
	}

	private Game createGameFromDto(GameDto gameDto) {
		Game game = new Game();
		game.setAwayParticipant(new Participant()._setId(gameDto.getAwayParticipantId()));
		game.setHomeParticipant(new Participant()._setId(gameDto.getHomeParticipantId()));
		game.setId(gameDto.getGameId());
		game.setResult(gameDto.getResult());
		return game;
	}
}