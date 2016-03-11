package org.tahom.processor.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tahom.processor.ImportTournamentException;
import org.tahom.repository.model.Player;
import org.tahom.repository.model.Surname;

public class PlayersHtmlImportFactory {

	private static final String TREFIK_URL = "trefik.cz/stiga";
	private static final String ITHF_URL = "stiga.trefik.cz/ithf/";

	private static final Logger logger = LoggerFactory.getLogger(PlayersHtmlImportFactory.class);

	public static List<Player> createdImportedPlayers(String url) {
		List<Player> players = new ArrayList<Player>();

		try {
			if (url.contains(TREFIK_URL)) {
				Document doc = Jsoup.connect(url + "index.htm").get();
				players = getTrefikPlayers(doc);
			} else if (url.contains(ITHF_URL)) {

				Document doc = Jsoup.connect(url).get();

				Elements viewState = doc.select("input[name=__VIEWSTATE");
				Elements eventValidation = doc.select("input[name=__EVENTVALIDATION]");

				Map<String, String> allFields = new HashMap<String, String>();
				allFields.put("__VIEWSTATE", viewState.val());
				allFields.put("__EVENTVALIDATION", eventValidation.val());
				allFields.put("DLCountry", "26");
				allFields.put("butCountry", "Sign In");

				doc = Jsoup.connect(url).data(allFields).post();
				players = getIthfPlayers(doc);
			} else {
				logger.warn("Unknown url: " + url + " . Trying to parse according to trefik.cz");
				Document doc = Jsoup.connect(url).get();
				players = getTrefikPlayers(doc);
				// throw new ImportTournamentException();
			}
		} catch (Exception e) {
			logger.error("Error during importing players", e);
			throw new ImportTournamentException(e.getMessage());
		}
		return players;
	}

	private static List<Player> getIthfPlayers(Document doc) {
		List<Player> players = new ArrayList<Player>();

		Element span = doc.select("span#LabRanking").first();
		Element body = span.select("table").get(1);
		Elements tableRows = body.select(" > tbody > tr:has(table)");

		for (int i = 0; i < tableRows.size(); i++) {

			String nameAndSurname = tableRows.get(i).select("td").get(1).select("a").first().ownText();
			String club = null;
			try {
				club = tableRows.get(i).select("td").get(2).select("a").first().ownText();
			} catch (Exception e) {
				logger.warn("Ignoring club of imported player: " + nameAndSurname, e);
			}
			Player player = new Player();
			player.setClub(club);
			player.setName(nameAndSurname.split(" ")[0]);
			player.setSurname(new Surname(nameAndSurname.split(" ")[1]));
			players.add(player);
		}
		return players;
	}

	private static List<Player> getTrefikPlayers(Document doc) {
		List<Player> players = new ArrayList<Player>();

		Element playerTable = doc.select("table").last();
		Element body = playerTable.select("tbody").first();
		Elements tableRows = body.select("tr");

		for (int i = 1; i < tableRows.size(); i++) {

			String nameAndSurname = tableRows.get(i).select("td").get(1).ownText();
			String club = tableRows.get(i).select("td").get(2).ownText();

			Player player = TournamentHtmlImportFactory.parsePlayer(nameAndSurname);
			player.setClub(club.replace("*", "").trim());
			players.add(player);
		}
		return players;

	}
}
