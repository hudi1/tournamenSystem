package org.toursys.processor.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.User;

public class PlayersHtmlImportFactory {

    private static final String TREFIK_URL = "trefik.cz/stiga";
    private static final String ITHF_URL = "stiga.trefik.cz/ithf/";

    public static List<Player> createdImportedPlayers(String url, User user) throws Exception {
        List<Player> players = new ArrayList<Player>();

        if (url.contains(TREFIK_URL)) {
            Document doc = Jsoup.connect(url).get();
            players = getTrefikPlayers(doc, user);
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
            players = getIthfPlayers(doc, user);
        }
        return players;
    }

    private static List<Player> getIthfPlayers(Document doc, User user) {
        List<Player> players = new ArrayList<Player>();

        Element span = doc.select("span#LabRanking").first();
        Element body = span.select("table").get(1);
        Elements tableRows = body.select(" > tbody > tr:has(table)");

        for (int i = 1; i < tableRows.size(); i++) {

            String nameAndSurname = tableRows.get(i).select("td").get(1).select("a").first().ownText();
            String club = tableRows.get(i).select("td").get(2).select("a").first().ownText();

            Player player = new Player();
            player.setUser(user);
            player.setClub(club);
            player.setName(nameAndSurname.split(" ")[0]);
            player.setSurname(nameAndSurname.split(" ")[1]);
            if (nameAndSurname.split(" ").length > 2 && nameAndSurname.split(" ")[2].length() < 4) {
                player.setPlayerDiscriminator(nameAndSurname.split(" ")[2]);
            }
            players.add(player);
        }
        return players;
    }

    private static List<Player> getTrefikPlayers(Document doc, User user) {
        List<Player> players = new ArrayList<Player>();

        Element playerTable = doc.select("table").last();
        Element body = playerTable.select("tbody").first();
        Elements tableRows = body.select("tr");

        for (int i = 1; i < tableRows.size(); i++) {

            String nameAndSurname = tableRows.get(i).select("td").get(1).ownText();
            String club = tableRows.get(i).select("td").get(2).ownText();

            Player player = new Player();
            player.setUser(user);
            player.setClub(club.replace("*", "").trim());
            if (nameAndSurname.contains("ml.")) {
                nameAndSurname = nameAndSurname.replaceAll("\\sml\\.", "");
                player.setName(nameAndSurname.split(" ")[1]);
                player.setSurname(nameAndSurname.split(" ")[0]);
                player.setPlayerDiscriminator("ml.");
            } else if (nameAndSurname.contains("st.")) {
                nameAndSurname = nameAndSurname.replaceAll("\\st\\.", "");
                player.setName(nameAndSurname.split(" ")[1]);
                player.setSurname(nameAndSurname.split(" ")[0]);
                player.setPlayerDiscriminator("st.");
            } else {
                player.setName(nameAndSurname.split(" ")[1]);
                player.setSurname(nameAndSurname.split(" ")[0]);
            }
            players.add(player);
        }
        return players;

    }
}
