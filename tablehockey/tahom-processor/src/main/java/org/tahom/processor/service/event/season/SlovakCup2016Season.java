package org.tahom.processor.service.event.season;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tahom.processor.util.SlovakScoring;
import org.tahom.repository.model.IthfTournament;
import org.tahom.repository.model.event.EventSeason;

public class SlovakCup2016Season extends EventSeason {

	private static final long serialVersionUID = 1L;

	private static SlovakCup2016Season instance;

	private static final String YEAR_2015 = "2015";
	private static final String YEAR_2016 = "2016";

	public static final Map<String, String> SEASONS_NAME = new HashMap<String, String>();

	static {
		SEASONS_NAME.put(YEAR_2016, YEAR_2015 + "/" + YEAR_2016);
	}

	private SlovakCup2016Season() {
	}

	public static SlovakCup2016Season getInstance() {
		if (instance == null) {
			instance = new SlovakCup2016Season();
		}
		return instance;
	}

	@Override
	public String getNationalChampionshipSeriesName() {
		return null;
	}

	@Override
	public String getNationalChampionshipSeriesIndexName() {
		return null;
	}

	@Override
	public String getNationalSeriesName() {
		return "Slovak Pohar";
	}

	@Override
	public int getNationalSeriesCount() {
		return 3;
	}

	@Override
	public int getNationalSeriesExcludedCount() {
		return 0;
	}

	@Override
	public int getOtherSeriesCount() {
		return 0;
	}

	@Override
	public Collection<String> getExcludedSeries() {
		return Collections.EMPTY_LIST;
	}

	@Override
	public String getSeason(IthfTournament wchTournament) {
		if (wchTournament.getDate().after(getStartDateYear(YEAR_2015))
		        && wchTournament.getDate().before(getEndDateYear(YEAR_2016))) {
			return getSeasonsName().get(YEAR_2016);
		}

		return null;
	}

	@Override
	public Map<String, String> getSeasonsName() {
		return SEASONS_NAME;
	}

	@Override
	public String getLabelName() {
		return "Slovak Cup 2016";
	}

	private Date getStartDateYear(String year) {
		try {
			DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
			return df.parse("01.09." + year);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private Date getEndDateYear(String year) {
		try {
			DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
			return df.parse("01.06." + year);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<Integer> getScoring() {
		return new SlovakScoring();
	}

	@Override
	public Integer getExtraBonusPoints() {
		return 0;
	}

	@Override
	public String getExtraBonusTournamentName() {
		return null;
	}

	@Override
	public String getCountry() {
		return "Slovakia";
	}

	@Override
	public Date getStartSeason() {
		return getStartDateYear(YEAR_2015);
	}

	@Override
	public Date getEndSeason() {
		return getEndDateYear(YEAR_2016);
	}

}
