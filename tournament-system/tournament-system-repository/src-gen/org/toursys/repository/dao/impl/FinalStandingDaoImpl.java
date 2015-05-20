package org.toursys.repository.dao.impl;

import org.toursys.repository.dao.FinalStandingDao;

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
import org.toursys.repository.model.FinalStanding;

public class FinalStandingDaoImpl implements FinalStandingDao {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected SqlEngineFactory sqlEngineFactory;
	protected SqlSessionFactory sqlSessionFactory;
			
	public FinalStandingDaoImpl() {
	}
			
	public FinalStandingDaoImpl(SqlEngineFactory sqlEngineFactory) {
		this.sqlEngineFactory = sqlEngineFactory;
	}
			
	public FinalStandingDaoImpl(SqlEngineFactory sqlEngineFactory, SqlSessionFactory sqlSessionFactory) {
		this.sqlEngineFactory = sqlEngineFactory;
		this.sqlSessionFactory = sqlSessionFactory;
	}
	

	public FinalStanding insert(SqlSession sqlSession, FinalStanding finalStanding, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("insert finalStanding: " + finalStanding + " " + sqlControl);
		}
		SqlCrudEngine sqlInsertFinalStanding = sqlEngineFactory.getCheckedCrudEngine("INSERT_FINAL_STANDING");
		int count = sqlInsertFinalStanding.insert(sqlSession, finalStanding, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("insert finalStanding result: " + count + " " + finalStanding);
		}
		return (count > 0) ? finalStanding : null;
	}
	public FinalStanding insert(FinalStanding finalStanding, SqlControl sqlControl) {
		return insert(sqlSessionFactory.getSqlSession(), finalStanding, sqlControl);
	}
	public FinalStanding insert(SqlSession sqlSession, FinalStanding finalStanding) {
		return insert(sqlSession, finalStanding, null);
	}
	public FinalStanding insert(FinalStanding finalStanding) {
		return insert(finalStanding, null);
	}

	public FinalStanding get(SqlSession sqlSession, FinalStanding finalStanding, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("get get: " + finalStanding + " " + sqlControl);
		}
		SqlCrudEngine sqlGetEngineFinalStanding = sqlEngineFactory.getCheckedCrudEngine("GET_FINAL_STANDING");
		//sqlControl = getMoreResultClasses(finalStanding, sqlControl);
		FinalStanding finalStandingGot = sqlGetEngineFinalStanding.get(sqlSession, FinalStanding.class, finalStanding, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("get finalStanding result: " + finalStandingGot);
		}
		return finalStandingGot;
	}
	public FinalStanding get(FinalStanding finalStanding, SqlControl sqlControl) {
		return get(sqlSessionFactory.getSqlSession(), finalStanding, sqlControl);
	}
	public FinalStanding get(SqlSession sqlSession, FinalStanding finalStanding) {
		return get(sqlSession, finalStanding, null);
	}
	public FinalStanding get(FinalStanding finalStanding) {
		return get(finalStanding, null);
	}

	public int update(SqlSession sqlSession, FinalStanding finalStanding, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("update finalStanding: " + finalStanding + " " + sqlControl);
		}
		SqlCrudEngine sqlUpdateEngineFinalStanding = sqlEngineFactory.getCheckedCrudEngine("UPDATE_FINAL_STANDING");
		int count = sqlUpdateEngineFinalStanding.update(sqlSession, finalStanding, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("update finalStanding result count: " + count);
		}
		return count;
	}
	public int update(FinalStanding finalStanding, SqlControl sqlControl) {
		return update(sqlSessionFactory.getSqlSession(), finalStanding, sqlControl);
	}
	public int update(SqlSession sqlSession, FinalStanding finalStanding) {
		return update(sqlSession, finalStanding, null);
	}
	public int update(FinalStanding finalStanding) {
		return update(finalStanding, null);
	}

	public int delete(SqlSession sqlSession, FinalStanding finalStanding, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("delete finalStanding: " + finalStanding + " " + sqlControl);
		}
		SqlCrudEngine sqlDeleteEngineFinalStanding = sqlEngineFactory.getCheckedCrudEngine("DELETE_FINAL_STANDING");
		int count = sqlDeleteEngineFinalStanding.delete(sqlSession, finalStanding, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("delete finalStanding result count: " + count);
		}
		return count;
	}
	public int delete(FinalStanding finalStanding, SqlControl sqlControl) {
		return delete(sqlSessionFactory.getSqlSession(), finalStanding, sqlControl);
	}
	public int delete(SqlSession sqlSession, FinalStanding finalStanding) {
		return delete(sqlSession, finalStanding, null);
	}
	public int delete(FinalStanding finalStanding) {
		return delete(finalStanding, null);
	}

	public List<FinalStanding> list(SqlSession sqlSession, FinalStanding finalStanding, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("list finalStanding: " + finalStanding + " " + sqlControl);
		}
		SqlQueryEngine sqlEngineFinalStanding = sqlEngineFactory.getCheckedQueryEngine("SELECT_FINAL_STANDING");
		//sqlControl = getMoreResultClasses(finalStanding, sqlControl);
		List<FinalStanding> finalStandingList = sqlEngineFinalStanding.query(sqlSession, FinalStanding.class, finalStanding, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("list finalStanding size: " + ((finalStandingList != null) ? finalStandingList.size() : "null"));
		}
		return finalStandingList;
	}
	public List<FinalStanding> list(FinalStanding finalStanding, SqlControl sqlControl) {
		return list(sqlSessionFactory.getSqlSession(), finalStanding, sqlControl);
	}
	public List<FinalStanding> list(SqlSession sqlSession, FinalStanding finalStanding) {
		return list(sqlSession, finalStanding, null);
	}
	public List<FinalStanding> list(FinalStanding finalStanding) {
		return list(finalStanding, null);
	}

	public int count(SqlSession sqlSession, FinalStanding finalStanding, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("count finalStanding: " + finalStanding + " " + sqlControl);
		}
		SqlQueryEngine sqlEngineFinalStanding = sqlEngineFactory.getCheckedQueryEngine("SELECT_FINAL_STANDING");
		//sqlControl = getMoreResultClasses(finalStanding, sqlControl);
		int count = sqlEngineFinalStanding.queryCount(sqlSession, finalStanding, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("count: " + count);
		}
		return count;
	}
	public int count(FinalStanding finalStanding, SqlControl sqlControl) {
		return count(sqlSessionFactory.getSqlSession(), finalStanding, sqlControl);
	}
	public int count(SqlSession sqlSession, FinalStanding finalStanding) {
		return count(sqlSession, finalStanding, null);
	}
	public int count(FinalStanding finalStanding) {
		return count(finalStanding, null);
	}
}
