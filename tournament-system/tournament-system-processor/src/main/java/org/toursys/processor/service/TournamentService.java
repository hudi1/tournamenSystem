package org.toursys.processor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Required;
import org.toursys.repository.form.TournamentForm;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.service.TournamentAggregationDao;

public class TournamentService {

    private TournamentAggregationDao tournamentAggregationDao;

    public void createPlayer(Player player) {
        tournamentAggregationDao.createPlayer(player);
    }

    public List<Season> getAllSeason() {
        return tournamentAggregationDao.getAllSeason();
    }

    public void updateSeason(Season season) {
        tournamentAggregationDao.updateSeason(season);
    }

    public void createSeason(Season season) {
        tournamentAggregationDao.createSeason(season);
    }

    public void deleteSeason(Season season) {
        List<Tournament> tournaments = findTournament(new TournamentForm(season));
        for (Tournament tournament : tournaments) {
            deleteTournament(tournament);
        }
        tournamentAggregationDao.deleteSeason(season);
    }

    public List<Tournament> findTournament(TournamentForm tournamentForm) {
        return tournamentAggregationDao.findTournament(tournamentForm);
    }

    public void updateTournament(Tournament tournament) {
        tournamentAggregationDao.updateTournament(tournament);
    }

    public void createTournament(Tournament tournament) {
        tournamentAggregationDao.createTournament(tournament);
    }

    public void deleteTournament(Tournament tournament) {
        tournamentAggregationDao.deleteTournament(tournament);
    }

    @Required
    public void setTournamentAggregationDao(TournamentAggregationDao tournamentAggregationDao) {
        this.tournamentAggregationDao = tournamentAggregationDao;
    }

}
