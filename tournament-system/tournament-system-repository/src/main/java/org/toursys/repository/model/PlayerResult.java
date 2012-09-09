package org.toursys.repository.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.sqlproc.engine.annotation.Pojo;

@Pojo
public class PlayerResult implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    private long playerResultId;

    private Integer points;

    private Integer rank;

    private Table tournamentTable;

    private Player player;

    private String score;

    private List<Game> games;

    public PlayerResult() {
    }

    public long getPlayerResultId() {
        return playerResultId;
    }

    /*
     * public PlayerResult(PlayerResult playerResult) { this.playerResultId = playerResult.getPlayerResultId();
     * this.points = playerResult.getPoints(); this.rank = playerResult.getRank(); this.tournamentTable =
     * playerResult.getTournamentTable(); this.player = playerResult.getPlayer(); this.score = playerResult.getScore();
     * this.games this.games = playerResult.getGames(); }
     */

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

    public Table getTournamentTable() {
        return tournamentTable;
    }

    public void setTournamentTable(Table tournamentTable) {
        this.tournamentTable = tournamentTable;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getScore() {
        return score;
    }

    public Result getResultScore() {
        Result result = new Result();
        if (score != null) {
            result.setLeftSide(Integer.parseInt(score.split(":")[0]));
            result.setRightSide(Integer.parseInt(score.split(":")[1]));
        }
        return result;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public List<Game> getGames() {
        if (games == null)
            games = new ArrayList<Game>();
        return games;
    }

    public Game getGames(int index) {
        return games.get(index);
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    @Override
    public PlayerResult clone() {
        PlayerResult playerResult = new PlayerResult();
        playerResult.getGames().addAll(getGames());
        playerResult.setPlayer(getPlayer());
        playerResult.setPlayerResultId(getPlayerResultId());
        playerResult.setPoints(getPoints());
        playerResult.setRank(getRank());
        playerResult.setScore(getScore());
        playerResult.setTournamentTable(getTournamentTable());
        return playerResult;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (playerResultId ^ (playerResultId >>> 32));
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
        PlayerResult other = (PlayerResult) obj;
        if (playerResultId != other.playerResultId)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PlayerResult [playerResultId=" + playerResultId + ", player=" + player + "]";
    }

}
