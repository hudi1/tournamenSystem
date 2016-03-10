package org.tahom.processor.service.imports;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.tahom.processor.ImportTournamentException;
import org.tahom.processor.TournamentException;
import org.tahom.processor.html.PlayerHtmlUpdateFactory;
import org.tahom.processor.html.PlayersHtmlImportFactory;
import org.tahom.processor.html.TournamentHtmlImportFactory;
import org.tahom.processor.service.finalStanding.FinalStandingService;
import org.tahom.processor.service.game.GameService;
import org.tahom.processor.service.group.GroupService;
import org.tahom.processor.service.participant.ParticipantService;
import org.tahom.processor.service.playOffGame.PlayOffGameService;
import org.tahom.processor.service.player.PlayerService;
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
	public void importTournament(String url, Tournament tournament, User user, boolean ignoreErrors) {
		try {

			try {
				importPlayers(url, user);
			} catch (TournamentException e) {
				if (!ignoreErrors) {
					throw e;
				}
			}

			LinkedList<Participant> savedParticipants = null;
			try {
				savedParticipants = importParticipants(url, tournament);
			} catch (TournamentException e) {
				logger.error("Error", e);
				if (!ignoreErrors) {
					throw e;
				}
			}

			if (savedParticipants == null) {
				return;
			}

			try {
				importGames(url, savedParticipants);
			} catch (TournamentException e) {
				if (!ignoreErrors) {
					throw e;
				}
			}

			try {
				importPlayOffGames(url, savedParticipants, tournament);
			} catch (TournamentException e) {
				if (!ignoreErrors) {
					throw e;
				}
			}
			try {
				importFinalStandings(url, savedParticipants, tournament);
			} catch (TournamentException e) {
				if (!ignoreErrors) {
					throw e;
				}
			}

		} catch (Exception e) {
			logger.error("Error import tournament ", e);
			throw new ImportTournamentException(e.getMessage());
		}
	}

	private void importFinalStandings(String url, LinkedList<Participant> savedParticipants, Tournament tournament) {
		List<FinalStanding> finalStandings = TournamentHtmlImportFactory.createFinalStandings(url, savedParticipants);
		for (FinalStanding finalStanding : finalStandings) {
			finalStanding.setTournament(tournament);
			finalStandingDao.insert(finalStanding);
		}
	}

	private void importPlayOffGames(String url, LinkedList<Participant> savedParticipants, Tournament tournament) {
		List<PlayOffGame> playOffGames = TournamentHtmlImportFactory.createPlayOffGames(url, savedParticipants,
		        tournament);

		for (PlayOffGame playOffGame : playOffGames) {
			Groups group = groupService.getGroup(playOffGame.getGroup());
			if (group == null) {
				group = groupService.createGroup(playOffGame.getGroup());
			}

			playOffGameService.createPlayOffGame(playOffGame._setGroup(group));
		}
	}

	private void importGames(String url, LinkedList<Participant> savedParticipants) {
		TournamentHtmlImportFactory.createImportedGames(url, savedParticipants);

		for (Participant participant : savedParticipants) {
			participantDao.update(participant);
			for (Game game : participant.getGames()) {
				gameDao.insert(game);
			}
		}
	}

	public static void main(String[] args) {
		System.out.println(TournamentHtmlImportFactory.createImportedParticipants(null,
		        "http://thcbluedragon.wz.cz/stiga/turnaje/"));
	}

	private LinkedList<Participant> importParticipants(String url, Tournament tournament) {
		LinkedList<Participant> participants = TournamentHtmlImportFactory.createImportedParticipants(tournament, url);
		List<Player> players = playerService.getNotRegisteredPlayers(tournament);

		for (Participant participant : participants) {
			List<Player> foundedPlayers = new ArrayList<Player>();
			for (Player player : players) {
				if (player.getSurname().getValue().equals(participant.getPlayer().getSurname().getValue())) {
					if (player.getName().charAt(0) == participant.getPlayer().getName().charAt(0)) {
						foundedPlayers.add(player);
					}
				}
			}

			if (foundedPlayers.isEmpty()) {
				throw new ImportTournamentException(participant.getPlayer().toString());
			} else if (foundedPlayers.size() == 1) {
				participant.setPlayer(foundedPlayers.get(0));
			} else {
				for (Player player : foundedPlayers) {
					if (player.getSurname().getDiscriminant()
					        .equals(participant.getPlayer().getSurname().getDiscriminant())) {
						participant.setPlayer(player);
						break;
					}
				}
			}

			if (participant.getPlayer().getId() == null) {
				throw new ImportTournamentException(participant.getPlayer().toString());
			}
			foundedPlayers.clear();
		}

		LinkedList<Participant> savedParticipants = new LinkedList<Participant>();

		for (Participant participant : participants) {
			Groups group = groupService.getGroup(participant.getGroup());
			if (group == null) {
				group = groupService.createGroup(participant.getGroup());
			}

			savedParticipants.add(participantService.createParticipant(participant.getPlayer(), group));
		}
		return savedParticipants;
	}

	@Transactional
	public void importPlayers(String url, User user) {
		List<Player> players = PlayersHtmlImportFactory.createdImportedPlayers(url);
		for (Player player : players) {
			playerService.createPlayer(user, player);
		}
	}

	@Transactional
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
