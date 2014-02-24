package org.toursys.processor.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.toursys.repository.model.Season;

public class SeasonService extends AbstractService {

    // Basic operations

    @Transactional
    public Season createSeason(Season season) {
        logger.debug("Create season: " + season);
        return tournamentAggregationDao.createSeason(season);
    }

    @Transactional(readOnly = true)
    public Season getSeason(Season season) {
        logger.debug("Get season: " + season);
        return tournamentAggregationDao.getSeason(season);
    }

    @Transactional
    public int updateSeason(Season season) {
        logger.debug("Update season: " + season);
        return tournamentAggregationDao.updateSeason(season);
    }

    @Transactional
    public int deleteSeason(Season season) {
        logger.debug("Delete season: " + season);
        return tournamentAggregationDao.deleteSeason(season);
    }

    @Transactional(readOnly = true)
    public List<Season> getSeasons(Season season) {
        logger.debug("Get list seasons: " + season);
        return tournamentAggregationDao.getListSeasons(season);
    }

    // Advanced operations

    @Transactional(readOnly = true)
    public List<Season> getAllSeasons() {
        logger.debug("Get all seasons");
        return tournamentAggregationDao.getListSeasons(new Season()._setInit(Season.Association.tournaments));
    }
}