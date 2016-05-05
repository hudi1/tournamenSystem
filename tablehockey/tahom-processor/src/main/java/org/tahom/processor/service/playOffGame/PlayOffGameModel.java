package org.tahom.processor.service.playOffGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.tahom.processor.service.game.dto.GameDto;
import org.tahom.processor.service.playOffGame.dto.PlayOffGameDto;
import org.tahom.processor.service.playOffGame.dto.PlayOffGroupDto;
import org.tahom.processor.util.MessageProvider;
import org.tahom.processor.util.TournamentUtil;
import org.tahom.repository.model.GameStatus;
import org.tahom.repository.model.Groups;
import org.tahom.repository.model.Participant;
import org.tahom.repository.model.PlayOffGame;
import org.tahom.repository.model.Player;
import org.tahom.repository.model.impl.Result;

public class PlayOffGameModel {

	private static final MessageProvider messageProvider = new MessageProvider();

	public PlayOffGame createPlayOffGame(Participant homeParticipant, Participant awayParticipant, Groups group,
	        int position) {
		PlayOffGame playOffGame = new PlayOffGame(group, position);
		playOffGame.setHomeParticipant(homeParticipant);
		playOffGame.setAwayParticipant(awayParticipant);
		return playOffGame;
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
		gameDto.setScore(homeWinnerCount + " : " + awayWinnerCount);
	}

	public PlayOffGame createPlayOffGameFromDto(PlayOffGameDto gameDto) {
		PlayOffGame game = new PlayOffGame();
		game.setAwayParticipant(new Participant()._setId(gameDto.getAwayParticipantId()));
		game.setHomeParticipant(new Participant()._setId(gameDto.getHomeParticipantId()));
		game.setId(gameDto.getGameId());
		game.setResult(gameDto.getResult());
		game.setStatus(gameDto.getGameStatus());
		return game;
	}

	protected List<PlayOffGameDto> createPlayOffGamesDto(List<PlayOffGame> playOffGames, Groups group, Locale locale) {
		List<PlayOffGameDto> gamesDto = new ArrayList<PlayOffGameDto>();

		for (PlayOffGame playOffGame : playOffGames) {
			gamesDto.add(getPlayOffGameDto(playOffGame, group, locale, playOffGames.size()));
		}
		return gamesDto;
	}

	public PlayOffGameDto getPlayOffGameDto(PlayOffGame game, Groups group, Locale locale, int playOfGameCount) {
		PlayOffGameDto gameDto = new PlayOffGameDto();
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
		gameDto.setRoundName(TournamentUtil.getRoundName(locale, group, game.getPosition(), playOfGameCount));
		assignWinner(gameDto);
		return gameDto;
	}

	private String getPlayerName(Player player) {
		if (player != null) {
			return player.getName() + " " + player.getSurname();
		}
		return "";
	}

	public PlayOffGroupDto createPlayOffGroupDto(Groups group) {
		PlayOffGroupDto playOffGroupDto = new PlayOffGroupDto();
		playOffGroupDto.setName(group.getName());
		return playOffGroupDto;
	}

}
