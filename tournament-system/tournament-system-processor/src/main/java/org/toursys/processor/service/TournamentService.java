package org.toursys.processor.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Tournament;

public class TournamentService extends AbstractService {

    // Basic operations

    @Transactional
    public Tournament createTournament(Season season, Tournament tournament) {
        logger.debug("Create tournament: " + season + " " + tournament);
        return tournamentAggregationDao.createTournament(tournament._setSeason(season));
    }

    @Transactional(readOnly = true)
    public Tournament getTournament(Tournament tournament) {
        logger.debug("Get tournament: " + tournament);
        return tournamentAggregationDao.getTournament(tournament);
    }

    @Transactional
    public int updateTournament(Tournament tournament) {
        logger.debug("Update tournament: " + tournament);
        return tournamentAggregationDao.updateTournament(tournament);
    }

    @Transactional
    public int deleteTournament(Tournament tournament) {
        logger.debug("Delete tournament: " + tournament);
        return tournamentAggregationDao.deleteTournament(tournament);
    }

    @Transactional(readOnly = true)
    public List<Tournament> getTournaments(Tournament tournament) {
        logger.debug("Get list tournaments: " + tournament);
        return tournamentAggregationDao.getListTournaments(tournament);
    }
}