package org.toursys.repository.dao.impl;

import java.util.List;

import org.sqlproc.engine.SqlSession;
import org.toursys.repository.dao.PlayerDao;
import org.toursys.repository.model.Player;

public class PlayerDaoImpl extends BaseDaoImpl implements PlayerDao {

	@Override
	public void createPlayer(Player player) {
		SqlSession session = getSqlSession();
		getCrudEngine("INSERT_PLAYER").insert(session, player);
	}

	@Override
	public void updatePlayer(Player player) {
		SqlSession session = getSqlSession();
		getCrudEngine("UPDATE_PLAYER").update(session, player);
	}

	@Override
	public void deletePlayer(Player player) {
		SqlSession session = getSqlSession();
		getCrudEngine("DELETE_PLAYER").delete(session, player);
	}

	@Override
	public List<Player> getAllPlayer() {
		SqlSession session = getSqlSession();
		return getQueryEngine("GET_ALL_PLAYER").query(session, Player.class);
	}

}
