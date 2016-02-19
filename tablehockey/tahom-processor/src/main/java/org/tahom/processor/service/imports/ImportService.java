package org.tahom.processor.service.imports;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.tahom.processor.ImportTournamentException;
import org.tahom.processor.html.PlayerHtmlUpdateFactory;
import org.tahom.processor.html.PlayersHtmlImportFactory;
import org.tahom.processor.html.TournamentHtmlImportFactory;
import org.tahom.processor.service.game.GameService;
import org.tahom.processor.service.group.GroupService;
import org.tahom.processor.service.participant.ParticipantService;
import org.tahom.processor.service.playOffGame.PlayOffGameService;
import org.tahom.processor.service.player.PlayerService;
import org.tahom.processor.service.standing.FinalStandingService;
import org.tahom.repository.dao.FinalStandingDao;
import org.tahom.repository.dao.GameDao;
import org.tahom.repository.dao.ParticipantExtDao;
import org.tahom.repository.model.FinalStanding;
import org.tahom.repository.model.Game;
import org.tahom.repository.model.Groups;
import org.tahom.repository.model.Participant;
import org.tahom.repository.model.PlayOffGame;
import org.tahom.repository.model.Player;
import org.tahom.repository.model.Tournament;
import org.tahom.repository.model.User;

public class ImportService {

	@Inject
	private PlayerService playerService;

	@Inject
	private ParticipantService participantService;

	@Inject
	private GameService gameService;

	@Inject
	private PlayOffGameService playOffGameService;

	@Inject
	private FinalStandingService finalStandingService;

	@Inject
	private GameDao gameDao;

	@Inject
	private FinalStandingDao finalStandingDao;

	@Inject
	private ParticipantExtDao participantDao;

	@Inject
	private GroupService groupService;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Transactional
	public void importTournament(String url, Tournament tournament, User user) {

		importPlayers(url + "index.htm", user);

		LinkedList<Participant> participants = TournamentHtmlImportFactory.createImportedParticipants(tournament, url);

		for (Participant participant : participants) {
			List<Player> players = playerService.getNotRegisteredPlayers(tournament);
			Boolean founded = false;
			for (Player player : players) {
				if (player.getSurname().equals(participant.getPlayer().getSurname())) {
					if (player.getName().charAt(0) == participant.getPlayer().getName().charAt(0)) {
						if (player.getPlayerDiscriminator().equals(participant.getPlayer().getPlayerDiscriminator())) {
							founded = true;
							participant.setPlayer(player);
							break;
						}
					}
				}
			}

			if (!founded) {
				// Parametrizovat a lokalizovat vyjimky
				throw new RuntimeException(participant.getPlayer().toString());
			}
		}

		LinkedList<Participant> savedParticipants = new LinkedList<Participant>();

		for (Participant participant : participants) {
			Groups group = groupService.getGroup(participant.getGroup());
			if (group == null) {
				group = groupService.createGroup(participant.getGroup());
			}

			savedParticipants.add(participantService.createParticipant(participant.getPlayer(), group));
		}

		TournamentHtmlImportFactory.createImportedGames(url, savedParticipants);

		for (Participant participant : savedParticipants) {
			participantDao.update(participant);
			for (Game game : participant.getGames()) {
				gameDao.insert(game);
			}
		}

		List<PlayOffGame> playOffGames = TournamentHtmlImportFactory.createPlayOffGames(url, savedParticipants);
		for (PlayOffGame playOffGame : playOffGames) {
			playOffGameService.createPlayOffGame(playOffGame);
		}

		List<FinalStanding> finalStandings = TournamentHtmlImportFactory.createFinalStandings(url, savedParticipants);
		for (FinalStanding finalStanding : finalStandings) {
			finalStanding.setTournament(tournament);
			finalStandingDao.insert(finalStanding);
		}

	}

	@Transactional
	public void importPlayers(String url, User user) {
		List<Player> players = PlayersHtmlImportFactory.createdImportedPlayers(url);
		for (Player player : players) {
			playerService.createPlayer(user, player);
		}
	}

	public List<Player> updateOnlinePlayers(List<Player> players) {
		List<Player> notUpdatedPlayer = new ArrayList<Player>();

		try {
			PlayerHtmlUpdateFactory.synchronizedIthfPlayer(players);

			for (Player player : players) {
				if (player.getIthfId() == null || player.getWorldRanking() == null) {
					notUpdatedPlayer.add(player);
				} else {
					playerService.updatePlayer(player);
				}
			}
		} catch (Exception e) {
			logger.error("Error import players ", e);
			throw new ImportTournamentException(e.getMessage());
		}
		return notUpdatedPlayer;
	}
}
