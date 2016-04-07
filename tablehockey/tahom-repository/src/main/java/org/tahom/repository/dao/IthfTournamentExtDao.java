package org.tahom.repository.dao;

import org.tahom.repository.model.IthfTournament;

public interface IthfTournamentExtDao extends IthfTournamentDao {

	public int customCount(IthfTournament tournament);

}
