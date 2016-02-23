package org.tahom.processor.html;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tahom.processor.ImportTournamentException;
import org.tahom.repository.model.FinalStanding;
import org.tahom.repository.model.Game;
import org.tahom.repository.model.Groups;
import org.tahom.repository.model.GroupsPlayOffType;
import org.tahom.repository.model.GroupsType;
import org.tahom.repository.model.Participant;
import org.tahom.repository.model.PlayOffGame;
import org.tahom.repository.model.Player;
import org.tahom.repository.model.Result;
import org.tahom.repository.model.Results;
import org.tahom.repository.model.Score;
import org.tahom.repository.model.Surname;
import org.tahom.repository.model.Tournament;

public class TournamentHtmlImportFactory {
	// http://trefik.cz/stiga/turnaje2014/kladno/kriz.htm

	private static final Logger logger = LoggerFactory.getLogger(TournamentHtmlImportFactory.class);

	public static LinkedList<Participant> createImportedParticipants(Tournament tournament, String urlBase) {

		LinkedList<Participant> tournamentParticipants = new LinkedList<Participant>();

		try {
			Document doc = Jsoup.connect(urlBase + "kriz.htm").get();

			for (Element element : doc.select("p.v")) {
				String groupName = element.ownText();
				Groups group = new Groups();
				group.setName(groupName.split(" ")[1]);
				group.setTournament(tournament);
				if (isNumeric(group.getName())) {
					group.setType(GroupsType.BASIC);
				} else {
					group.setType(GroupsType.FINAL);
					if ("A".equals(group.getName().trim())) {
						group.setPlayOffType(GroupsPlayOffType.FINAL);
					}
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

					// new get(2)
					String playerName = rowElement.select("td").get(1).ownText();
					// System.out.println("Parsing player: " + playerName);

					if (playerName.contains("Chylík")) {
						player.setSurname(new Surname("Chylík ml."));
						player.setName("Jiří");
					} else if (playerName.contains("I.Vépy")) {
						player.setSurname(new Surname("Vépy I"));
						player.setName("Martin");
					} else {
						player.setSurname(new Surname(playerName.split("\\.", 2)[1]));
						player.setName(playerName.split("\\.")[0]);
					}
				}
				tournamentParticipants.addAll(participants);
			}
		} catch (Exception e) {
			logger.error("Error during creating imported participants", e);
			throw new ImportTournamentException(e.getMessage());
		}
		return tournamentParticipants;
	}

	public static void createImportedGames(String urlBase, LinkedList<Participant> tournamentParticipants) {

		try {
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
		} catch (Exception e) {
			logger.error("Error during creating imported games", e);
			throw new ImportTournamentException(e.getMessage());
		}
	}

	public static List<PlayOffGame> createPlayOffGames(String urlBase, List<Participant> savedParticipants) {
		List<PlayOffGame> playOffGames = new ArrayList<PlayOffGame>();

		try {
			String startOfHeading = " ";

			Document doc = Jsoup.connect(urlBase + "umisteni.htm").get();

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
					String playerNameAway = columns.get(2).ownText().replace("- ", "").trim()
					        .replaceAll("\\(.*\\)", "");
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
		} catch (Exception e) {
			logger.error("Error during creating play off games", e);
			throw new ImportTournamentException(e.getMessage());
		}
		return playOffGames;
	}

	public static List<FinalStanding> createFinalStandings(String url, List<Participant> savedParticipants) {

		List<FinalStanding> finalStandings = new ArrayList<FinalStanding>();
		try {
			Document doc = Jsoup.connect(url + "index.htm").get();

			Element playerTable = doc.select("table").last();
			Element body = playerTable.select("tbody").first();
			Elements tableRows = body.select("tr");

			for (Participant participant : savedParticipants) {
				List<FinalStanding> foundedPlayers = new ArrayList<FinalStanding>();

				for (int i = 1; i < tableRows.size(); i++) {
					FinalStanding finalStanding = new FinalStanding();
					String playerName = tableRows.get(i).select("td").get(1).ownText();
					Player player = parsePlayer(playerName);
					String rank = tableRows.get(i).select("td").get(0).ownText().replace(".", "");
					finalStanding.setFinalRank(Integer.parseInt(rank));

					if (player.getSurname().getValue().equals(participant.getPlayer().getSurname().getValue())) {

						if (player.getName().charAt(0) == participant.getPlayer().getName().charAt(0)) {
							foundedPlayers
							        .add(finalStanding._setPlayer(player._setId(participant.getPlayer().getId())));
						}
					}
				}

				if (foundedPlayers.isEmpty()) {
					throw new ImportTournamentException(participant.getPlayer().toString());
				} else if (foundedPlayers.size() == 1) {
					boolean founded = false;
					for (FinalStanding finalStanding : finalStandings) {
						if (finalStanding.getFinalRank().equals(foundedPlayers.get(0).getFinalRank())) {
							founded = true;
						}
					}
					if (!founded) {
						finalStandings.add(foundedPlayers.get(0));
					}

				} else {
					for (FinalStanding foundedPlayer : foundedPlayers) {
						if (participant.getPlayer().getSurname().getDiscriminant()
						        .equals(foundedPlayer.getPlayer().getSurname().getDiscriminant())) {
							boolean founded = false;
							for (FinalStanding finalStanding : finalStandings) {
								if (finalStanding.getFinalRank().equals(foundedPlayer.getFinalRank())) {
									founded = true;
								}
							}
							if (!founded) {
								finalStandings.add(foundedPlayer);
							}

							break;
						}
					}
				}

				foundedPlayers.clear();

			}
		} catch (Exception e) {
			logger.error("Error during creating play off games", e);
			throw new ImportTournamentException(e.getMessage());
		}
		return finalStandings;
	}

	private static boolean isEqualPlayer(Player htmlPlayer, Player player) {
		if (htmlPlayer.getName().equals(player.getName())) {
			if (htmlPlayer.getSurname().equals(player.getSurname())) {
				return true;
			}
		}
		return false;
	}

	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");
	}

	public static Player parsePlayer(String playerName) {
		// System.out.println("Parse name: " + playerName);
		Player player = new Player();
		if (playerName.split(" ").length == 2) {
			player.setSurname(new Surname(playerName.split(" ")[0]));
			player.setName(playerName.split(" ")[1]);
		} else if (playerName.split(" ").length == 3) {
			player.setSurname(new Surname(playerName.split(" ")[0] + " " + playerName.split(" ")[1]));
			player.setName(playerName.split(" ")[2]);
		}
		return player;
	}

}