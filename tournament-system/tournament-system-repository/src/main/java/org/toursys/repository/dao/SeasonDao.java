package org.toursys.repository.dao;

import java.util.List;

import org.toursys.repository.model.Season;

public interface SeasonDao {

    public Season createSeason(Season season);

    public Season updateSeason(Season season);

    public boolean deleteSeason(Season season);

    public Season getSeason(Season season);

    public List<Season> getAllSeasons();

    public List<Season> getListSeason(Season season);

}
