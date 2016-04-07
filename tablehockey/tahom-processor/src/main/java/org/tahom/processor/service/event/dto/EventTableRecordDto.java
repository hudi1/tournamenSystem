package org.tahom.processor.service.event.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EventTableRecordDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<EventRecordDto> records;
	private List<String> eventTournamentHeaders;
	private List<String> eventSeasonHeaders;

	public List<EventRecordDto> getRecords() {
		return records;
	}

	public void setRecords(List<EventRecordDto> records) {
		this.records = records;
	}

	public List<String> getEventTournamentHeaders() {
		if (eventTournamentHeaders == null) {
			eventTournamentHeaders = new ArrayList<String>();
		}
		return eventTournamentHeaders;
	}

	public List<String> getEventSeasonHeaders() {
		if (eventSeasonHeaders == null) {
			eventSeasonHeaders = new ArrayList<String>();
		}
		return eventSeasonHeaders;
	}

}
