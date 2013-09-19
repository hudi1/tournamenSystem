package org.toursys.processor.service;

import java.util.List;

import org.toursys.repository.model.Season;

public class SeasonService extends AbstractService {

    // Basic operations

    public Season createSeason(Season season) {
        return tournamentAggregationDao.createSeason(season);
    }

    public Season getSeason(Season season) {
        return tournamentAggregationDao.getSeason(season);
    }

    public int updateSeason(Season season) {
        return tournamentAggregationDao.updateSeason(season);
    }

    public int deleteSeason(Season season) {
        return tournamentAggregationDao.deleteSeason(season);
    }

    public List<Season> getSeasons(Season season) {
        return tournamentAggregationDao.getListSeasons(season);
    }

    // Advanced operations

    public List<Season> getAllSeasons() {
        return tournamentAggregationDao.getListSeasons(new Season());
    }
}