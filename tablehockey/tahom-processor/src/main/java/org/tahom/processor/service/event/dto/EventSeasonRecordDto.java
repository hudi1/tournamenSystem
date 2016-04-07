package org.tahom.processor.service.event.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EventSeasonRecordDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<EventDetailRecordDto> nationTournamentDetails;
	private List<EventDetailRecordDto> otherTournamentDetails;
	private EventDetailRecordDto nationalChampionship;

	public List<EventDetailRecordDto> getNationTournamentDetails() {
		if (nationTournamentDetails == null) {
			nationTournamentDetails = new ArrayList<EventDetailRecordDto>();
		}
		return nationTournamentDetails;
	}

	public List<EventDetailRecordDto> getOtherTournamentDetails() {
		if (otherTournamentDetails == null) {
			otherTournamentDetails = new ArrayList<EventDetailRecordDto>();
		}
		return otherTournamentDetails;
	}

	public EventDetailRecordDto getNationalChampionship() {
		return nationalChampionship;
	}

	public void setNationalChampionship(EventDetailRecordDto nationalChampionship) {
		this.nationalChampionship = nationalChampionship;
	}

	@Override
	public String toString() {
		return "EventSeasonRecordDto [nationTournamentDetails=" + nationTournamentDetails + ", otherTournamentDetails="
		        + otherTournamentDetails + ", nationalChampionship=" + nationalChampionship + "]";
	}

}
