package org.toursys.repository.dao.impl;

import org.toursys.repository.dao.PlayOffResultDao;

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
import org.toursys.repository.model.PlayOffResult;

public class PlayOffResultDaoImpl implements PlayOffResultDao {
  protected final Logger logger = LoggerFactory.getLogger(getClass());

  protected SqlEngineFactory sqlEngineFactory;
  protected SqlSessionFactory sqlSessionFactory;
    	
  public PlayOffResultDaoImpl(SqlEngineFactory sqlEngineFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
  }
    	
  public PlayOffResultDaoImpl(SqlEngineFactory sqlEngineFactory, SqlSessionFactory sqlSessionFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
    this.sqlSessionFactory = sqlSessionFactory;
  }
  
  
  public PlayOffResult insert(SqlSession sqlSession, PlayOffResult playOffResult, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("insert playOffResult: " + playOffResult + " " + sqlControl);
    }
    SqlCrudEngine sqlInsertPlayOffResult = sqlEngineFactory.getCheckedCrudEngine("INSERT_PLAY_OFF_RESULT");
    int count = sqlInsertPlayOffResult.insert(sqlSession, playOffResult, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("insert playOffResult result: " + count + " " + playOffResult);
    }
    return (count > 0) ? playOffResult : null;
  }
  
  public PlayOffResult insert(PlayOffResult playOffResult, SqlControl sqlControl) {
  	return insert(sqlSessionFactory.getSqlSession(), playOffResult, sqlControl);
  }
  
  public PlayOffResult insert(SqlSession sqlSession, PlayOffResult playOffResult) {
    return insert(sqlSession, playOffResult, null);
  }
  
  public PlayOffResult insert(PlayOffResult playOffResult) {
    return insert(playOffResult, null);
  }
  
  public PlayOffResult get(SqlSession sqlSession, PlayOffResult playOffResult, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("get get: " + playOffResult + " " + sqlControl);
    }
    SqlCrudEngine sqlGetEnginePlayOffResult = sqlEngineFactory.getCheckedCrudEngine("GET_PLAY_OFF_RESULT");
    //sqlControl = getMoreResultClasses(playOffResult, sqlControl);
    PlayOffResult playOffResultGot = sqlGetEnginePlayOffResult.get(sqlSession, PlayOffResult.class, playOffResult, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("get playOffResult result: " + playOffResultGot);
    }
    return playOffResultGot;
  }
  	
  public PlayOffResult get(PlayOffResult playOffResult, SqlControl sqlControl) {
  	return get(sqlSessionFactory.getSqlSession(), playOffResult, sqlControl);
  }
  
  public PlayOffResult get(SqlSession sqlSession, PlayOffResult playOffResult) {
    return get(sqlSession, playOffResult, null);
  }
  
  public PlayOffResult get(PlayOffResult playOffResult) {
    return get(playOffResult, null);
  }
  
  public int update(SqlSession sqlSession, PlayOffResult playOffResult, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("update playOffResult: " + playOffResult + " " + sqlControl);
    }
    SqlCrudEngine sqlUpdateEnginePlayOffResult = sqlEngineFactory.getCheckedCrudEngine("UPDATE_PLAY_OFF_RESULT");
    int count = sqlUpdateEnginePlayOffResult.update(sqlSession, playOffResult, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("update playOffResult result count: " + count);
    }
    return count;
  }
  
  public int update(PlayOffResult playOffResult, SqlControl sqlControl) {
  	return update(sqlSessionFactory.getSqlSession(), playOffResult, sqlControl);
  }
  
  public int update(SqlSession sqlSession, PlayOffResult playOffResult) {
    return update(sqlSession, playOffResult, null);
  }
  
  public int update(PlayOffResult playOffResult) {
    return update(playOffResult, null);
  }
  
  public int delete(SqlSession sqlSession, PlayOffResult playOffResult, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("delete playOffResult: " + playOffResult + " " + sqlControl);
    }
    SqlCrudEngine sqlDeleteEnginePlayOffResult = sqlEngineFactory.getCheckedCrudEngine("DELETE_PLAY_OFF_RESULT");
    int count = sqlDeleteEnginePlayOffResult.delete(sqlSession, playOffResult, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("delete playOffResult result count: " + count);
    }
    return count;
  }
  
  public int delete(PlayOffResult playOffResult, SqlControl sqlControl) {
  	return delete(sqlSessionFactory.getSqlSession(), playOffResult, sqlControl);
  }
  
  public int delete(SqlSession sqlSession, PlayOffResult playOffResult) {
    return delete(sqlSession, playOffResult, null);
  }
  
  public int delete(PlayOffResult playOffResult) {
    return delete(playOffResult, null);
  }
  
  public List<PlayOffResult> list(SqlSession sqlSession, PlayOffResult playOffResult, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("list playOffResult: " + playOffResult + " " + sqlControl);
    }
    SqlQueryEngine sqlEnginePlayOffResult = sqlEngineFactory.getCheckedQueryEngine("SELECT_PLAY_OFF_RESULT");
    //sqlControl = getMoreResultClasses(playOffResult, sqlControl);
    List<PlayOffResult> playOffResultList = sqlEnginePlayOffResult.query(sqlSession, PlayOffResult.class, playOffResult, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("list playOffResult size: " + ((playOffResultList != null) ? playOffResultList.size() : "null"));
    }
    return playOffResultList;
  }
  
  public List<PlayOffResult> list(PlayOffResult playOffResult, SqlControl sqlControl) {
  	return list(sqlSessionFactory.getSqlSession(), playOffResult, sqlControl);
  }
  
  public List<PlayOffResult> list(SqlSession sqlSession, PlayOffResult playOffResult) {
    return list(sqlSession, playOffResult, null);
  }
  
  public List<PlayOffResult> list(PlayOffResult playOffResult) {
    return list(playOffResult, null);
  }
  
  public int count(SqlSession sqlSession, PlayOffResult playOffResult, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("count playOffResult: " + playOffResult + " " + sqlControl);
    }
    SqlQueryEngine sqlEnginePlayOffResult = sqlEngineFactory.getCheckedQueryEngine("SELECT_PLAY_OFF_RESULT");
    //sqlControl = getMoreResultClasses(playOffResult, sqlControl);
    int count = sqlEnginePlayOffResult.queryCount(sqlSession, playOffResult, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("count: " + count);
    }
    return count;
  }
  
  public int count(PlayOffResult playOffResult, SqlControl sqlControl) {
  	return count(sqlSessionFactory.getSqlSession(), playOffResult, sqlControl);
  }
  
  public int count(SqlSession sqlSession, PlayOffResult playOffResult) {
    return count(sqlSession, playOffResult, null);
  }
  
  public int count(PlayOffResult playOffResult) {
    return count(playOffResult, null);
  }
}
