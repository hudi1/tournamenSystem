package org.toursys.processor.service;

import java.util.List;

import org.toursys.repository.model.Season;
import org.toursys.repository.model.Tournament;

public class TournamentService extends AbstractService {

    // Basic operations

    public Tournament createTournament(Season season, Tournament tournament) {
        return tournamentAggregationDao.createTournament(tournament._setSeason(season));
    }

    public Tournament getTournament(Tournament tournament) {
        return tournamentAggregationDao.getTournament(tournament);
    }

    public int updateTournament(Tournament tournament) {
        return tournamentAggregationDao.updateTournament(tournament);
    }

    public int deleteTournament(Tournament tournament) {
        return tournamentAggregationDao.deleteTournament(tournament);
    }

    public List<Tournament> getTournaments(Tournament tournament) {
        return tournamentAggregationDao.getListTournaments(tournament);
    }
}