package org.tahom.processor.service.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.tahom.processor.service.event.dto.EventDetailRecordDto;
import org.tahom.processor.service.event.dto.EventRecordDto;
import org.tahom.processor.service.event.dto.EventSeasonRecordDto;
import org.tahom.processor.service.event.dto.EventTableRecordDto;
import org.tahom.processor.service.event.season.SlovakCup2016Season;
import org.tahom.repository.model.IthfTournament;
import org.tahom.repository.model.Player;
import org.tahom.repository.model.event.EventSeason;
import org.tahom.repository.model.impl.IthfTournamentForm;
import org.tahom.repository.model.impl.Series;

public class EventModel {

	private final String EMPTY_POINTS = "-";

	public EventTableRecordDto map(EventSeason eventSeason, List<Player> players, int playedNationTournament) {
		EventTableRecordDto eventTableRecord = new EventTableRecordDto();
		eventTableRecord.setRecords(getEventRecords(eventSeason, players, playedNationTournament));
		mapEventTableHeader(eventTableRecord, eventSeason);
		return eventTableRecord;
	}

	public IthfTournamentForm getIthfForm(EventSeason eventSeason) {
		IthfTournamentForm form = new IthfTournamentForm();
		form.setStartDate(eventSeason.getStartSeason());
		form.setEndDate(eventSeason.getEndSeason());
		form.setSeries(new Series(eventSeason.getNationalSeriesName()));
		return form;
	}

	private List<EventRecordDto> getEventRecords(EventSeason eventSeason, List<Player> players,
			int playedNationTournament) {
		Collection<String> excludedSeries = eventSeason.getExcludedSeries();
		List<EventRecordDto> eventRecords = new ArrayList<EventRecordDto>();

		for (Player player : players) {
			EventRecordDto eventRecord = new EventRecordDto();
			eventRecord.setPlayerName(player.getName() + " " + player.getSurname());

			for (Map.Entry<String, String> entry : eventSeason.getSeasonsName().entrySet()) {
				eventRecord.getEventSeasonRecords().put(entry.getValue(), new EventSeasonRecordDto());
			}

			// todo optimalizovat select vratane order by
			Collections.sort(player.getIthfTournaments(), new Comparator<IthfTournament>() {
				@Override
				public int compare(IthfTournament o1, IthfTournament o2) {
					return o2.getPoints().compareTo(o1.getPoints());
				};
			});

			int total = 0;
			EventDetailRecordDto eventDetailRecord = null;
			for (IthfTournament ithfTournament : player.getIthfTournaments()) {
				if ((ithfTournament.getSeries() != null
						&& excludedSeries.containsAll(ithfTournament.getSeries().getSeries()))
						|| eventSeason.getSeason(ithfTournament) == null) {
					continue;
				}

				EventSeasonRecordDto eventSeasonRecord = eventRecord.getEventSeasonRecords()
						.get(eventSeason.getSeason(ithfTournament));
				if (isIthfTournamentInSeries(eventSeason.getNationalChampionshipSeriesName(), ithfTournament)) {
					eventDetailRecord = setNationalChampionship(eventSeasonRecord, ithfTournament);
				} else if (isIthfTournamentInSeries(eventSeason.getNationalSeriesName(), ithfTournament)
						|| isSlovakianOpen(ithfTournament, eventSeason)) {
					if (eventRecord.getEventSeasonRecords().get(eventSeason.getSeason(ithfTournament))
							.getNationTournamentDetails().size() >= (eventSeason.getNationalSeriesCount())) {
						continue;
					}

					eventDetailRecord = addEventDetailRecords(eventSeasonRecord.getNationTournamentDetails(),
							ithfTournament, eventSeason, false);
				} else {
					if (eventRecord.getEventSeasonRecords().get(eventSeason.getSeason(ithfTournament))
							.getOtherTournamentDetails().size() >= eventSeason.getOtherSeriesCount()) {
						continue;
					}
					eventDetailRecord = addEventDetailRecords(eventSeasonRecord.getOtherTournamentDetails(),
							ithfTournament, eventSeason, true);
				}

				if (eventDetailRecord != null) {
					total += Integer.parseInt(eventDetailRecord.getPoints());
				}
			}

			for (Entry<String, EventSeasonRecordDto> record : eventRecord.getEventSeasonRecords().entrySet()) {
				setEmptyNationalChampionship(eventSeason, record.getValue());
				addEmptyEventDetailRecords(eventSeason.getNationalSeriesCount(),
						record.getValue().getNationTournamentDetails());
				addEmptyEventDetailRecords(eventSeason.getOtherSeriesCount(),
						record.getValue().getOtherTournamentDetails());

				sortEventDetailRecords(record.getValue().getOtherTournamentDetails());
				sortEventDetailRecords(record.getValue().getNationTournamentDetails());
				total -= getExcludeTournamentDetailPoints(record.getValue().getNationTournamentDetails(), eventSeason,
						playedNationTournament);
			}

			eventRecord.setTotalPoints(total);
			if (eventRecord.getTotalPoints() > 0) {
				eventRecords.add(eventRecord);
			}
		}

		sortEventRecords(eventRecords);
		return eventRecords;
	}

	private EventDetailRecordDto addEventDetailRecords(List<EventDetailRecordDto> eventDetailRecords,
			IthfTournament ithfTournament, EventSeason eventSeason, boolean ignoreScoring) {
		Integer points;

		if (!eventSeason.getScoring().isEmpty() && ithfTournament.getRank() != null && !ignoreScoring) {
			points = eventSeason.getScoring().get(ithfTournament.getRank());
		} else {
			points = ithfTournament.getPoints();
		}

		if (eventSeason.getExtraBonusTournamentName() != null
				&& eventSeason.getExtraBonusTournamentName().equals(ithfTournament.getName())) {
			points += eventSeason.getExtraBonusPoints();
		}

		EventDetailRecordDto eventDetailRecord = new EventDetailRecordDto(points.toString(), ithfTournament.getName());
		eventDetailRecords.add(eventDetailRecord);
		return eventDetailRecord;
	}

	private boolean isSlovakianOpen(IthfTournament ithfTournament, EventSeason eventSeason) {
		if (ithfTournament.getName().equals("Slovakian Open 2016") && eventSeason instanceof SlovakCup2016Season) {
			return true;
		}
		return false;
	}

	private EventDetailRecordDto setNationalChampionship(EventSeasonRecordDto eventSeasonRecord,
			IthfTournament ithfTournament) {
		EventDetailRecordDto eventDetailRecord = new EventDetailRecordDto(ithfTournament.getPoints().toString(),
				ithfTournament.getName());
		eventSeasonRecord.setNationalChampionship(eventDetailRecord);
		return eventDetailRecord;
	}

	private boolean isIthfTournamentInSeries(String seriesName, IthfTournament ithfTournament) {
		if (ithfTournament.getSeries() != null && ithfTournament.getSeries().getSeries().contains(seriesName)) {
			return true;
		}
		return false;
	}

	private void setEmptyNationalChampionship(EventSeason eventSeason, EventSeasonRecordDto eventSeasonRecordDto) {
		if (eventSeasonRecordDto.getNationalChampionship() == null
				&& eventSeason.getNationalChampionshipSeriesName() != null) {
			eventSeasonRecordDto.setNationalChampionship(new EventDetailRecordDto(EMPTY_POINTS, null));
		}
	}

	private void addEmptyEventDetailRecords(int recordsCount, List<EventDetailRecordDto> eventDetailRecords) {
		int size = eventDetailRecords.size();
		while (size < recordsCount) {
			eventDetailRecords.add(new EventDetailRecordDto(EMPTY_POINTS, null));
			size++;
		}
	}

	private int getExcludeTournamentDetailPoints(List<EventDetailRecordDto> eventDetailsRecord, EventSeason eventSeason,
			int playedNationTournament) {
		int points = 0;
		if (!eventDetailsRecord.isEmpty() && eventDetailsRecord.size() > playedNationTournament) {
			for (int i = 0; i < eventSeason.getNationalSeriesExcludedCount(); i++) {
				EventDetailRecordDto tournamentDetails = eventDetailsRecord.get(playedNationTournament - 1 - i);
				tournamentDetails.setExcluded(true);
				if (!tournamentDetails.getPoints().equals(EMPTY_POINTS)) {
					points += Integer.valueOf(tournamentDetails.getPoints());
				}
			}
		}
		return points;
	}

	private void sortEventDetailRecords(List<EventDetailRecordDto> eventDetailsRecord) {
		Collections.sort(eventDetailsRecord, new Comparator<EventDetailRecordDto>() {
			@Override
			public int compare(EventDetailRecordDto o1, EventDetailRecordDto o2) {
				Integer points1 = o1.getPoints().equals(EMPTY_POINTS) ? 0 : Integer.valueOf(o1.getPoints());
				Integer points2 = o2.getPoints().equals(EMPTY_POINTS) ? 0 : Integer.valueOf(o2.getPoints());
				return points2.compareTo(points1);
			}
		});
	}

	private void sortEventRecords(List<EventRecordDto> eventRecords) {

		// TODO deeper sort when same points based on better tournament
		Collections.sort(eventRecords, new Comparator<EventRecordDto>() {
			@Override
			public int compare(EventRecordDto o1, EventRecordDto o2) {
				return o2.getTotalPoints().compareTo(o1.getTotalPoints());
			}
		});
	}

	private void mapEventTableHeader(EventTableRecordDto eventTableRecord, EventSeason eventSeason) {
		if (!eventTableRecord.getRecords().isEmpty()) {
			EventRecordDto record = eventTableRecord.getRecords().get(0);
			for (Entry<String, EventSeasonRecordDto> entry : record.getEventSeasonRecords().entrySet()) {
				eventTableRecord.getEventSeasonHeaders().add(entry.getKey());
				int name = 0;
				for (int i = 0; i < entry.getValue().getNationTournamentDetails().size(); i++) {
					name = i + 1;
					eventTableRecord.getEventTournamentHeaders().add(name + "");
				}
				for (int i = 0; i < entry.getValue().getOtherTournamentDetails().size(); i++) {
					name = i + 1;
					eventTableRecord.getEventTournamentHeaders().add(name + "");
				}

				if (entry.getValue().getNationalChampionship() != null
						&& eventSeason.getNationalChampionshipSeriesIndexName() != null) {
					eventTableRecord.getEventTournamentHeaders()
							.add(eventSeason.getNationalChampionshipSeriesIndexName());
				}
			}
		}
	}
}
