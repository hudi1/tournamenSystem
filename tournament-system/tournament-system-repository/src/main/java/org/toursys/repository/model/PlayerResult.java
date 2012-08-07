package org.toursys.repository.model;

import java.io.Serializable;

import org.sqlproc.engine.annotation.Pojo;

@Pojo
public class PlayerResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private long playerResultId;

    private Integer points;

    private Integer rank;

    private long tournamentTableId;

    private long playerId;

    private String score;

    public PlayerResult() {
    }

    public long getPlayerResultId() {
        return playerResultId;
    }

    public void setPlayerResultId(long playerResultId) {
        this.playerResultId = playerResultId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public long getTournamentTableId() {
        return tournamentTableId;
    }

    public void setTournamentTableId(long tournamentTableId) {
        this.tournamentTableId = tournamentTableId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

}
