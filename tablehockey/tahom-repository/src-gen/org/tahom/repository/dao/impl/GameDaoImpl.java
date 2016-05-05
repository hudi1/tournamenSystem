package org.tahom.repository.dao.impl;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.sqlproc.engine.SqlControl;
import org.sqlproc.engine.SqlEngineFactory;
import org.sqlproc.engine.SqlRowProcessor;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlSessionFactory;
import org.tahom.repository.dao.GameDao;
import org.tahom.repository.model.Game;

@SuppressWarnings("all")
public class GameDaoImpl implements GameDao {
  protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
  
  public GameDaoImpl() {
  }
  
  public GameDaoImpl(final SqlEngineFactory sqlEngineFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
  }
  
  public GameDaoImpl(final SqlEngineFactory sqlEngineFactory, final SqlSessionFactory sqlSessionFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
    this.sqlSessionFactory = sqlSessionFactory;
  }
  
  protected SqlEngineFactory sqlEngineFactory;
  
  protected SqlSessionFactory sqlSessionFactory;
  
  public Game insert(final SqlSession sqlSession, final Game game, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql insert game: " + game + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlInsertGame = sqlEngineFactory.getCheckedCrudEngine("INSERT_GAME");
    int count = sqlInsertGame.insert(sqlSession, game, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql insert game result: " + count + " " + game);
    }
    return (count > 0) ? game : null;
  }
  
  public Game insert(final Game game, SqlControl sqlControl) {
    return insert(sqlSessionFactory.getSqlSession(), game, sqlControl);
  }
  
  public Game insert(final SqlSession sqlSession, final Game game) {
    return insert(sqlSession, game, null);
  }
  
  public Game insert(final Game game) {
    return insert(game, null);
  }
  
  public Game get(final SqlSession sqlSession, final Game game, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql get: " + game + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlGetEngineGame = sqlEngineFactory.getCheckedCrudEngine("GET_GAME");
    //sqlControl = getMoreResultClasses(game, sqlControl);
    Game gameGot = sqlGetEngineGame.get(sqlSession, Game.class, game, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql get game result: " + gameGot);
    }
    return gameGot;
  }
  
  public Game get(final Game game, SqlControl sqlControl) {
    return get(sqlSessionFactory.getSqlSession(), game, sqlControl);
  }
  
  public Game get(final SqlSession sqlSession, final Game game) {
    return get(sqlSession, game, null);
  }
  
  public Game get(final Game game) {
    return get(game, null);
  }
  
  public int update(final SqlSession sqlSession, final Game game, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql update game: " + game + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlUpdateEngineGame = sqlEngineFactory.getCheckedCrudEngine("UPDATE_GAME");
    int count = sqlUpdateEngineGame.update(sqlSession, game, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql update game result count: " + count);
    }
    return count;
  }
  
  public int update(final Game game, SqlControl sqlControl) {
    return update(sqlSessionFactory.getSqlSession(), game, sqlControl);
  }
  
  public int update(final SqlSession sqlSession, final Game game) {
    return update(sqlSession, game, null);
  }
  
  public int update(final Game game) {
    return update(game, null);
  }
  
  public int delete(final SqlSession sqlSession, final Game game, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql delete game: " + game + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlDeleteEngineGame = sqlEngineFactory.getCheckedCrudEngine("DELETE_GAME");
    int count = sqlDeleteEngineGame.delete(sqlSession, game, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql delete game result count: " + count);
    }
    return count;
  }
  
  public int delete(final Game game, SqlControl sqlControl) {
    return delete(sqlSessionFactory.getSqlSession(), game, sqlControl);
  }
  
  public int delete(final SqlSession sqlSession, final Game game) {
    return delete(sqlSession, game, null);
  }
  
  public int delete(final Game game) {
    return delete(game, null);
  }
  
  public List<Game> list(final SqlSession sqlSession, final Game game, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list game: " + game + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineGame = sqlEngineFactory.getCheckedQueryEngine("SELECT_GAME");
    //sqlControl = getMoreResultClasses(game, sqlControl);
    List<Game> gameList = sqlEngineGame.query(sqlSession, Game.class, game, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list game size: " + ((gameList != null) ? gameList.size() : "null"));
    }
    return gameList;
  }
  
  public List<Game> list(final Game game, SqlControl sqlControl) {
    return list(sqlSessionFactory.getSqlSession(), game, sqlControl);
  }
  
  public List<Game> list(final SqlSession sqlSession, final Game game) {
    return list(sqlSession, game, null);
  }
  
  public List<Game> list(final Game game) {
    return list(game, null);
  }
  
  public int query(final SqlSession sqlSession, final Game game, SqlControl sqlControl, final SqlRowProcessor<Game> sqlRowProcessor) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql query game: " + game + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineGame = sqlEngineFactory.getCheckedQueryEngine("SELECT_GAME");
    //sqlControl = getMoreResultClasses(game, sqlControl);
    int rownums = sqlEngineGame.query(sqlSession, Game.class, game, sqlControl, sqlRowProcessor);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql query game size: " + rownums);
    }
    return rownums;
  }
  
  public int query(final Game game, SqlControl sqlControl, final SqlRowProcessor<Game> sqlRowProcessor) {
    return query(sqlSessionFactory.getSqlSession(), game, sqlControl, sqlRowProcessor);
  }
  
  public int query(final SqlSession sqlSession, final Game game, final SqlRowProcessor<Game> sqlRowProcessor) {
    return query(sqlSession, game, null, sqlRowProcessor);
  }
  
  public int query(final Game game, final SqlRowProcessor<Game> sqlRowProcessor) {
    return query(game, null, sqlRowProcessor);
  }
  
  public List<Game> listFromTo(final SqlSession sqlSession, final Game game, SqlControl sqlControl) {
    if (sqlControl == null || sqlControl.getFirstResult() == null || sqlControl.getMaxResults() == null || game == null)
    	return list(sqlSession, game, sqlControl);
    
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list game: " + game + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineGame = sqlEngineFactory.getCheckedQueryEngine("SELECT_GAME");
    //sqlControl = getMoreResultClasses(game, sqlControl);
    game.setOnlyIds_(true);
    java.util.Set<String> initAssociations = game.getInitAssociations_();
    game.setInitAssociations_(new java.util.HashSet<String>());
    final java.util.List<java.lang.Integer> ids_ = sqlEngineGame.query(sqlSession, java.lang.Integer.class, game, sqlControl);
    game.setInitAssociations_(initAssociations);
    
    List<Game> gameList = new java.util.ArrayList<Game>();
    if (!ids_.isEmpty()) {
    	org.sqlproc.engine.impl.SqlStandardControl sqlc = new org.sqlproc.engine.impl.SqlStandardControl(sqlControl);
    	sqlc.setFirstResult(0);
    	sqlc.setMaxResults(0);
    	sqlc.setOrder(null);
    	final Map<java.lang.Integer, Game> map = new java.util.HashMap<java.lang.Integer, Game>();
    	final SqlRowProcessor<Game> sqlRowProcessor = new SqlRowProcessor<Game>() {
    		@Override
    		public boolean processRow(Game result, int rownum) throws org.sqlproc.engine.SqlRuntimeException {
    			map.put(result.getId(), result);
    			return true;
    		}
    	};
    	sqlEngineGame.query(sqlSession, Game.class, new Game()._setIds_(ids_), sqlc, sqlRowProcessor);
    	for (java.lang.Integer id : ids_)
    		gameList.add(map.get(id));
    }
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list game size: " + ((gameList != null) ? gameList.size() : "null"));
    }
    return gameList;
  }
  
  public List<Game> listFromTo(final Game game, SqlControl sqlControl) {
    return listFromTo(sqlSessionFactory.getSqlSession(), game, sqlControl);
  }
  
  public List<Game> listFromTo(final SqlSession sqlSession, final Game game) {
    return listFromTo(sqlSession, game, null);
  }
  
  public List<Game> listFromTo(final Game game) {
    return listFromTo(game, null);
  }
  
  public int count(final SqlSession sqlSession, final Game game, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("count game: " + game + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineGame = sqlEngineFactory.getCheckedQueryEngine("SELECT_GAME");
    //sqlControl = getMoreResultClasses(game, sqlControl);
    int count = sqlEngineGame.queryCount(sqlSession, game, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("count: " + count);
    }
    return count;
  }
  
  public int count(final Game game, SqlControl sqlControl) {
    return count(sqlSessionFactory.getSqlSession(), game, sqlControl);
  }
  
  public int count(final SqlSession sqlSession, final Game game) {
    return count(sqlSession, game, null);
  }
  
  public int count(final Game game) {
    return count(game, null);
  }
}
