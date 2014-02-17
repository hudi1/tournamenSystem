package org.toursys.processor.pdf;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.User;

public class PlayersHtmlImportFactory {

    public static List<Player> createdImportedPlayers(String url, User user) throws Exception {
        // TODO
        Document doc = Jsoup.connect(url).get();
        Element playerTable = doc.select("table").last();
        Element body = playerTable.select("tbody").first();
        Elements tableRows = body.select("tr");

        List<Player> players = new ArrayList<Player>();

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
            } else if (nameAndSurname.contains("st.")) {
                nameAndSurname = nameAndSurname.replaceAll("\\st\\.", "");
                player.setName(nameAndSurname.split(" ")[1]);
                player.setSurname(nameAndSurname.split(" ")[0]);
            } else {
                player.setName(nameAndSurname.split(" ")[1]);
                player.setSurname(nameAndSurname.split(" ")[0]);
            }
            players.add(player);
        }
        return players;
    }
}
