package org.toursys.processor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.toursys.repository.service.TournamentAggregationDao;

public abstract class AbstractService {

    protected TournamentAggregationDao tournamentAggregationDao;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public static final int BEST_OF_GAMES = 9;

    @Required
    public void setTournamentAggregationDao(TournamentAggregationDao tournamentAggregationDao) {
        this.tournamentAggregationDao = tournamentAggregationDao;
    }
}
