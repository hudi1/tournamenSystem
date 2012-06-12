package org.toursys.repository.model;

import java.io.Serializable;

import org.sqlproc.engine.annotation.Pojo;

@Pojo
public class Tournament implements Serializable {

	private static final long serialVersionUID = 1L;

	private long tournamentId;

	private String name;

	private Season season;

	public String getName() {
		return name;
	}

	public Tournament() {
	}

	public long getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(long tournamentId) {
		this.tournamentId = tournamentId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season seasonId) {
		this.season = seasonId;
	}

}
