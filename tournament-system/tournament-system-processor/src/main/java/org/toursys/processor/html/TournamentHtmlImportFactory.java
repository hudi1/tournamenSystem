package org.toursys.processor.html;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.toursys.repository.model.FinalStanding;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.GroupsType;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.Result;
import org.toursys.repository.model.Results;
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
                } else if (playerName.contains("I.Vépy")) {
                    player.setPlayerDiscriminator("I");
                    player.setSurname("Vépy");
                    player.setName("Martin");
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

    public static void createImportedGames(String urlBase, LinkedList<Participant> tournamentParticipants)
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
                    game.setResult(new Results(result));
                }
            }
            groupPlayerPrefix += tableRows.size();
        }
    }

    public static List<PlayOffGame> createPlayOffGames(String urlBase, List<Participant> savedParticipants)
            throws Exception {
        String startOfHeading = " ";

        Document doc = Jsoup.connect(urlBase + "umisteni.htm").get();
        List<PlayOffGame> playOffGames = new ArrayList<PlayOffGame>();

        for (Element element : doc.select("p.v")) {
            Element body = element.nextElementSibling().select("tbody").get(1);
            Elements tableRows = body.select("tr");
            tableRows.remove(0);

            String groupName = "A";
            int position = 16;
            boolean first = true;

            for (Element rowElement : tableRows) {
                Elements columns = rowElement.select("td");

                if (columns.get(0).ownText().contains("B Finals")) {
                    groupName = "B";
                    position = 8;
                    first = true;
                }

                if (first) {
                    PlayOffGame playOffGameTemp = new PlayOffGame();
                    playOffGameTemp.setPosition(position--);
                    playOffGames.add(playOffGameTemp);

                    for (Participant participant : savedParticipants) {
                        if (participant.getGroup().getName().equals(groupName)) {
                            playOffGameTemp.setGroup(participant.getGroup());
                            break;
                        }
                    }
                    first = false;
                }

                PlayOffGame playOffGame = new PlayOffGame();
                playOffGame.setPosition(position--);
                playOffGames.add(playOffGame);

                playOffGame.setResult(new Results());

                String playerNameHome = columns.get(1).ownText().trim().replaceAll("\\(.*\\)", "");
                if (playerNameHome.isEmpty()) {
                    playerNameHome = columns.get(1).select("b").first().ownText().trim().replaceAll("\\(.*\\)", "");
                }
                String playerNameAway = columns.get(2).ownText().replace("- ", "").trim().replaceAll("\\(.*\\)", "");
                if (playerNameAway.isEmpty()) {
                    playerNameAway = columns.get(2).select("b").first().ownText().replace("- ", "").trim()
                            .replaceAll("\\(.*\\)", "");
                }
                Player playerHome = parsePlayer(playerNameHome);
                Player playerAway = parsePlayer(playerNameAway);
                boolean homeFounded = false;
                boolean awayFouned = false;

                for (Participant participant : savedParticipants) {
                    if (!participant.getGroup().getName().equals(groupName)) {
                        continue;
                    }
                    if (!homeFounded && isEqualPlayer(playerHome, participant.getPlayer())) {
                        playOffGame.setHomeParticipant(participant);
                        playOffGame.setGroup(participant.getGroup());
                        homeFounded = true;
                    }
                    if (!awayFouned && isEqualPlayer(playerAway, participant.getPlayer())) {
                        playOffGame.setAwayParticipant(participant);
                        playOffGame.setGroup(participant.getGroup());
                        awayFouned = true;
                    }
                    if (homeFounded && awayFouned) {
                        break;
                    }

                }

                if (!homeFounded) {
                    throw new RuntimeException("Player: " + playerHome + "  not found");
                }

                if (!awayFouned) {
                    throw new RuntimeException("Player: " + awayFouned + " not found");
                }

                for (int i = 3; i < columns.size(); i++) {
                    String resultString = columns.get(i).ownText();
                    resultString = resultString.replaceAll(startOfHeading, "");
                    if (resultString.isEmpty()) {
                        continue;
                    }
                    playOffGame.getResult().getResults().add(new Result(resultString));
                }
            }
        }
        return playOffGames;
    }

    public static List<FinalStanding> createFinalStandings(String url, List<Participant> savedParticipants)
            throws Exception {
        Document doc = Jsoup.connect(url + "index.htm").get();
        List<FinalStanding> finalStandings = new ArrayList<FinalStanding>();

        Element playerTable = doc.select("table").last();
        Element body = playerTable.select("tbody").first();
        Elements tableRows = body.select("tr");

        for (int i = 1; i < tableRows.size(); i++) {

            FinalStanding finalStanding = new FinalStanding();
            String playerName = tableRows.get(i).select("td").get(1).ownText();
            String rank = tableRows.get(i).select("td").get(0).ownText().replace(".", "");
            finalStanding.setFinalRank(Integer.parseInt(rank));

            Player player = parsePlayer(playerName);
            boolean founded = false;

            for (Participant participant : savedParticipants) {
                if (isEqualPlayer(player, participant.getPlayer())) {
                    finalStanding.setPlayer(participant.getPlayer());
                    founded = true;
                }
            }

            if (!founded) {
                throw new RuntimeException("Player: " + playerName + "  not found");
            }

            finalStandings.add(finalStanding);
        }

        return finalStandings;
    }

    public static void main(String[] args) throws Exception {
        createFinalStandings("http://trefik.cz/stiga/turnaje2014/kladno/", new ArrayList<Participant>());
    }

    private static boolean isEqualPlayer(Player htmlPlayer, Player player) {
        System.out.println(htmlPlayer + " vs " + player);
        if (htmlPlayer.getName().equals(player.getName())) {
            if (htmlPlayer.getSurname().equals(player.getSurname())) {
                if (htmlPlayer.getPlayerDiscriminator().equals(player.getPlayerDiscriminator())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public static Player parsePlayer(String playerName) {
        System.out.println("Parse name: " + playerName);
        Player player = new Player();
        if (playerName.split(" ").length == 2) {
            player.setSurname(playerName.split(" ")[0]);
            player.setName(playerName.split(" ")[1]);
            player.setPlayerDiscriminator("");
        } else if (playerName.split(" ").length == 3) {
            player.setSurname(playerName.split(" ")[0]);
            player.setPlayerDiscriminator(playerName.split(" ")[1]);
            player.setName(playerName.split(" ")[2]);
        }
        System.out.println("Return player: " + player);
        return player;
    }
}
