package org.toursys.processor.html;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toursys.processor.ImportTournamentException;
import org.toursys.repository.model.WchQualification;
import org.toursys.repository.model.WchTournament;

public class WChHtmlImportFactory {

    private static final String ITHF_URL = "http://stiga.trefik.cz/ithf/ranking/player.aspx?id=";
    private static final String NATION = "Slovakia";
    private static final String TOO_OLD = "2014";

    private static final Logger logger = LoggerFactory.getLogger(WChHtmlImportFactory.class);

    public static WchQualification getWchQualification(Integer id) {
        if (id == null) {
            return null;
        }

        try {
            Document doc = Jsoup.connect(ITHF_URL + id.toString()).post();
            WchQualification record = getWChRecord(doc);
            if (record != null) {
                record.setIthfId(id);
                record.setLastUpdate(new Date());
            }
            return record;
        } catch (Exception e) {
            logger.error("Error during importing players", e);
            throw new ImportTournamentException(e.getMessage());
        }
    }

    private static WchQualification getWChRecord(Document doc) throws Exception {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        DateFormat yearDf = new SimpleDateFormat("yyyy");

        Element spanCountry = doc.select("span#LabCountry").first();
        String country = spanCountry.select("a").first().ownText();
        if (country == null || !NATION.equals(country)) {
            return null;
        }

        Element divTable = doc.select("div#PanelTour").first();
        Element bodyTable = divTable.select("table").get(1).select("table").get(1);
        Elements tableRows = bodyTable.select(" > tbody > tr");
        WchQualification wchQualification = new WchQualification();

        for (int i = 1; i < tableRows.size(); i++) {
            WchTournament wchTournament = new WchTournament();

            String points = tableRows.get(i).select("td").get(6).ownText();
            if (points.contains("(")) {
                points = points.substring(points.indexOf("(") + 1, points.indexOf(")"));
            }

            String series = null;
            if (tableRows.get(i).select("td").get(3).select("a").size() > 0) {
                series = tableRows.get(i).select("td").get(3).select("a").first().ownText();
            }
            String date = tableRows.get(i).select("td").get(0).ownText();
            String name = tableRows.get(i).select("td").get(1).select("a").first().ownText();

            wchTournament.setPoints(Integer.parseInt(points));
            wchTournament.setSeries(series);
            wchTournament.setDate(df.parse(date));
            wchTournament.setName(name);
            if (yearDf.format(wchTournament.getDate()).equals(TOO_OLD)) {
                break;
            }
            wchQualification.getWchTournaments().add(wchTournament);

        }

        return wchQualification;
    }

    public static void main(String[] args) {
        WchQualification a = getWchQualification(260047);

        System.out.println(a);
    }

}
