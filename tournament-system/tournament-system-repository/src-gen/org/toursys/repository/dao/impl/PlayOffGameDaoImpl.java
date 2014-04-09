package org.toursys.repository.dao.impl;

import org.toursys.repository.dao.PlayOffGameDao;

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
import org.toursys.repository.model.PlayOffGame;

public class PlayOffGameDaoImpl implements PlayOffGameDao {
  protected final Logger logger = LoggerFactory.getLogger(getClass());

  protected SqlEngineFactory sqlEngineFactory;
  protected SqlSessionFactory sqlSessionFactory;
    	
  public PlayOffGameDaoImpl() {
  }
    	
  public PlayOffGameDaoImpl(SqlEngineFactory sqlEngineFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
  }
    	
  public PlayOffGameDaoImpl(SqlEngineFactory sqlEngineFactory, SqlSessionFactory sqlSessionFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
    this.sqlSessionFactory = sqlSessionFactory;
  }
  
  
  public PlayOffGame insert(SqlSession sqlSession, PlayOffGame playOffGame, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("insert playOffGame: " + playOffGame + " " + sqlControl);
    }
    SqlCrudEngine sqlInsertPlayOffGame = sqlEngineFactory.getCheckedCrudEngine("INSERT_PLAY_OFF_GAME");
    SqlCrudEngine sqlInsertGame = sqlEngineFactory.getCheckedCrudEngine("INSERT_GAME");
    int count = sqlInsertGame.insert(sqlSession, playOffGame, sqlControl);
    if (count > 0) {
      sqlInsertPlayOffGame.insert(sqlSession, playOffGame, sqlControl);
    }
    if (logger.isTraceEnabled()) {
      logger.trace("insert playOffGame result: " + count + " " + playOffGame);
    }
    return (count > 0) ? playOffGame : null;
  }
  public PlayOffGame insert(PlayOffGame playOffGame, SqlControl sqlControl) {
  	return insert(sqlSessionFactory.getSqlSession(), playOffGame, sqlControl);
  }
  public PlayOffGame insert(SqlSession sqlSession, PlayOffGame playOffGame) {
    return insert(sqlSession, playOffGame, null);
  }
  public PlayOffGame insert(PlayOffGame playOffGame) {
    return insert(playOffGame, null);
  }
  
  public PlayOffGame get(SqlSession sqlSession, PlayOffGame playOffGame, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("get get: " + playOffGame + " " + sqlControl);
    }
    SqlCrudEngine sqlGetEnginePlayOffGame = sqlEngineFactory.getCheckedCrudEngine("GET_PLAY_OFF_GAME");
    //sqlControl = getMoreResultClasses(playOffGame, sqlControl);
    PlayOffGame playOffGameGot = sqlGetEnginePlayOffGame.get(sqlSession, PlayOffGame.class, playOffGame, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("get playOffGame result: " + playOffGameGot);
    }
    return playOffGameGot;
  }
  public PlayOffGame get(PlayOffGame playOffGame, SqlControl sqlControl) {
  	return get(sqlSessionFactory.getSqlSession(), playOffGame, sqlControl);
  }
  public PlayOffGame get(SqlSession sqlSession, PlayOffGame playOffGame) {
    return get(sqlSession, playOffGame, null);
  }
  public PlayOffGame get(PlayOffGame playOffGame) {
    return get(playOffGame, null);
  }
  
  public int update(SqlSession sqlSession, PlayOffGame playOffGame, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("update playOffGame: " + playOffGame + " " + sqlControl);
    }
    SqlCrudEngine sqlUpdateEnginePlayOffGame = sqlEngineFactory.getCheckedCrudEngine("UPDATE_PLAY_OFF_GAME");
    SqlCrudEngine sqlUpdateGame = sqlEngineFactory.getCheckedCrudEngine("UPDATE_GAME");
    int count = sqlUpdateEnginePlayOffGame.update(sqlSession, playOffGame, sqlControl);
    if (count > 0) {
    	sqlUpdateGame.update(sqlSession, playOffGame, sqlControl);
    }
    if (logger.isTraceEnabled()) {
      logger.trace("update playOffGame result count: " + count);
    }
    return count;
  }
  public int update(PlayOffGame playOffGame, SqlControl sqlControl) {
  	return update(sqlSessionFactory.getSqlSession(), playOffGame, sqlControl);
  }
  public int update(SqlSession sqlSession, PlayOffGame playOffGame) {
    return update(sqlSession, playOffGame, null);
  }
  public int update(PlayOffGame playOffGame) {
    return update(playOffGame, null);
  }
  
  public int delete(SqlSession sqlSession, PlayOffGame playOffGame, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("delete playOffGame: " + playOffGame + " " + sqlControl);
    }
    SqlCrudEngine sqlDeleteEnginePlayOffGame = sqlEngineFactory.getCheckedCrudEngine("DELETE_PLAY_OFF_GAME");
    SqlCrudEngine sqlDeleteGame = sqlEngineFactory.getCheckedCrudEngine("DELETE_GAME");
    int count = sqlDeleteEnginePlayOffGame.delete(sqlSession, playOffGame, sqlControl);
    if (count > 0) {
    	sqlDeleteGame.delete(sqlSession, playOffGame, sqlControl);
    }
    if (logger.isTraceEnabled()) {
      logger.trace("delete playOffGame result count: " + count);
    }
    return count;
  }
  public int delete(PlayOffGame playOffGame, SqlControl sqlControl) {
  	return delete(sqlSessionFactory.getSqlSession(), playOffGame, sqlControl);
  }
  public int delete(SqlSession sqlSession, PlayOffGame playOffGame) {
    return delete(sqlSession, playOffGame, null);
  }
  public int delete(PlayOffGame playOffGame) {
    return delete(playOffGame, null);
  }
  
  public List<PlayOffGame> list(SqlSession sqlSession, PlayOffGame playOffGame, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("list playOffGame: " + playOffGame + " " + sqlControl);
    }
    SqlQueryEngine sqlEnginePlayOffGame = sqlEngineFactory.getCheckedQueryEngine("SELECT_PLAY_OFF_GAME");
    //sqlControl = getMoreResultClasses(playOffGame, sqlControl);
    List<PlayOffGame> playOffGameList = sqlEnginePlayOffGame.query(sqlSession, PlayOffGame.class, playOffGame, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("list playOffGame size: " + ((playOffGameList != null) ? playOffGameList.size() : "null"));
    }
    return playOffGameList;
  }
  public List<PlayOffGame> list(PlayOffGame playOffGame, SqlControl sqlControl) {
  	return list(sqlSessionFactory.getSqlSession(), playOffGame, sqlControl);
  }
      public List<PlayOffGame> list(SqlSession sqlSession, PlayOffGame playOffGame) {
    return list(sqlSession, playOffGame, null);
  }
  public List<PlayOffGame> list(PlayOffGame playOffGame) {
    return list(playOffGame, null);
  }
  
  public int count(SqlSession sqlSession, PlayOffGame playOffGame, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("count playOffGame: " + playOffGame + " " + sqlControl);
    }
    SqlQueryEngine sqlEnginePlayOffGame = sqlEngineFactory.getCheckedQueryEngine("SELECT_PLAY_OFF_GAME");
    //sqlControl = getMoreResultClasses(playOffGame, sqlControl);
    int count = sqlEnginePlayOffGame.queryCount(sqlSession, playOffGame, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("count: " + count);
    }
    return count;
  }
  public int count(PlayOffGame playOffGame, SqlControl sqlControl) {
  	return count(sqlSessionFactory.getSqlSession(), playOffGame, sqlControl);
  }
      public int count(SqlSession sqlSession, PlayOffGame playOffGame) {
    return count(sqlSession, playOffGame, null);
  }
  public int count(PlayOffGame playOffGame) {
    return count(playOffGame, null);
  }
}
