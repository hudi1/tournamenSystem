package org.toursys.repository.dao.impl;

import java.util.List;

import org.sqlproc.engine.SqlSession;
import org.toursys.repository.dao.PlayerResultDao;
import org.toursys.repository.form.PlayerResultForm;
import org.toursys.repository.model.PlayerResult;

public class PlayerResultDaoImpl extends BaseDaoImpl implements PlayerResultDao {

	@Override
	public void createPlayerResult(PlayerResult playerResult) {
		SqlSession session = getSqlSession();
		getCrudEngine("INSERT_PLAYER_RESULT").insert(session, playerResult);
	}

	@Override
	public void updatePlayerResult(PlayerResult playerResult) {
		SqlSession session = getSqlSession();
		getCrudEngine("UPDATE_PLAYER_RESULT").update(session, playerResult);

	}

	@Override
	public void deletePlayerResult(PlayerResult playerResult) {
		SqlSession session = getSqlSession();
		getCrudEngine("DELETE_PLAYER_RESULT").delete(session, playerResult);
	}

	@Override
	public List<PlayerResult> findPlayerResult(PlayerResultForm playerResultForm) {
		SqlSession session = getSqlSession();
		return getQueryEngine("GET_PLAYER_RESULT").query(session,
				PlayerResult.class, playerResultForm);
	}
}
