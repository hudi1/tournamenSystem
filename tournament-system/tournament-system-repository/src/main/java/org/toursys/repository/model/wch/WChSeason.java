package org.toursys.repository.model.wch;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.toursys.repository.model.WchTournament;

public abstract class WChSeason implements Serializable {

    private static final long serialVersionUID = 1L;

    public abstract String getNationalChampionshipSeriesName();

    public abstract String getNationalChampionshipSeriesIndexName();

    public abstract String getNationalSeriesName();

    public abstract int getNationalSeriesCount();

    public abstract int getOtherSeriesCount();

    public abstract List<String> getExcludedSeries();

    public abstract String getSeason(WchTournament wchTournament);

    public abstract Map<String, String> getSeasonsName();

    public abstract String getLabelName();

}
