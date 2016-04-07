package org.tahom.repository.model.event;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.tahom.repository.model.IthfTournament;

public abstract class EventSeason implements Serializable {

	private static final long serialVersionUID = 1L;

	public abstract String getNationalChampionshipSeriesName();

	public abstract String getNationalChampionshipSeriesIndexName();

	public abstract String getNationalSeriesName();

	public abstract int getNationalSeriesCount();

	public abstract int getNationalSeriesExcludedCount();

	public abstract int getOtherSeriesCount();

	public abstract Collection<String> getExcludedSeries();

	public abstract String getSeason(IthfTournament wchTournament);

	public abstract Map<String, String> getSeasonsName();

	public abstract String getLabelName();

	public abstract List<Integer> getScoring();

	public abstract Integer getExtraBonusPoints();

	public abstract String getExtraBonusTournamentName();

	public abstract String getCountry();

	public abstract Date getStartSeason();

	public abstract Date getEndSeason();

}
