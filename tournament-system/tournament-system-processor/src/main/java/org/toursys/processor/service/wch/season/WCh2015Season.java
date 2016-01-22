package org.toursys.processor.service.wch.season;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.toursys.repository.model.WchTournament;
import org.toursys.repository.model.wch.WChExcludedSeries;
import org.toursys.repository.model.wch.WChSeason;

public class WCh2015Season extends WChSeason {

    private static final long serialVersionUID = 1L;

    private static WCh2015Season instance;

    private static final String YEAR_2013 = "2013";
    private static final String YEAR_2014 = "2014";
    private static final String YEAR_2015 = "2015";

    public static final Map<String, String> SEASONS_NAME = new HashMap<String, String>();

    static {
        SEASONS_NAME.put(YEAR_2014, YEAR_2013 + "/" + YEAR_2014);
        SEASONS_NAME.put(YEAR_2015, YEAR_2014 + "/" + YEAR_2015);
    }

    private WCh2015Season() {
    }

    public static WCh2015Season getInstance() {
        if (instance == null) {
            instance = new WCh2015Season();
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
    public Collection<String> getExcludedSeries() {
        return new WChExcludedSeries();
    }

    @Override
    public String getSeason(WchTournament wchTournament) {
        if (wchTournament.getDate().after(getStartDateYear(YEAR_2014))
                && wchTournament.getDate().before(getEndDateYear(YEAR_2014))) {
            return getSeasonsName().get(YEAR_2014);
        } else if (wchTournament.getDate().after(getStartDateYear(YEAR_2015))
                && wchTournament.getDate().before(getEndDateYear(YEAR_2015))) {
            return getSeasonsName().get(YEAR_2015);
        }

        return null;
    }

    @Override
    public Map<String, String> getSeasonsName() {
        return SEASONS_NAME;
    }

    @Override
    public String getLabelName() {
        return "2015";
    }

}
