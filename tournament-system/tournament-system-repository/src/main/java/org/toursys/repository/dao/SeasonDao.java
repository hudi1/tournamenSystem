package org.toursys.repository.dao;

import java.util.List;

import org.toursys.repository.model.Season;

public interface SeasonDao {

	public void createSeason(Season season);

	public void updateSeason(Season season);

	public void deleteSeason(Season season);

	public List<Season> getAllSeason();

}
