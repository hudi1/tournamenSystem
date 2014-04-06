package org.toursys.processor.html;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.GroupsType;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.Score;

public class TournamentHtmlImportFactory {
    // http://trefik.cz/stiga/turnaje2014/kladno/kriz.htm

    public static LinkedList<Participant> createImportedParticipants(String urlBase) throws Exception {

        Document doc = Jsoup.connect(urlBase + "kriz.htm").get();
        LinkedList<Participant> tournamentParticipants = new LinkedList<Participant>();

        for (Element element : doc.select("p.v")) {
            String groupName = element.ownText();
            Groups group = new Groups();
            group.setName(groupName.split(" ")[1]);
            if (isNumeric(group.getName())) {
                group.setType(GroupsType.BASIC);
            } else {
                group.setType(GroupsType.FINAL);
            }
            LinkedList<Participant> participants = new LinkedList<Participant>();

            Element body = element.nextElementSibling().select("tbody").get(1);
            Elements tableRows = body.select("tr");
            tableRows.remove(0);

            for (Element rowElement : tableRows) {
                Player player = new Player();
                Participant participant = new Participant();
                participants.add(participant);
                participant.setPlayer(player);
                participant.setGroup(group);

                String playerName = rowElement.select("td").get(1).ownText();

                if (playerName.contains("Chylík")) {
                    player.setPlayerDiscriminator("ml.");
                    player.setSurname("Chylík");
                    player.setName("Jiří");
                } else if (playerName.contains(" ")) {
                    player.setPlayerDiscriminator(playerName.split(" ")[1]);
                    player.setSurname(playerName.split(" ")[0].split("\\.")[1]);
                    player.setName(playerName.split(" ")[0].split("\\.")[0]);
                } else {
                    player.setPlayerDiscriminator("");
                    player.setSurname(playerName.split("\\.")[1]);
                    player.setName(playerName.split("\\.")[0]);
                }
            }
            tournamentParticipants.addAll(participants);
        }
        return tournamentParticipants;
    }

    public static List<Participant> createImportedGames(String urlBase, LinkedList<Participant> tournamentParticipants)
            throws Exception {

        Document doc = Jsoup.connect(urlBase + "kriz.htm").get();
        int groupPlayerPrefix = 0;
        Iterator<Participant> participantIterator = tournamentParticipants.iterator();

        for (Element element : doc.select("p.v")) {
            Element body = element.nextElementSibling().select("tbody").get(1);
            Elements tableRows = body.select("tr");
            tableRows.remove(0);

            for (Element rowElement : tableRows) {
                Participant homeParticipant = participantIterator.next();
                Elements columns = rowElement.select("td");

                String rank = columns.get(columns.size() - 1).ownText();
                String points = columns.get(columns.size() - 2).ownText();
                String score = columns.get(columns.size() - 4).ownText();

                homeParticipant.setPoints(Integer.parseInt(points));
                homeParticipant.setRank(Integer.parseInt(rank.replace(".", "")));
                homeParticipant.setScore(new Score(Integer.parseInt(score.split(":")[0]), Integer.parseInt(score
                        .split(":")[1])));

                for (int i = 2; i < columns.size() - 4; i++) {
                    String result = columns.get(i).ownText();
                    if (result.contains("*")) {
                        continue;
                    }

                    Game game = new Game();
                    game.setHomeParticipant(homeParticipant);
                    game.setAwayParticipant(tournamentParticipants.get(i - 2 + groupPlayerPrefix));
                    homeParticipant.getGames().add(game);

                    if (result.contains(":")) {
                        if (result.contains("K")) {
                            result = result.replace("K", "").trim();
                        }

                        if (result.contains("+")) {
                            result = result.split("\\+")[0].trim();
                        }

                        String[] split = result.split(":");
                        game.setHomeScore(Integer.parseInt(split[0]));
                        game.setAwayScore(Integer.parseInt(split[1]));

                        if (game.getAwayScore() == null || game.getHomeScore() == null) {
                            throw new RuntimeException(game.toString());
                        }
                    }
                }
            }
            groupPlayerPrefix += tableRows.size();
        }
        return tournamentParticipants;
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public static void main(String[] args) throws Exception {
        List<Participant> a = createImportedParticipants("http://trefik.cz/stiga/turnaje2014/kladno/");

        for (Participant participant : a) {
            for (Game game : participant.getGames()) {
                System.out.println(game.getAwayParticipant().getPlayer() + " vs "
                        + game.getHomeParticipant().getPlayer());
            }
            System.out.println();
        }
    }
}
