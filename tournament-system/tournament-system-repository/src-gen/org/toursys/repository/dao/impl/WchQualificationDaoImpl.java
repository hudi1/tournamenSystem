package org.toursys.repository.dao.impl;

import org.toursys.repository.dao.WchQualificationDao;

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
import org.toursys.repository.model.WchQualification;

public class WchQualificationDaoImpl implements WchQualificationDao {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected SqlEngineFactory sqlEngineFactory;
	protected SqlSessionFactory sqlSessionFactory;
			
	public WchQualificationDaoImpl() {
	}
			
	public WchQualificationDaoImpl(SqlEngineFactory sqlEngineFactory) {
		this.sqlEngineFactory = sqlEngineFactory;
	}
			
	public WchQualificationDaoImpl(SqlEngineFactory sqlEngineFactory, SqlSessionFactory sqlSessionFactory) {
		this.sqlEngineFactory = sqlEngineFactory;
		this.sqlSessionFactory = sqlSessionFactory;
	}
	

	public WchQualification insert(SqlSession sqlSession, WchQualification wchQualification, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("insert wchQualification: " + wchQualification + " " + sqlControl);
		}
		SqlCrudEngine sqlInsertWchQualification = sqlEngineFactory.getCheckedCrudEngine("INSERT_WCH_QUALIFICATION");
		int count = sqlInsertWchQualification.insert(sqlSession, wchQualification, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("insert wchQualification result: " + count + " " + wchQualification);
		}
		return (count > 0) ? wchQualification : null;
	}
	public WchQualification insert(WchQualification wchQualification, SqlControl sqlControl) {
		return insert(sqlSessionFactory.getSqlSession(), wchQualification, sqlControl);
	}
	public WchQualification insert(SqlSession sqlSession, WchQualification wchQualification) {
		return insert(sqlSession, wchQualification, null);
	}
	public WchQualification insert(WchQualification wchQualification) {
		return insert(wchQualification, null);
	}

	public WchQualification get(SqlSession sqlSession, WchQualification wchQualification, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("get get: " + wchQualification + " " + sqlControl);
		}
		SqlCrudEngine sqlGetEngineWchQualification = sqlEngineFactory.getCheckedCrudEngine("GET_WCH_QUALIFICATION");
		//sqlControl = getMoreResultClasses(wchQualification, sqlControl);
		WchQualification wchQualificationGot = sqlGetEngineWchQualification.get(sqlSession, WchQualification.class, wchQualification, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("get wchQualification result: " + wchQualificationGot);
		}
		return wchQualificationGot;
	}
	public WchQualification get(WchQualification wchQualification, SqlControl sqlControl) {
		return get(sqlSessionFactory.getSqlSession(), wchQualification, sqlControl);
	}
	public WchQualification get(SqlSession sqlSession, WchQualification wchQualification) {
		return get(sqlSession, wchQualification, null);
	}
	public WchQualification get(WchQualification wchQualification) {
		return get(wchQualification, null);
	}

	public int update(SqlSession sqlSession, WchQualification wchQualification, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("update wchQualification: " + wchQualification + " " + sqlControl);
		}
		SqlCrudEngine sqlUpdateEngineWchQualification = sqlEngineFactory.getCheckedCrudEngine("UPDATE_WCH_QUALIFICATION");
		int count = sqlUpdateEngineWchQualification.update(sqlSession, wchQualification, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("update wchQualification result count: " + count);
		}
		return count;
	}
	public int update(WchQualification wchQualification, SqlControl sqlControl) {
		return update(sqlSessionFactory.getSqlSession(), wchQualification, sqlControl);
	}
	public int update(SqlSession sqlSession, WchQualification wchQualification) {
		return update(sqlSession, wchQualification, null);
	}
	public int update(WchQualification wchQualification) {
		return update(wchQualification, null);
	}

	public int delete(SqlSession sqlSession, WchQualification wchQualification, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("delete wchQualification: " + wchQualification + " " + sqlControl);
		}
		SqlCrudEngine sqlDeleteEngineWchQualification = sqlEngineFactory.getCheckedCrudEngine("DELETE_WCH_QUALIFICATION");
		int count = sqlDeleteEngineWchQualification.delete(sqlSession, wchQualification, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("delete wchQualification result count: " + count);
		}
		return count;
	}
	public int delete(WchQualification wchQualification, SqlControl sqlControl) {
		return delete(sqlSessionFactory.getSqlSession(), wchQualification, sqlControl);
	}
	public int delete(SqlSession sqlSession, WchQualification wchQualification) {
		return delete(sqlSession, wchQualification, null);
	}
	public int delete(WchQualification wchQualification) {
		return delete(wchQualification, null);
	}

	public List<WchQualification> list(SqlSession sqlSession, WchQualification wchQualification, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("list wchQualification: " + wchQualification + " " + sqlControl);
		}
		SqlQueryEngine sqlEngineWchQualification = sqlEngineFactory.getCheckedQueryEngine("SELECT_WCH_QUALIFICATION");
		//sqlControl = getMoreResultClasses(wchQualification, sqlControl);
		List<WchQualification> wchQualificationList = sqlEngineWchQualification.query(sqlSession, WchQualification.class, wchQualification, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("list wchQualification size: " + ((wchQualificationList != null) ? wchQualificationList.size() : "null"));
		}
		return wchQualificationList;
	}
	public List<WchQualification> list(WchQualification wchQualification, SqlControl sqlControl) {
		return list(sqlSessionFactory.getSqlSession(), wchQualification, sqlControl);
	}
	public List<WchQualification> list(SqlSession sqlSession, WchQualification wchQualification) {
		return list(sqlSession, wchQualification, null);
	}
	public List<WchQualification> list(WchQualification wchQualification) {
		return list(wchQualification, null);
	}

	public int count(SqlSession sqlSession, WchQualification wchQualification, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("count wchQualification: " + wchQualification + " " + sqlControl);
		}
		SqlQueryEngine sqlEngineWchQualification = sqlEngineFactory.getCheckedQueryEngine("SELECT_WCH_QUALIFICATION");
		//sqlControl = getMoreResultClasses(wchQualification, sqlControl);
		int count = sqlEngineWchQualification.queryCount(sqlSession, wchQualification, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("count: " + count);
		}
		return count;
	}
	public int count(WchQualification wchQualification, SqlControl sqlControl) {
		return count(sqlSessionFactory.getSqlSession(), wchQualification, sqlControl);
	}
	public int count(SqlSession sqlSession, WchQualification wchQualification) {
		return count(sqlSession, wchQualification, null);
	}
	public int count(WchQualification wchQualification) {
		return count(wchQualification, null);
	}
}
