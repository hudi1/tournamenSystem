package org.tahom.processor.service.season;

import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;
import org.tahom.repository.dao.SeasonDao;
import org.tahom.repository.model.Season;
import org.tahom.repository.model.User;

public class SeasonService {

	@Inject
	private SeasonDao seasonDao;

	@Transactional
	public Season createSeason(User user, Season season) {
		return seasonDao.insert(season._setUser(new User()._setId(user.getId())));
	}

	@Transactional
	public int updateSeason(Season season) {
		return seasonDao.update(season);
	}

	@Transactional
	public int deleteSeason(Season season) {
		return seasonDao.delete(season);
	}

	@Transactional(readOnly = true)
	public List<Season> getUserSeasons(User user) {
		return seasonDao.list(new Season()._setUser(user)._setInit(Season.Association.tournaments));
	}

	@Transactional(readOnly = true)
	public List<Season> getAllSeasons() {
		return seasonDao.list(new Season()._setInit(Season.Association.tournaments));
	}

}