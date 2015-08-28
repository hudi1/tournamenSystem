package org.toursys.processor.service.tournament;

import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;
import org.toursys.repository.dao.TournamentDao;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Tournament;

public class TournamentService {

    @Inject
    private TournamentDao tournamentDao;

    @Transactional
    public Tournament createTournament(Season season, Tournament tournament) {
        return tournamentDao.insert(tournament._setSeason(season));
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

    @Transactional(readOnly = true)
    public List<Tournament> getTournaments(Tournament tournament) {
        return tournamentDao.list(tournament);
    }
}