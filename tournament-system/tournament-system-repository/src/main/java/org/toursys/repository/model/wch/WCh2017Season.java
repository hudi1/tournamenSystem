package org.toursys.repository.model.wch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WCh2017Season extends WChSeason {

    private static final String YEAR_2016 = "2016";
    private static final String YEAR_2017 = "2017";

    private static WCh2017Season instance;

    private WCh2017Season() {
    }

    public static WCh2017Season getInstance() {
        if (instance == null) {
            instance = new WCh2017Season();
        }
        return instance;
    }

    @Override
    public Date getStartDateYear1() {
        try {
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            return df.parse("01.09." + (Integer.parseInt(YEAR_2016) - 1));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Date getStartDateYear2() {
        try {
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            return df.parse("01.09." + (Integer.parseInt(YEAR_2016)));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Date getEndDateYear1() {
        try {
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            return df.parse("01.06." + YEAR_2016);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Date getEndDateYear2() {
        try {
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            return df.parse("01.06." + YEAR_2017);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
