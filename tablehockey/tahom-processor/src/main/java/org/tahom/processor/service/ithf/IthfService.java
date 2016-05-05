package org.tahom.processor.service.ithf;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;
import org.tahom.processor.html.IthfTournamentHtmlImportFactory;
import org.tahom.processor.service.player.PlayerService;
import org.tahom.repository.dao.impl.IthfTournamentExtDaoImpl;
import org.tahom.repository.model.IthfTournament;
import org.tahom.repository.model.Player;
import org.tahom.repository.model.User;
import org.tahom.repository.model.event.EventSeason;
import org.tahom.repository.model.impl.IthfTournamentForm;

public class IthfService {

	@Inject
	private IthfTournamentExtDaoImpl ithfTournamentDaoImpl;

	@Inject
	private PlayerService playerService;

	@Transactional
	public IthfTournament createIthfTournament(IthfTournament ithfTournament) {
		return ithfTournamentDaoImpl.insert(ithfTournament);
	}

	@Transactional
	public int updateIthfTournament(IthfTournament ithfTournament) {
		return ithfTournamentDaoImpl.update(ithfTournament);
	}

	@Transactional
	public int deleteIthfTournament(IthfTournament ithfTournament) {
		return ithfTournamentDaoImpl.delete(ithfTournament);
	}

	@Transactional
	public int getCustomIthfTournamentCount(IthfTournamentForm ithfTournamentForm) {
		return ithfTournamentDaoImpl.customCount(ithfTournamentForm);
	}

	@Transactional
	public void updateIthfTournaments(User user, EventSeason eventSeason) {
		List<Player> players = playerService.getUserPlayersIthfTournament(user);
		for (Player player : players) {
			if (player.getIthfId() != null) {
				List<IthfTournament> tournaments = IthfTournamentHtmlImportFactory
						.getIthfTournaments(player.getIthfId(), eventSeason.getCountry());

				for (IthfTournament ithfTournament : tournaments) {

					IthfTournament founded = null;
					for (IthfTournament ithfTournamentDb : player.getIthfTournaments()) {
						if (ithfTournament.getName().equals(ithfTournamentDb.getName())) {
							founded = ithfTournamentDb;
							break;
						}
					}
					if (founded == null) {
						ithfTournament.setPlayer(player);
						createIthfTournament(ithfTournament);
					} else {
						// TODO not necessary in future when db model will be completed
						if (!Objects.equals(founded.getPoints(), ithfTournament.getPoints())
								|| !Objects.equals(founded.getRank(), founded.getRank())) {

							founded.setPoints(ithfTournament.getPoints());
							founded.setRank(ithfTournament.getRank());
							updateIthfTournament(founded);
						}
					}
				}
			}
			player.setLastUpdate(new Date());
			playerService.updatePlayer(player);
		}
	}
}
