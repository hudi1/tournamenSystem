package org.tahom.repository.dao;

import org.tahom.repository.model.Tournament;

public interface FinalStandingExtDao extends FinalStandingDao {

	public int deleteByTournament(Tournament tournament);

}
