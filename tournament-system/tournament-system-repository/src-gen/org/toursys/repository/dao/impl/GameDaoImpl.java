package org.toursys.repository.dao.impl;

import org.toursys.repository.dao.GameDao;

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
import org.toursys.repository.model.Game;

public class GameDaoImpl implements GameDao {
  protected final Logger logger = LoggerFactory.getLogger(getClass());

  protected SqlEngineFactory sqlEngineFactory;
  protected SqlSessionFactory sqlSessionFactory;
    	
  public GameDaoImpl() {
  }
    	
  public GameDaoImpl(SqlEngineFactory sqlEngineFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
  }
    	
  public GameDaoImpl(SqlEngineFactory sqlEngineFactory, SqlSessionFactory sqlSessionFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
    this.sqlSessionFactory = sqlSessionFactory;
  }
  
  
  public Game insert(SqlSession sqlSession, Game game, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("insert game: " + game + " " + sqlControl);
    }
    SqlCrudEngine sqlInsertGame = sqlEngineFactory.getCheckedCrudEngine("INSERT_GAME");
    int count = sqlInsertGame.insert(sqlSession, game, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("insert game result: " + count + " " + game);
    }
    return (count > 0) ? game : null;
  }
  public Game insert(Game game, SqlControl sqlControl) {
  	return insert(sqlSessionFactory.getSqlSession(), game, sqlControl);
  }
  public Game insert(SqlSession sqlSession, Game game) {
    return insert(sqlSession, game, null);
  }
  public Game insert(Game game) {
    return insert(game, null);
  }
  
  public Game get(SqlSession sqlSession, Game game, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("get get: " + game + " " + sqlControl);
    }
    SqlCrudEngine sqlGetEngineGame = sqlEngineFactory.getCheckedCrudEngine("GET_GAME");
    //sqlControl = getMoreResultClasses(game, sqlControl);
    Game gameGot = sqlGetEngineGame.get(sqlSession, Game.class, game, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("get game result: " + gameGot);
    }
    return gameGot;
  }
  public Game get(Game game, SqlControl sqlControl) {
  	return get(sqlSessionFactory.getSqlSession(), game, sqlControl);
  }
  public Game get(SqlSession sqlSession, Game game) {
    return get(sqlSession, game, null);
  }
  public Game get(Game game) {
    return get(game, null);
  }
  
  public int update(SqlSession sqlSession, Game game, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("update game: " + game + " " + sqlControl);
    }
    SqlCrudEngine sqlUpdateEngineGame = sqlEngineFactory.getCheckedCrudEngine("UPDATE_GAME");
    int count = sqlUpdateEngineGame.update(sqlSession, game, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("update game result count: " + count);
    }
    return count;
  }
  public int update(Game game, SqlControl sqlControl) {
  	return update(sqlSessionFactory.getSqlSession(), game, sqlControl);
  }
  public int update(SqlSession sqlSession, Game game) {
    return update(sqlSession, game, null);
  }
  public int update(Game game) {
    return update(game, null);
  }
  
  public int delete(SqlSession sqlSession, Game game, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("delete game: " + game + " " + sqlControl);
    }
    SqlCrudEngine sqlDeleteEngineGame = sqlEngineFactory.getCheckedCrudEngine("DELETE_GAME");
    int count = sqlDeleteEngineGame.delete(sqlSession, game, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("delete game result count: " + count);
    }
    return count;
  }
  public int delete(Game game, SqlControl sqlControl) {
  	return delete(sqlSessionFactory.getSqlSession(), game, sqlControl);
  }
  public int delete(SqlSession sqlSession, Game game) {
    return delete(sqlSession, game, null);
  }
  public int delete(Game game) {
    return delete(game, null);
  }
  
  public List<Game> list(SqlSession sqlSession, Game game, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("list game: " + game + " " + sqlControl);
    }
    SqlQueryEngine sqlEngineGame = sqlEngineFactory.getCheckedQueryEngine("SELECT_GAME");
    //sqlControl = getMoreResultClasses(game, sqlControl);
    List<Game> gameList = sqlEngineGame.query(sqlSession, Game.class, game, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("list game size: " + ((gameList != null) ? gameList.size() : "null"));
    }
    return gameList;
  }
  public List<Game> list(Game game, SqlControl sqlControl) {
  	return list(sqlSessionFactory.getSqlSession(), game, sqlControl);
  }
      public List<Game> list(SqlSession sqlSession, Game game) {
    return list(sqlSession, game, null);
  }
  public List<Game> list(Game game) {
    return list(game, null);
  }
  
  public int count(SqlSession sqlSession, Game game, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("count game: " + game + " " + sqlControl);
    }
    SqlQueryEngine sqlEngineGame = sqlEngineFactory.getCheckedQueryEngine("SELECT_GAME");
    //sqlControl = getMoreResultClasses(game, sqlControl);
    int count = sqlEngineGame.queryCount(sqlSession, game, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("count: " + count);
    }
    return count;
  }
  public int count(Game game, SqlControl sqlControl) {
  	return count(sqlSessionFactory.getSqlSession(), game, sqlControl);
  }
      public int count(SqlSession sqlSession, Game game) {
    return count(sqlSession, game, null);
  }
  public int count(Game game) {
    return count(game, null);
  }
}
