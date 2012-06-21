package org.toursys.repository.dao.impl;

import java.util.List;

import org.sqlproc.engine.SqlSession;
import org.toursys.repository.dao.TournamentDao;
import org.toursys.repository.form.TournamentForm;
import org.toursys.repository.model.Tournament;

public class TournamentDaoImpl extends BaseDaoImpl implements TournamentDao {

	@Override
	public void createTournament(Tournament tournament) {
		SqlSession session = getSqlSession();
		getCrudEngine("INSERT_TOURNAMENT").insert(session, tournament);
	}

	@Override
	public void updateTournament(Tournament tournament) {
		SqlSession session = getSqlSession();
		getCrudEngine("UPDATE_TOURNAMENT").update(session, tournament);
	}

	@Override
	public void deleteTournament(Tournament tournament) {
		SqlSession session = getSqlSession();
		getCrudEngine("DELETE_TOURNAMENT").delete(session, tournament);
	}

	public List<Tournament> findTournament(TournamentForm tournamentForm) {
		SqlSession session = getSqlSession();
		return getQueryEngine("GET_TOURNAMENT").query(session,
				Tournament.class, tournamentForm);
	}
}
