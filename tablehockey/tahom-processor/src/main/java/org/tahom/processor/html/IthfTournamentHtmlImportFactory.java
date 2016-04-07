package org.tahom.processor.html;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tahom.processor.ImportTournamentException;
import org.tahom.repository.model.IthfTournament;

public class IthfTournamentHtmlImportFactory {

	private static final String ITHF_URL = "http://stiga.trefik.cz/ithf/ranking/player.aspx?id=";
	private static final Integer TOO_OLD = 2013;

	private static final Logger logger = LoggerFactory.getLogger(IthfTournamentHtmlImportFactory.class);

	public static List<IthfTournament> getIthfTournaments(Integer id, String country) {
		if (id == null) {
			return Collections.EMPTY_LIST;
		}

		try {
			Document doc = Jsoup.connect(ITHF_URL + id.toString()).post();
			return getTournaments(doc, country);
		} catch (Exception e) {
			logger.error("Error during importing players", e);
			throw new ImportTournamentException(e.getMessage());
		}
	}

	private static List<IthfTournament> getTournaments(Document doc, String dbCountry) throws Exception {
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		DateFormat yearDf = new SimpleDateFormat("yyyy");

		Element spanCountry = doc.select("span#LabCountry").first();
		String country = spanCountry.select("a").first().ownText();
		if (country == null || !dbCountry.equals(country)) {
			return Collections.EMPTY_LIST;
		}

		Element divTable = doc.select("div#PanelTour").first();
		Element bodyTable = divTable.select("table").get(1).select("table").get(1);
		Elements tableRows = bodyTable.select(" > tbody > tr");

		List<IthfTournament> tournaments = new ArrayList<IthfTournament>();

		for (int i = 1; i < tableRows.size(); i++) {
			IthfTournament ithfTournament = new IthfTournament();

			String points = tableRows.get(i).select("td").get(6).ownText();
			if (points.contains("(")) {
				points = points.substring(points.indexOf("(") + 1, points.indexOf(")"));
			}
			String rank = tableRows.get(i).select("td").get(5).ownText();
			if (rank.contains(".")) {
				rank = rank.substring(0, rank.indexOf("."));
			}

			String series = null;
			if (tableRows.get(i).select("td").get(3).select("a").size() > 0) {
				series = tableRows.get(i).select("td").get(3).select("a").first().ownText();
			}
			String date = tableRows.get(i).select("td").get(0).ownText();
			String name = tableRows.get(i).select("td").get(1).select("a").first().ownText();

			ithfTournament.setPoints(Integer.parseInt(points));
			ithfTournament.setSeries(series);
			ithfTournament.setDate(df.parse(date));
			ithfTournament.setName(name);
			ithfTournament.setRank(Integer.parseInt(rank));
			if (Integer.parseInt(yearDf.format(ithfTournament.getDate())) <= TOO_OLD) {
				break;
			}
			tournaments.add(ithfTournament);
		}

		return tournaments;
	}

}
