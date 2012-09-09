package org.toursys.repository.model;

import java.io.Serializable;

public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    private long gameId;

    private PlayerResult playerResult;

    private PlayerResult opponent;

    private Result result;

    private Integer hockey;

    private Integer round;

    public Game() {

    }

    public Game(PlayerResult playerResult, PlayerResult opponent) {
        this.playerResult = playerResult;
        this.opponent = opponent;
        result = new Result();
    }

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (gameId ^ (gameId >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Game other = (Game) obj;
        if (gameId != other.gameId)
            return false;
        return true;
    }

    public Integer getHockey() {
        return hockey;
    }

    public void setHockey(Integer hockey) {
        this.hockey = hockey;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    @Override
    public String toString() {
        return "Game [gameId=" + gameId + ", result=" + result + ", hockey=" + hockey + ", round=" + round + "]";
    }

}
