package org.tahom.processor.service.event;

import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;
import org.tahom.processor.service.event.dto.EventTableRecordDto;
import org.tahom.processor.service.ithf.IthfService;
import org.tahom.processor.service.player.PlayerService;
import org.tahom.repository.model.Player;
import org.tahom.repository.model.event.EventSeason;
import org.tahom.repository.model.impl.IthfTournamentForm;

public class EventService {

	@Inject
	private PlayerService playerService;

	@Inject
	private EventModel eventModel;

	@Inject
	private IthfService ithfService;

	@Transactional(readOnly = true)
	public EventTableRecordDto getEventRecords(EventSeason eventSeason) {
		List<Player> players = playerService.listPlayers(new Player()._setCountry(eventSeason.getCountry()));
		IthfTournamentForm form = eventModel.getIthfForm(eventSeason);
		int customCount = ithfService.getCustomIthfTournamentCount(form);
		EventTableRecordDto eventTableRecord = eventModel.map(eventSeason, players, customCount);
		return eventTableRecord;
	}

}
