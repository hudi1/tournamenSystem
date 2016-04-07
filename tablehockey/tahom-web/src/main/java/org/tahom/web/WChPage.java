package org.tahom.web;

import java.util.ArrayList;
import java.util.List;

import org.tahom.processor.service.event.season.WCh2017Season;
import org.tahom.repository.model.event.EventSeason;

public class WChPage extends EventPage<WChPage> {

	private static final long serialVersionUID = 1L;

	private static List<EventSeason> WCH_SEASONS = new ArrayList<EventSeason>();

	static {
		WCH_SEASONS.add(WCh2017Season.getInstance());
	}

	@Override
	public List<EventSeason> getEvents() {
		return WCH_SEASONS;
	}

	@Override
	protected Class<WChPage> getEventPage() {
		return WChPage.class;
	}

}