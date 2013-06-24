package org.toursys.repository.dao.impl;

import org.toursys.repository.dao.PlayerResultDao;

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
import org.toursys.repository.model.PlayerResult;

public class PlayerResultDaoImpl implements PlayerResultDao {
  protected final Logger logger = LoggerFactory.getLogger(getClass());

  protected SqlEngineFactory sqlEngineFactory;
  protected SqlSessionFactory sqlSessionFactory;
    	
  public PlayerResultDaoImpl(SqlEngineFactory sqlEngineFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
  }
    	
  public PlayerResultDaoImpl(SqlEngineFactory sqlEngineFactory, SqlSessionFactory sqlSessionFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
    this.sqlSessionFactory = sqlSessionFactory;
  }
  
  
  public PlayerResult insert(SqlSession sqlSession, PlayerResult playerResult, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("insert playerResult: " + playerResult + " " + sqlControl);
    }
    SqlCrudEngine sqlInsertPlayerResult = sqlEngineFactory.getCheckedCrudEngine("INSERT_PLAYER_RESULT");
    int count = sqlInsertPlayerResult.insert(sqlSession, playerResult, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("insert playerResult result: " + count + " " + playerResult);
    }
    return (count > 0) ? playerResult : null;
  }
  
  public PlayerResult insert(PlayerResult playerResult, SqlControl sqlControl) {
  	return insert(sqlSessionFactory.getSqlSession(), playerResult, sqlControl);
  }
  
  public PlayerResult insert(SqlSession sqlSession, PlayerResult playerResult) {
    return insert(sqlSession, playerResult, null);
  }
  
  public PlayerResult insert(PlayerResult playerResult) {
    return insert(playerResult, null);
  }
  
  public PlayerResult get(SqlSession sqlSession, PlayerResult playerResult, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("get get: " + playerResult + " " + sqlControl);
    }
    SqlCrudEngine sqlGetEnginePlayerResult = sqlEngineFactory.getCheckedCrudEngine("GET_PLAYER_RESULT");
    //sqlControl = getMoreResultClasses(playerResult, sqlControl);
    PlayerResult playerResultGot = sqlGetEnginePlayerResult.get(sqlSession, PlayerResult.class, playerResult, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("get playerResult result: " + playerResultGot);
    }
    return playerResultGot;
  }
  	
  public PlayerResult get(PlayerResult playerResult, SqlControl sqlControl) {
  	return get(sqlSessionFactory.getSqlSession(), playerResult, sqlControl);
  }
  
  public PlayerResult get(SqlSession sqlSession, PlayerResult playerResult) {
    return get(sqlSession, playerResult, null);
  }
  
  public PlayerResult get(PlayerResult playerResult) {
    return get(playerResult, null);
  }
  
  public int update(SqlSession sqlSession, PlayerResult playerResult, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("update playerResult: " + playerResult + " " + sqlControl);
    }
    SqlCrudEngine sqlUpdateEnginePlayerResult = sqlEngineFactory.getCheckedCrudEngine("UPDATE_PLAYER_RESULT");
    int count = sqlUpdateEnginePlayerResult.update(sqlSession, playerResult, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("update playerResult result count: " + count);
    }
    return count;
  }
  
  public int update(PlayerResult playerResult, SqlControl sqlControl) {
  	return update(sqlSessionFactory.getSqlSession(), playerResult, sqlControl);
  }
  
  public int update(SqlSession sqlSession, PlayerResult playerResult) {
    return update(sqlSession, playerResult, null);
  }
  
  public int update(PlayerResult playerResult) {
    return update(playerResult, null);
  }
  
  public int delete(SqlSession sqlSession, PlayerResult playerResult, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("delete playerResult: " + playerResult + " " + sqlControl);
    }
    SqlCrudEngine sqlDeleteEnginePlayerResult = sqlEngineFactory.getCheckedCrudEngine("DELETE_PLAYER_RESULT");
    int count = sqlDeleteEnginePlayerResult.delete(sqlSession, playerResult, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("delete playerResult result count: " + count);
    }
    return count;
  }
  
  public int delete(PlayerResult playerResult, SqlControl sqlControl) {
  	return delete(sqlSessionFactory.getSqlSession(), playerResult, sqlControl);
  }
  
  public int delete(SqlSession sqlSession, PlayerResult playerResult) {
    return delete(sqlSession, playerResult, null);
  }
  
  public int delete(PlayerResult playerResult) {
    return delete(playerResult, null);
  }
  
  public List<PlayerResult> list(SqlSession sqlSession, PlayerResult playerResult, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("list playerResult: " + playerResult + " " + sqlControl);
    }
    SqlQueryEngine sqlEnginePlayerResult = sqlEngineFactory.getCheckedQueryEngine("SELECT_PLAYER_RESULT");
    //sqlControl = getMoreResultClasses(playerResult, sqlControl);
    List<PlayerResult> playerResultList = sqlEnginePlayerResult.query(sqlSession, PlayerResult.class, playerResult, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("list playerResult size: " + ((playerResultList != null) ? playerResultList.size() : "null"));
    }
    return playerResultList;
  }
  
  public List<PlayerResult> list(PlayerResult playerResult, SqlControl sqlControl) {
  	return list(sqlSessionFactory.getSqlSession(), playerResult, sqlControl);
  }
  
  public List<PlayerResult> list(SqlSession sqlSession, PlayerResult playerResult) {
    return list(sqlSession, playerResult, null);
  }
  
  public List<PlayerResult> list(PlayerResult playerResult) {
    return list(playerResult, null);
  }
  
  public int count(SqlSession sqlSession, PlayerResult playerResult, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("count playerResult: " + playerResult + " " + sqlControl);
    }
    SqlQueryEngine sqlEnginePlayerResult = sqlEngineFactory.getCheckedQueryEngine("SELECT_PLAYER_RESULT");
    //sqlControl = getMoreResultClasses(playerResult, sqlControl);
    int count = sqlEnginePlayerResult.queryCount(sqlSession, playerResult, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("count: " + count);
    }
    return count;
  }
  
  public int count(PlayerResult playerResult, SqlControl sqlControl) {
  	return count(sqlSessionFactory.getSqlSession(), playerResult, sqlControl);
  }
  
  public int count(SqlSession sqlSession, PlayerResult playerResult) {
    return count(sqlSession, playerResult, null);
  }
  
  public int count(PlayerResult playerResult) {
    return count(playerResult, null);
  }
}
