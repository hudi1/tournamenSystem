package org.toursys.repository.dao.impl;

import org.toursys.repository.dao.TournamentDao;

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
import org.toursys.repository.model.Tournament;

public class TournamentDaoImpl implements TournamentDao {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected SqlEngineFactory sqlEngineFactory;
	protected SqlSessionFactory sqlSessionFactory;
			
	public TournamentDaoImpl() {
	}
			
	public TournamentDaoImpl(SqlEngineFactory sqlEngineFactory) {
		this.sqlEngineFactory = sqlEngineFactory;
	}
			
	public TournamentDaoImpl(SqlEngineFactory sqlEngineFactory, SqlSessionFactory sqlSessionFactory) {
		this.sqlEngineFactory = sqlEngineFactory;
		this.sqlSessionFactory = sqlSessionFactory;
	}
	

	public Tournament insert(SqlSession sqlSession, Tournament tournament, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("insert tournament: " + tournament + " " + sqlControl);
		}
		SqlCrudEngine sqlInsertTournament = sqlEngineFactory.getCheckedCrudEngine("INSERT_TOURNAMENT");
		int count = sqlInsertTournament.insert(sqlSession, tournament, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("insert tournament result: " + count + " " + tournament);
		}
		return (count > 0) ? tournament : null;
	}
	public Tournament insert(Tournament tournament, SqlControl sqlControl) {
		return insert(sqlSessionFactory.getSqlSession(), tournament, sqlControl);
	}
	public Tournament insert(SqlSession sqlSession, Tournament tournament) {
		return insert(sqlSession, tournament, null);
	}
	public Tournament insert(Tournament tournament) {
		return insert(tournament, null);
	}

	public Tournament get(SqlSession sqlSession, Tournament tournament, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("get get: " + tournament + " " + sqlControl);
		}
		SqlCrudEngine sqlGetEngineTournament = sqlEngineFactory.getCheckedCrudEngine("GET_TOURNAMENT");
		//sqlControl = getMoreResultClasses(tournament, sqlControl);
		Tournament tournamentGot = sqlGetEngineTournament.get(sqlSession, Tournament.class, tournament, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("get tournament result: " + tournamentGot);
		}
		return tournamentGot;
	}
	public Tournament get(Tournament tournament, SqlControl sqlControl) {
		return get(sqlSessionFactory.getSqlSession(), tournament, sqlControl);
	}
	public Tournament get(SqlSession sqlSession, Tournament tournament) {
		return get(sqlSession, tournament, null);
	}
	public Tournament get(Tournament tournament) {
		return get(tournament, null);
	}

	public int update(SqlSession sqlSession, Tournament tournament, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("update tournament: " + tournament + " " + sqlControl);
		}
		SqlCrudEngine sqlUpdateEngineTournament = sqlEngineFactory.getCheckedCrudEngine("UPDATE_TOURNAMENT");
		int count = sqlUpdateEngineTournament.update(sqlSession, tournament, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("update tournament result count: " + count);
		}
		return count;
	}
	public int update(Tournament tournament, SqlControl sqlControl) {
		return update(sqlSessionFactory.getSqlSession(), tournament, sqlControl);
	}
	public int update(SqlSession sqlSession, Tournament tournament) {
		return update(sqlSession, tournament, null);
	}
	public int update(Tournament tournament) {
		return update(tournament, null);
	}

	public int delete(SqlSession sqlSession, Tournament tournament, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("delete tournament: " + tournament + " " + sqlControl);
		}
		SqlCrudEngine sqlDeleteEngineTournament = sqlEngineFactory.getCheckedCrudEngine("DELETE_TOURNAMENT");
		int count = sqlDeleteEngineTournament.delete(sqlSession, tournament, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("delete tournament result count: " + count);
		}
		return count;
	}
	public int delete(Tournament tournament, SqlControl sqlControl) {
		return delete(sqlSessionFactory.getSqlSession(), tournament, sqlControl);
	}
	public int delete(SqlSession sqlSession, Tournament tournament) {
		return delete(sqlSession, tournament, null);
	}
	public int delete(Tournament tournament) {
		return delete(tournament, null);
	}

	public List<Tournament> list(SqlSession sqlSession, Tournament tournament, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("list tournament: " + tournament + " " + sqlControl);
		}
		SqlQueryEngine sqlEngineTournament = sqlEngineFactory.getCheckedQueryEngine("SELECT_TOURNAMENT");
		//sqlControl = getMoreResultClasses(tournament, sqlControl);
		List<Tournament> tournamentList = sqlEngineTournament.query(sqlSession, Tournament.class, tournament, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("list tournament size: " + ((tournamentList != null) ? tournamentList.size() : "null"));
		}
		return tournamentList;
	}
	public List<Tournament> list(Tournament tournament, SqlControl sqlControl) {
		return list(sqlSessionFactory.getSqlSession(), tournament, sqlControl);
	}
	public List<Tournament> list(SqlSession sqlSession, Tournament tournament) {
		return list(sqlSession, tournament, null);
	}
	public List<Tournament> list(Tournament tournament) {
		return list(tournament, null);
	}

	public int count(SqlSession sqlSession, Tournament tournament, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("count tournament: " + tournament + " " + sqlControl);
		}
		SqlQueryEngine sqlEngineTournament = sqlEngineFactory.getCheckedQueryEngine("SELECT_TOURNAMENT");
		//sqlControl = getMoreResultClasses(tournament, sqlControl);
		int count = sqlEngineTournament.queryCount(sqlSession, tournament, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("count: " + count);
		}
		return count;
	}
	public int count(Tournament tournament, SqlControl sqlControl) {
		return count(sqlSessionFactory.getSqlSession(), tournament, sqlControl);
	}
	public int count(SqlSession sqlSession, Tournament tournament) {
		return count(sqlSession, tournament, null);
	}
	public int count(Tournament tournament) {
		return count(tournament, null);
	}
}
