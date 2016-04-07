package org.tahom.web;

import java.util.ArrayList;
import java.util.List;

import org.tahom.processor.service.event.season.CzechCup2016Season;
import org.tahom.processor.service.event.season.SlovakCup2016Season;
import org.tahom.repository.model.event.EventSeason;

public class NationalCupPage extends EventPage<NationalCupPage> {

	private static final long serialVersionUID = 1L;

	private static List<EventSeason> EVENT_SEASONS = new ArrayList<EventSeason>();

	static {
		EVENT_SEASONS.add(SlovakCup2016Season.getInstance());
		EVENT_SEASONS.add(CzechCup2016Season.getInstance());
	}

	@Override
	public List<EventSeason> getEvents() {
		return EVENT_SEASONS;
	}

	@Override
	protected Class<NationalCupPage> getEventPage() {
		return NationalCupPage.class;
	}

}