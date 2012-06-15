package org.toursys.repository.model;

import java.io.Serializable;

public class Game implements Serializable {

	private static final long serialVersionUID = 1L;

	private long gameId;

	private PlayerResult playerResult;

	private PlayerResult opponent;

	private Result result;

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public PlayerResult getPlayerResult() {
		return playerResult;
	}

	public void setPlayerResult(PlayerResult playerResult) {
		this.playerResult = playerResult;
	}

	public PlayerResult getOpponent() {
		return opponent;
	}

	public void setOpponent(PlayerResult opponent) {
		this.opponent = opponent;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
}
