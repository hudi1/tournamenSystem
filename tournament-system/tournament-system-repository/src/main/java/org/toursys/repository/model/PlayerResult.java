package org.toursys.repository.model;

import java.io.Serializable;

import org.sqlproc.engine.annotation.Pojo;

@Pojo
public class PlayerResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private long playerResultId;

	private Integer points;

	private Integer rank;

	private Table table;

	private Player player;

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

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
