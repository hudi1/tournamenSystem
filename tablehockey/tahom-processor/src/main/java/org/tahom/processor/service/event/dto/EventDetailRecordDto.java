package org.tahom.processor.service.event.dto;

import java.io.Serializable;

public class EventDetailRecordDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String points;
	private String tournament;
	private boolean excluded;

	public EventDetailRecordDto() {
	}

	public EventDetailRecordDto(String points, String tournament) {
		this.points = points;
		this.tournament = tournament;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getTournament() {
		return tournament;
	}

	public void setTournament(String tournament) {
		this.tournament = tournament;
	}

	public boolean isExcluded() {
		return excluded;
	}

	public void setExcluded(boolean excluded) {
		this.excluded = excluded;
	}

	@Override
	public String toString() {
		return "EventTournamentDetailsRecord [points=" + points + ", tournament=" + tournament + "]";
	}

}
