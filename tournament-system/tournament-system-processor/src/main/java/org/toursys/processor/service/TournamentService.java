package org.toursys.processor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Required;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.Season;
import org.toursys.repository.service.TournamentAggregationDao;

public class TournamentService {

    private TournamentAggregationDao tournamentAggregationDao;

    public void createPlayer(Player player) {
        tournamentAggregationDao.createPlayer(player);
    }

    public List<Season> getAllSeason() {
        return tournamentAggregationDao.getAllSeason();
    }

    @Required
    public void setTournamentAggregationDao(TournamentAggregationDao tournamentAggregationDao) {
        this.tournamentAggregationDao = tournamentAggregationDao;
    }

}
