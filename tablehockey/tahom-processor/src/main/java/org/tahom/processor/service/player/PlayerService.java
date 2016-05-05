package org.tahom.processor.service.player;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;
import org.tahom.processor.service.finalStanding.FinalStandingService;
import org.tahom.processor.service.participant.ParticipantService;
import org.tahom.repository.dao.PlayerExtDao;
import org.tahom.repository.model.FinalStanding;
import org.tahom.repository.model.Participant;
import org.tahom.repository.model.Player;
import org.tahom.repository.model.Player.Attribute;
import org.tahom.repository.model.Tournament;
import org.tahom.repository.model.User;
import org.tahom.repository.model.impl.StatisticForm;

public class PlayerService {

	@Inject
	private PlayerExtDao playerDao;

	@Inject
	private FinalStandingService finalStandingService;

	@Inject
	private ParticipantService participantService;

	@Transactional
	public Player createPlayer(User user, Player player) {
		player.setUser(user);
		Player playerForm = new Player(player.getName(), player.getSurname(), user);
		Player dbPlayer = playerDao.get(playerForm);

		if (dbPlayer == null) {
			return playerDao.insert(player);
		} else {
			player.setNull_(Attribute.club);
			playerDao.update(player._setId(dbPlayer.getId()));
			return player;
		}
	}

	@Transactional
	public int updatePlayer(Player player) {
		player.setNull_(Attribute.ithfId, Attribute.club, Attribute.worldRanking);
		return playerDao.update(player);
	}

	@Transactional
	public int deletePlayer(Player player) {
		return playerDao.delete(player);
	}

	@Transactional(readOnly = true)
	public List<Player> getUserPlayers(User user) {
		Player player = new Player();
		player.setUser(user);
		player.setInit_(Player.Association.user.name());
		return playerDao.list(player);
	}

	@Transactional(readOnly = true)
	public List<Player> getUserPlayersIthfTournament(User user) {
		Player player = new Player();
		player.setUser(user);
		player.setInit_(Player.Association.user.name(), Player.Association.ithfTournaments.name());
		return playerDao.list(player);
	}

	@Transactional(readOnly = true)
	public Player getPlayer(Player player) {
		return playerDao.get(player);
	}

	@Transactional(readOnly = true)
	public List<Player> getSortedUserPlayers(User user, final Locale locale) {
		Player player = new Player();
		player.setUser(user);
		player.setInit_(Player.Association.user.name());
		List<Player> players = playerDao.list(player);
		Collections.sort(players, new Comparator<Player>() {

			@Override
			public int compare(Player o1, Player o2) {
				Collator collator = Collator.getInstance(locale);
				return collator.compare(o1.getSurname().toString() + " " + o1.getName(), o2.getSurname().toString()
				        + " " + o2.getName());
			}
		});
		return players;
	}

	@Transactional(readOnly = true)
	public List<Player> getNotRegisteredPlayers(Tournament tournament) {
		return playerDao.listNotRegisteredPlayers(tournament);
	}

	@Transactional(readOnly = true)
	public List<Player> getPlayersGames(StatisticForm statisticForm) {
		return playerDao.listPlayersGames(statisticForm);
	}

	@Transactional(readOnly = true)
	public List<Player> listPlayers(Player player) {
		player._setInit_(Player.Association.ithfTournaments);
		return playerDao.list(player);
	}

	@Transactional
	public void mergePlayers(Player originalPlayer, Player deletedPlayer) {
		if (originalPlayer != null && deletedPlayer != null) {
			List<Participant> participants = participantService.getParticipants(new Participant()
			        ._setPlayer(deletedPlayer));
			for (Participant participant : participants) {
				participant.setPlayer(originalPlayer);
				participantService.updateParticipant(participant);
			}

			List<FinalStanding> finalStandings = finalStandingService.getFinalStandings(new FinalStanding()
			        ._setPlayer(deletedPlayer));
			for (FinalStanding finalStanding : finalStandings) {
				finalStanding.setPlayer(originalPlayer);
				finalStandingService.updateFinalStanding(finalStanding);
			}
			deletePlayer(deletedPlayer);
		}
	}
}
