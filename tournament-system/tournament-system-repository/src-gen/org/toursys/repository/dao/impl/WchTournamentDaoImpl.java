package org.toursys.repository.dao.impl;

import org.toursys.repository.dao.WchTournamentDao;

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
import org.toursys.repository.model.WchTournament;

public class WchTournamentDaoImpl implements WchTournamentDao {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected SqlEngineFactory sqlEngineFactory;
	protected SqlSessionFactory sqlSessionFactory;
			
	public WchTournamentDaoImpl() {
	}
			
	public WchTournamentDaoImpl(SqlEngineFactory sqlEngineFactory) {
		this.sqlEngineFactory = sqlEngineFactory;
	}
			
	public WchTournamentDaoImpl(SqlEngineFactory sqlEngineFactory, SqlSessionFactory sqlSessionFactory) {
		this.sqlEngineFactory = sqlEngineFactory;
		this.sqlSessionFactory = sqlSessionFactory;
	}
	

	public WchTournament insert(SqlSession sqlSession, WchTournament wchTournament, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("insert wchTournament: " + wchTournament + " " + sqlControl);
		}
		SqlCrudEngine sqlInsertWchTournament = sqlEngineFactory.getCheckedCrudEngine("INSERT_WCH_TOURNAMENT");
		int count = sqlInsertWchTournament.insert(sqlSession, wchTournament, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("insert wchTournament result: " + count + " " + wchTournament);
		}
		return (count > 0) ? wchTournament : null;
	}
	public WchTournament insert(WchTournament wchTournament, SqlControl sqlControl) {
		return insert(sqlSessionFactory.getSqlSession(), wchTournament, sqlControl);
	}
	public WchTournament insert(SqlSession sqlSession, WchTournament wchTournament) {
		return insert(sqlSession, wchTournament, null);
	}
	public WchTournament insert(WchTournament wchTournament) {
		return insert(wchTournament, null);
	}

	public WchTournament get(SqlSession sqlSession, WchTournament wchTournament, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("get get: " + wchTournament + " " + sqlControl);
		}
		SqlCrudEngine sqlGetEngineWchTournament = sqlEngineFactory.getCheckedCrudEngine("GET_WCH_TOURNAMENT");
		//sqlControl = getMoreResultClasses(wchTournament, sqlControl);
		WchTournament wchTournamentGot = sqlGetEngineWchTournament.get(sqlSession, WchTournament.class, wchTournament, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("get wchTournament result: " + wchTournamentGot);
		}
		return wchTournamentGot;
	}
	public WchTournament get(WchTournament wchTournament, SqlControl sqlControl) {
		return get(sqlSessionFactory.getSqlSession(), wchTournament, sqlControl);
	}
	public WchTournament get(SqlSession sqlSession, WchTournament wchTournament) {
		return get(sqlSession, wchTournament, null);
	}
	public WchTournament get(WchTournament wchTournament) {
		return get(wchTournament, null);
	}

	public int update(SqlSession sqlSession, WchTournament wchTournament, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("update wchTournament: " + wchTournament + " " + sqlControl);
		}
		SqlCrudEngine sqlUpdateEngineWchTournament = sqlEngineFactory.getCheckedCrudEngine("UPDATE_WCH_TOURNAMENT");
		int count = sqlUpdateEngineWchTournament.update(sqlSession, wchTournament, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("update wchTournament result count: " + count);
		}
		return count;
	}
	public int update(WchTournament wchTournament, SqlControl sqlControl) {
		return update(sqlSessionFactory.getSqlSession(), wchTournament, sqlControl);
	}
	public int update(SqlSession sqlSession, WchTournament wchTournament) {
		return update(sqlSession, wchTournament, null);
	}
	public int update(WchTournament wchTournament) {
		return update(wchTournament, null);
	}

	public int delete(SqlSession sqlSession, WchTournament wchTournament, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("delete wchTournament: " + wchTournament + " " + sqlControl);
		}
		SqlCrudEngine sqlDeleteEngineWchTournament = sqlEngineFactory.getCheckedCrudEngine("DELETE_WCH_TOURNAMENT");
		int count = sqlDeleteEngineWchTournament.delete(sqlSession, wchTournament, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("delete wchTournament result count: " + count);
		}
		return count;
	}
	public int delete(WchTournament wchTournament, SqlControl sqlControl) {
		return delete(sqlSessionFactory.getSqlSession(), wchTournament, sqlControl);
	}
	public int delete(SqlSession sqlSession, WchTournament wchTournament) {
		return delete(sqlSession, wchTournament, null);
	}
	public int delete(WchTournament wchTournament) {
		return delete(wchTournament, null);
	}

	public List<WchTournament> list(SqlSession sqlSession, WchTournament wchTournament, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("list wchTournament: " + wchTournament + " " + sqlControl);
		}
		SqlQueryEngine sqlEngineWchTournament = sqlEngineFactory.getCheckedQueryEngine("SELECT_WCH_TOURNAMENT");
		//sqlControl = getMoreResultClasses(wchTournament, sqlControl);
		List<WchTournament> wchTournamentList = sqlEngineWchTournament.query(sqlSession, WchTournament.class, wchTournament, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("list wchTournament size: " + ((wchTournamentList != null) ? wchTournamentList.size() : "null"));
		}
		return wchTournamentList;
	}
	public List<WchTournament> list(WchTournament wchTournament, SqlControl sqlControl) {
		return list(sqlSessionFactory.getSqlSession(), wchTournament, sqlControl);
	}
	public List<WchTournament> list(SqlSession sqlSession, WchTournament wchTournament) {
		return list(sqlSession, wchTournament, null);
	}
	public List<WchTournament> list(WchTournament wchTournament) {
		return list(wchTournament, null);
	}

	public int count(SqlSession sqlSession, WchTournament wchTournament, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("count wchTournament: " + wchTournament + " " + sqlControl);
		}
		SqlQueryEngine sqlEngineWchTournament = sqlEngineFactory.getCheckedQueryEngine("SELECT_WCH_TOURNAMENT");
		//sqlControl = getMoreResultClasses(wchTournament, sqlControl);
		int count = sqlEngineWchTournament.queryCount(sqlSession, wchTournament, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("count: " + count);
		}
		return count;
	}
	public int count(WchTournament wchTournament, SqlControl sqlControl) {
		return count(sqlSessionFactory.getSqlSession(), wchTournament, sqlControl);
	}
	public int count(SqlSession sqlSession, WchTournament wchTournament) {
		return count(sqlSession, wchTournament, null);
	}
	public int count(WchTournament wchTournament) {
		return count(wchTournament, null);
	}
}
