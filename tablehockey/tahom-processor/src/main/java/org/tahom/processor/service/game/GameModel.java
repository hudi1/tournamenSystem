package org.tahom.processor.service.game;

import org.tahom.processor.service.game.dto.GameDto;
import org.tahom.repository.model.Game;
import org.tahom.repository.model.GameStatus;
import org.tahom.repository.model.Participant;
import org.tahom.repository.model.Player;
import org.tahom.repository.model.Result;
import org.tahom.repository.model.Results;

public class GameModel {

	public void changeParticipants(Game game) {
		Participant temp = game.getAwayParticipant();
		game.setAwayParticipant(game.getHomeParticipant());
		game.setHomeParticipant(temp);
		if (GameStatus.WIN.equals(game.getStatus())) {
			game.setStatus(GameStatus.LOSE);
		} else if (GameStatus.LOSE.equals(game.getStatus())) {
			game.setStatus(GameStatus.WIN);
		}
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
		game.setAwayParticipant(new Participant()._setId(gameDto.getAwayParticipantId()));
		game.setHomeParticipant(new Participant()._setId(gameDto.getHomeParticipantId()));
		game.setId(gameDto.getGameId());
		game.setResult(gameDto.getResult());
		game.setStatus(gameDto.getGameStatus());
		return game;
	}

	public GameDto createTempResultGame() {
		GameDto gameDto = new GameDto();
		gameDto.setResult(new Results(true));
		return gameDto;
	}

	public static GameDto createGameDto(Game game) {
		GameDto gameDto = new GameDto();
		if (game != null) {
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
			gameDto.setGameStatus(game.getStatus());
		}
		return gameDto;

	}

	private static String getPlayerName(Player player) {
		if (player != null) {
			return player.getName() + " " + player.getSurname();
		}
		return "";
	}

}
