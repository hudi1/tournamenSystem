package org.tahom.processor.service.game;

import org.tahom.processor.service.game.dto.GameDto;
import org.tahom.repository.model.Game;
import org.tahom.repository.model.GameStatus;
import org.tahom.repository.model.Participant;
import org.tahom.repository.model.Result;

public class GameModel {

	public void changeParticipants(Game game) {
		Participant temp = game.getAwayParticipant();
		game.setAwayParticipant(game.getHomeParticipant());
		game.setHomeParticipant(temp);
	}

	public void assignWinner(GameDto gameDto) {
		int homeWinnerCount = 0;
		int awayWinnerCount = 0;

		if (gameDto.getResult() == null || gameDto.getResult().getResults().isEmpty()) {
			gameDto.setGameStatus(null);
			return;
		}

		for (Result result : gameDto.getResult().getResults()) {
			if (result.getLeftSide() > result.getRightSide()) {
				homeWinnerCount++;
			} else if (result.getLeftSide() < result.getRightSide()) {
				awayWinnerCount++;
			}
		}

		if (homeWinnerCount > awayWinnerCount) {
			gameDto.setGameStatus(GameStatus.WIN);
		} else if (homeWinnerCount < awayWinnerCount) {
			gameDto.setGameStatus(GameStatus.LOSE);
		} else {
			gameDto.setGameStatus(GameStatus.DRAW);
		}

	}

	public Game createGameFromDto(GameDto gameDto) {
		Game game = new Game();
		game.setAwayParticipant(new Participant()._setId(gameDto.getAwayParticipandId()));
		game.setHomeParticipant(new Participant()._setId(gameDto.getHomeParticipantId()));
		game.setId(gameDto.getGameId());
		game.setResult(gameDto.getResult());
		return game;
	}

}
