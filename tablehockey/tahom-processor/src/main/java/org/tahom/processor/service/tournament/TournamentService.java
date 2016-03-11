package org.tahom.processor.service.tournament;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;
import org.tahom.repository.dao.TournamentDao;
import org.tahom.repository.model.Season;
import org.tahom.repository.model.Tournament;

public class TournamentService {

	@Inject
	private TournamentDao tournamentDao;

	@Transactional
	public Tournament createTournament(Season season, Tournament tournament) {
		return tournamentDao.insert(tournament._setSeason(new Season()._setId(season.getId())));
	}

	@Transactional(readOnly = true)
	public Tournament getTournament(Tournament tournament) {
		return tournamentDao.get(tournament);
	}

	@Transactional
	public int updateTournament(Tournament tournament) {
		return tournamentDao.update(tournament);
	}

	@Transactional
	public int deleteTournament(Tournament tournament) {
		return tournamentDao.delete(tournament);
	}

}