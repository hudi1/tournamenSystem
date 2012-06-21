package org.toursys.processor.service;

import org.toursys.repository.model.Player;
import org.toursys.repository.service.TournamentAggregationDao;

public class TournamentService {

	private TournamentAggregationDao tournamentAggregationDao;

	public void createPlayer(Player player) {
		tournamentAggregationDao.createPlayer(player);
	}

	public void setTournamentAggregationDao(
			TournamentAggregationDao tournamentAggregationDao) {
		this.tournamentAggregationDao = tournamentAggregationDao;
	}

}
