package org.toursys.repository.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.sqlproc.engine.annotation.Pojo;

@Pojo
public class Season implements Serializable {

	private static final long serialVersionUID = 1L;

	private long idSeason;

	private String name;

	private List<Tournament> tournaments = new ArrayList<Tournament>();

	public Season() {
	}

	public List<Tournament> getTournaments() {
		return tournaments;
	}

	public void setTournaments(List<Tournament> tournaments) {
		this.tournaments = tournaments;
	}

	public long getIdSeason() {
		return idSeason;
	}

	public void setIdSeason(long idSeason) {
		this.idSeason = idSeason;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
