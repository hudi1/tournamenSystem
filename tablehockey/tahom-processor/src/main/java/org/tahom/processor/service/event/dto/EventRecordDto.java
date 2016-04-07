package org.tahom.processor.service.event.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EventRecordDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, EventSeasonRecordDto> eventSeasonRecords;
	private Integer totalPoints;
	private String playerName;

	public EventRecordDto() {
		eventSeasonRecords = new HashMap<String, EventSeasonRecordDto>();
	}

	public Map<String, EventSeasonRecordDto> getEventSeasonRecords() {
		return eventSeasonRecords;
	}

	public List<EventSeasonRecordDto> getSeasonRecordValues() {
		return new ArrayList<EventSeasonRecordDto>(eventSeasonRecords.values());
	}

	public List<EventDetailRecordDto> getSeasonRecords() {
		List<EventDetailRecordDto> records = new LinkedList<EventDetailRecordDto>();
		for (Map.Entry<String, EventSeasonRecordDto> entry : eventSeasonRecords.entrySet()) {
			for (EventDetailRecordDto detail : entry.getValue().getNationTournamentDetails()) {
				records.add(detail);
			}
			for (EventDetailRecordDto detail : entry.getValue().getOtherTournamentDetails()) {
				records.add(detail);
			}
			if (entry.getValue().getNationalChampionship() != null) {
				records.add(entry.getValue().getNationalChampionship());
			}
		}
		return records;
	}

	public Integer getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(Integer totalPoints) {
		this.totalPoints = totalPoints;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	@Override
	public String toString() {
		return "EventRecordDto [eventSeasonRecords=" + eventSeasonRecords + ", totalPoints=" + totalPoints
		        + ", playerName=" + playerName + "]";
	}

}
