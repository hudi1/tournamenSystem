package org.toursys.processor.service.wch.season;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.toursys.repository.model.WchTournament;
import org.toursys.repository.model.wch.WChExcludedSeries;
import org.toursys.repository.model.wch.WChSeason;

public class WCh2017Season extends WChSeason {

    private static final long serialVersionUID = 1L;

    private static WCh2017Season instance;

    private static final String YEAR_2015 = "2015";
    private static final String YEAR_2016 = "2016";
    private static final String YEAR_2017 = "2017";

    public static final Map<String, String> SEASONS_NAME = new HashMap<String, String>();

    static {
        SEASONS_NAME.put(YEAR_2016, YEAR_2015 + "/" + YEAR_2016);
        SEASONS_NAME.put(YEAR_2017, YEAR_2016 + "/" + YEAR_2017);
    }

    private WCh2017Season() {
    }

    public static WCh2017Season getInstance() {
        if (instance == null) {
            instance = new WCh2017Season();
        }
        return instance;
    }

    private Date getStartDateYear(String year) {
        try {
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            return df.parse("01.09." + (Integer.parseInt(year) - 1));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Date getEndDateYear(String year) {
        try {
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            return df.parse("01.06." + year);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String getNationalChampionshipSeriesName() {
        return "Slovak Championships";
    }

    @Override
    public String getNationalChampionshipSeriesIndexName() {
        return "SVK Ch";
    }

    @Override
    public String getNationalSeriesName() {
        return null;
    }

    @Override
    public int getNationalSeriesCount() {
        return 0;
    }

    @Override
    public int getOtherSeriesCount() {
        return 3;
    }

    @Override
    public List<String> getExcludedSeries() {
        return new WChExcludedSeries();
    }

    @Override
    public String getSeason(WchTournament wchTournament) {
        if (wchTournament.getDate().after(getStartDateYear(YEAR_2016))
                && wchTournament.getDate().before(getEndDateYear(YEAR_2016))) {
            return getSeasonsName().get(YEAR_2016);
        } else if (wchTournament.getDate().after(getStartDateYear(YEAR_2017))
                && wchTournament.getDate().before(getEndDateYear(YEAR_2017))) {
            return getSeasonsName().get(YEAR_2017);
        }

        return null;
    }

    @Override
    public Map<String, String> getSeasonsName() {
        return SEASONS_NAME;
    }

    @Override
    public String getLabelName() {
        return "2017";
    }

}
