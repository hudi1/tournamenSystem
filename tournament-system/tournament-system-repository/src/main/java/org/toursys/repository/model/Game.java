package org.toursys.repository.model;

import java.io.Serializable;

public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    private long gameId;

    private long playerResultId;

    private long opponentId;

    private Result result;

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public long getPlayerResultId() {
        return playerResultId;
    }

    public void setPlayerResultId(long playerResultId) {
        this.playerResultId = playerResultId;
    }

    public long getOpponentId() {
        return opponentId;
    }

    public void setOpponentId(long opponentId) {
        this.opponentId = opponentId;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

}
