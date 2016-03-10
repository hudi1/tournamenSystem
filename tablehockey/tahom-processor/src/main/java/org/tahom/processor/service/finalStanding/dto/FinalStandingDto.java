package org.tahom.processor.service.finalStanding.dto;

import java.io.Serializable;

public class FinalStandingDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private Integer rank;
	private String club;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getClub() {
		return club;
	}

	public void setClub(String club) {
		this.club = club;
	}

}