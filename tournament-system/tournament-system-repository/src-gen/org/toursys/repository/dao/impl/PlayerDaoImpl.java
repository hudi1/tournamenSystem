package org.toursys.repository.dao.impl;

import org.toursys.repository.dao.PlayerDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlproc.engine.SqlControl;
import org.sqlproc.engine.SqlCrudEngine;
import org.sqlproc.engine.SqlEngineFactory;
import org.sqlproc.engine.SqlQueryEngine;
import org.sqlproc.engine.SqlProcedureEngine;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlSessionFactory;
import org.sqlproc.engine.impl.SqlStandardControl;
import org.toursys.repository.model.Player;

public class PlayerDaoImpl implements PlayerDao {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected SqlEngineFactory sqlEngineFactory;
	protected SqlSessionFactory sqlSessionFactory;
			
	public PlayerDaoImpl() {
	}
			
	public PlayerDaoImpl(SqlEngineFactory sqlEngineFactory) {
		this.sqlEngineFactory = sqlEngineFactory;
	}
			
	public PlayerDaoImpl(SqlEngineFactory sqlEngineFactory, SqlSessionFactory sqlSessionFactory) {
		this.sqlEngineFactory = sqlEngineFactory;
		this.sqlSessionFactory = sqlSessionFactory;
	}
	

	public Player insert(SqlSession sqlSession, Player player, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("insert player: " + player + " " + sqlControl);
		}
		SqlCrudEngine sqlInsertPlayer = sqlEngineFactory.getCheckedCrudEngine("INSERT_PLAYER");
		int count = sqlInsertPlayer.insert(sqlSession, player, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("insert player result: " + count + " " + player);
		}
		return (count > 0) ? player : null;
	}
	public Player insert(Player player, SqlControl sqlControl) {
		return insert(sqlSessionFactory.getSqlSession(), player, sqlControl);
	}
	public Player insert(SqlSession sqlSession, Player player) {
		return insert(sqlSession, player, null);
	}
	public Player insert(Player player) {
		return insert(player, null);
	}

	public Player get(SqlSession sqlSession, Player player, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("get get: " + player + " " + sqlControl);
		}
		SqlCrudEngine sqlGetEnginePlayer = sqlEngineFactory.getCheckedCrudEngine("GET_PLAYER");
		//sqlControl = getMoreResultClasses(player, sqlControl);
		Player playerGot = sqlGetEnginePlayer.get(sqlSession, Player.class, player, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("get player result: " + playerGot);
		}
		return playerGot;
	}
	public Player get(Player player, SqlControl sqlControl) {
		return get(sqlSessionFactory.getSqlSession(), player, sqlControl);
	}
	public Player get(SqlSession sqlSession, Player player) {
		return get(sqlSession, player, null);
	}
	public Player get(Player player) {
		return get(player, null);
	}

	public int update(SqlSession sqlSession, Player player, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("update player: " + player + " " + sqlControl);
		}
		SqlCrudEngine sqlUpdateEnginePlayer = sqlEngineFactory.getCheckedCrudEngine("UPDATE_PLAYER");
		int count = sqlUpdateEnginePlayer.update(sqlSession, player, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("update player result count: " + count);
		}
		return count;
	}
	public int update(Player player, SqlControl sqlControl) {
		return update(sqlSessionFactory.getSqlSession(), player, sqlControl);
	}
	public int update(SqlSession sqlSession, Player player) {
		return update(sqlSession, player, null);
	}
	public int update(Player player) {
		return update(player, null);
	}

	public int delete(SqlSession sqlSession, Player player, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("delete player: " + player + " " + sqlControl);
		}
		SqlCrudEngine sqlDeleteEnginePlayer = sqlEngineFactory.getCheckedCrudEngine("DELETE_PLAYER");
		int count = sqlDeleteEnginePlayer.delete(sqlSession, player, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("delete player result count: " + count);
		}
		return count;
	}
	public int delete(Player player, SqlControl sqlControl) {
		return delete(sqlSessionFactory.getSqlSession(), player, sqlControl);
	}
	public int delete(SqlSession sqlSession, Player player) {
		return delete(sqlSession, player, null);
	}
	public int delete(Player player) {
		return delete(player, null);
	}

	public List<Player> list(SqlSession sqlSession, Player player, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("list player: " + player + " " + sqlControl);
		}
		SqlQueryEngine sqlEnginePlayer = sqlEngineFactory.getCheckedQueryEngine("SELECT_PLAYER");
		//sqlControl = getMoreResultClasses(player, sqlControl);
		List<Player> playerList = sqlEnginePlayer.query(sqlSession, Player.class, player, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("list player size: " + ((playerList != null) ? playerList.size() : "null"));
		}
		return playerList;
	}
	public List<Player> list(Player player, SqlControl sqlControl) {
		return list(sqlSessionFactory.getSqlSession(), player, sqlControl);
	}
	public List<Player> list(SqlSession sqlSession, Player player) {
		return list(sqlSession, player, null);
	}
	public List<Player> list(Player player) {
		return list(player, null);
	}

	public int count(SqlSession sqlSession, Player player, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("count player: " + player + " " + sqlControl);
		}
		SqlQueryEngine sqlEnginePlayer = sqlEngineFactory.getCheckedQueryEngine("SELECT_PLAYER");
		//sqlControl = getMoreResultClasses(player, sqlControl);
		int count = sqlEnginePlayer.queryCount(sqlSession, player, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("count: " + count);
		}
		return count;
	}
	public int count(Player player, SqlControl sqlControl) {
		return count(sqlSessionFactory.getSqlSession(), player, sqlControl);
	}
	public int count(SqlSession sqlSession, Player player) {
		return count(sqlSession, player, null);
	}
	public int count(Player player) {
		return count(player, null);
	}
}
