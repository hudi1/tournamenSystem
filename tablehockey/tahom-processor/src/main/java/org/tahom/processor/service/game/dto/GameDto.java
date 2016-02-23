package org.tahom.processor.service.game.dto;

import java.io.Serializable;

import org.tahom.repository.model.GameStatus;
import org.tahom.repository.model.Results;

public class GameDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String playerName;

	private String opponentName;

	private GameStatus gameStatus;

	private Results result;

	private Integer round;

	private Integer hockey;

	private Integer gameId;

	private Integer homeParticipantId;

	private Integer awayParticipantId;

	private String score;

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getOpponentName() {
		return opponentName;
	}

	public void setOpponentName(String opponentName) {
		this.opponentName = opponentName;
	}

	public GameStatus getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}

	public Results getResult() {
		if (result == null) {
			result = new Results();
		}
		return result;
	}

	public void setResult(Results result) {
		this.result = result;
	}

	public Integer getRound() {
		return round;
	}

	public void setRound(Integer round) {
		this.round = round;
	}

	public Integer getHockey() {
		return hockey;
	}

	public void setHockey(Integer hockey) {
		this.hockey = hockey;
	}

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	public Integer getHomeParticipantId() {
		return homeParticipantId;
	}

	public void setHomeParticipantId(Integer homeParticipantId) {
		this.homeParticipantId = homeParticipantId;
	}

	public Integer getAwayParticipantId() {
		return awayParticipantId;
	}

	public void setAwayParticipantId(Integer awayParticipandId) {
		this.awayParticipantId = awayParticipandId;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

}
