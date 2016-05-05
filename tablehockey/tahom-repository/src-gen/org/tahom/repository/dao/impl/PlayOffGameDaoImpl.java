package org.tahom.repository.dao.impl;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.sqlproc.engine.SqlControl;
import org.sqlproc.engine.SqlEngineFactory;
import org.sqlproc.engine.SqlRowProcessor;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlSessionFactory;
import org.tahom.repository.dao.PlayOffGameDao;
import org.tahom.repository.model.PlayOffGame;

@SuppressWarnings("all")
public class PlayOffGameDaoImpl implements PlayOffGameDao {
  protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
  
  public PlayOffGameDaoImpl() {
  }
  
  public PlayOffGameDaoImpl(final SqlEngineFactory sqlEngineFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
  }
  
  public PlayOffGameDaoImpl(final SqlEngineFactory sqlEngineFactory, final SqlSessionFactory sqlSessionFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
    this.sqlSessionFactory = sqlSessionFactory;
  }
  
  protected SqlEngineFactory sqlEngineFactory;
  
  protected SqlSessionFactory sqlSessionFactory;
  
  public PlayOffGame insert(final SqlSession sqlSession, final PlayOffGame playOffGame, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql insert playOffGame: " + playOffGame + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlInsertPlayOffGame = sqlEngineFactory.getCheckedCrudEngine("INSERT_PLAY_OFF_GAME");
    int count = sqlInsertPlayOffGame.insert(sqlSession, playOffGame, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql insert playOffGame result: " + count + " " + playOffGame);
    }
    return (count > 0) ? playOffGame : null;
  }
  
  public PlayOffGame insert(final PlayOffGame playOffGame, SqlControl sqlControl) {
    return insert(sqlSessionFactory.getSqlSession(), playOffGame, sqlControl);
  }
  
  public PlayOffGame insert(final SqlSession sqlSession, final PlayOffGame playOffGame) {
    return insert(sqlSession, playOffGame, null);
  }
  
  public PlayOffGame insert(final PlayOffGame playOffGame) {
    return insert(playOffGame, null);
  }
  
  public PlayOffGame get(final SqlSession sqlSession, final PlayOffGame playOffGame, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql get: " + playOffGame + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlGetEnginePlayOffGame = sqlEngineFactory.getCheckedCrudEngine("GET_PLAY_OFF_GAME");
    //sqlControl = getMoreResultClasses(playOffGame, sqlControl);
    PlayOffGame playOffGameGot = sqlGetEnginePlayOffGame.get(sqlSession, PlayOffGame.class, playOffGame, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql get playOffGame result: " + playOffGameGot);
    }
    return playOffGameGot;
  }
  
  public PlayOffGame get(final PlayOffGame playOffGame, SqlControl sqlControl) {
    return get(sqlSessionFactory.getSqlSession(), playOffGame, sqlControl);
  }
  
  public PlayOffGame get(final SqlSession sqlSession, final PlayOffGame playOffGame) {
    return get(sqlSession, playOffGame, null);
  }
  
  public PlayOffGame get(final PlayOffGame playOffGame) {
    return get(playOffGame, null);
  }
  
  public int update(final SqlSession sqlSession, final PlayOffGame playOffGame, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql update playOffGame: " + playOffGame + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlUpdateEnginePlayOffGame = sqlEngineFactory.getCheckedCrudEngine("UPDATE_PLAY_OFF_GAME");
    int count = sqlUpdateEnginePlayOffGame.update(sqlSession, playOffGame, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql update playOffGame result count: " + count);
    }
    return count;
  }
  
  public int update(final PlayOffGame playOffGame, SqlControl sqlControl) {
    return update(sqlSessionFactory.getSqlSession(), playOffGame, sqlControl);
  }
  
  public int update(final SqlSession sqlSession, final PlayOffGame playOffGame) {
    return update(sqlSession, playOffGame, null);
  }
  
  public int update(final PlayOffGame playOffGame) {
    return update(playOffGame, null);
  }
  
  public int delete(final SqlSession sqlSession, final PlayOffGame playOffGame, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql delete playOffGame: " + playOffGame + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlDeleteEnginePlayOffGame = sqlEngineFactory.getCheckedCrudEngine("DELETE_PLAY_OFF_GAME");
    int count = sqlDeleteEnginePlayOffGame.delete(sqlSession, playOffGame, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql delete playOffGame result count: " + count);
    }
    return count;
  }
  
  public int delete(final PlayOffGame playOffGame, SqlControl sqlControl) {
    return delete(sqlSessionFactory.getSqlSession(), playOffGame, sqlControl);
  }
  
  public int delete(final SqlSession sqlSession, final PlayOffGame playOffGame) {
    return delete(sqlSession, playOffGame, null);
  }
  
  public int delete(final PlayOffGame playOffGame) {
    return delete(playOffGame, null);
  }
  
  public List<PlayOffGame> list(final SqlSession sqlSession, final PlayOffGame playOffGame, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list playOffGame: " + playOffGame + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEnginePlayOffGame = sqlEngineFactory.getCheckedQueryEngine("SELECT_PLAY_OFF_GAME");
    //sqlControl = getMoreResultClasses(playOffGame, sqlControl);
    List<PlayOffGame> playOffGameList = sqlEnginePlayOffGame.query(sqlSession, PlayOffGame.class, playOffGame, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list playOffGame size: " + ((playOffGameList != null) ? playOffGameList.size() : "null"));
    }
    return playOffGameList;
  }
  
  public List<PlayOffGame> list(final PlayOffGame playOffGame, SqlControl sqlControl) {
    return list(sqlSessionFactory.getSqlSession(), playOffGame, sqlControl);
  }
  
  public List<PlayOffGame> list(final SqlSession sqlSession, final PlayOffGame playOffGame) {
    return list(sqlSession, playOffGame, null);
  }
  
  public List<PlayOffGame> list(final PlayOffGame playOffGame) {
    return list(playOffGame, null);
  }
  
  public int query(final SqlSession sqlSession, final PlayOffGame playOffGame, SqlControl sqlControl, final SqlRowProcessor<PlayOffGame> sqlRowProcessor) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql query playOffGame: " + playOffGame + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEnginePlayOffGame = sqlEngineFactory.getCheckedQueryEngine("SELECT_PLAY_OFF_GAME");
    //sqlControl = getMoreResultClasses(playOffGame, sqlControl);
    int rownums = sqlEnginePlayOffGame.query(sqlSession, PlayOffGame.class, playOffGame, sqlControl, sqlRowProcessor);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql query playOffGame size: " + rownums);
    }
    return rownums;
  }
  
  public int query(final PlayOffGame playOffGame, SqlControl sqlControl, final SqlRowProcessor<PlayOffGame> sqlRowProcessor) {
    return query(sqlSessionFactory.getSqlSession(), playOffGame, sqlControl, sqlRowProcessor);
  }
  
  public int query(final SqlSession sqlSession, final PlayOffGame playOffGame, final SqlRowProcessor<PlayOffGame> sqlRowProcessor) {
    return query(sqlSession, playOffGame, null, sqlRowProcessor);
  }
  
  public int query(final PlayOffGame playOffGame, final SqlRowProcessor<PlayOffGame> sqlRowProcessor) {
    return query(playOffGame, null, sqlRowProcessor);
  }
  
  public List<PlayOffGame> listFromTo(final SqlSession sqlSession, final PlayOffGame playOffGame, SqlControl sqlControl) {
    if (sqlControl == null || sqlControl.getFirstResult() == null || sqlControl.getMaxResults() == null || playOffGame == null)
    	return list(sqlSession, playOffGame, sqlControl);
    
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list playOffGame: " + playOffGame + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEnginePlayOffGame = sqlEngineFactory.getCheckedQueryEngine("SELECT_PLAY_OFF_GAME");
    //sqlControl = getMoreResultClasses(playOffGame, sqlControl);
    playOffGame.setOnlyIds_(true);
    java.util.Set<String> initAssociations = playOffGame.getInitAssociations_();
    playOffGame.setInitAssociations_(new java.util.HashSet<String>());
    final java.util.List<java.lang.Integer> ids_ = sqlEnginePlayOffGame.query(sqlSession, java.lang.Integer.class, playOffGame, sqlControl);
    playOffGame.setInitAssociations_(initAssociations);
    
    List<PlayOffGame> playOffGameList = new java.util.ArrayList<PlayOffGame>();
    if (!ids_.isEmpty()) {
    	org.sqlproc.engine.impl.SqlStandardControl sqlc = new org.sqlproc.engine.impl.SqlStandardControl(sqlControl);
    	sqlc.setFirstResult(0);
    	sqlc.setMaxResults(0);
    	sqlc.setOrder(null);
    	final Map<java.lang.Integer, PlayOffGame> map = new java.util.HashMap<java.lang.Integer, PlayOffGame>();
    	final SqlRowProcessor<PlayOffGame> sqlRowProcessor = new SqlRowProcessor<PlayOffGame>() {
    		@Override
    		public boolean processRow(PlayOffGame result, int rownum) throws org.sqlproc.engine.SqlRuntimeException {
    			map.put(result.getId(), result);
    			return true;
    		}
    	};
    	sqlEnginePlayOffGame.query(sqlSession, PlayOffGame.class, new PlayOffGame()._setIds_(ids_), sqlc, sqlRowProcessor);
    	for (java.lang.Integer id : ids_)
    		playOffGameList.add(map.get(id));
    }
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list playOffGame size: " + ((playOffGameList != null) ? playOffGameList.size() : "null"));
    }
    return playOffGameList;
  }
  
  public List<PlayOffGame> listFromTo(final PlayOffGame playOffGame, SqlControl sqlControl) {
    return listFromTo(sqlSessionFactory.getSqlSession(), playOffGame, sqlControl);
  }
  
  public List<PlayOffGame> listFromTo(final SqlSession sqlSession, final PlayOffGame playOffGame) {
    return listFromTo(sqlSession, playOffGame, null);
  }
  
  public List<PlayOffGame> listFromTo(final PlayOffGame playOffGame) {
    return listFromTo(playOffGame, null);
  }
  
  public int count(final SqlSession sqlSession, final PlayOffGame playOffGame, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("count playOffGame: " + playOffGame + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEnginePlayOffGame = sqlEngineFactory.getCheckedQueryEngine("SELECT_PLAY_OFF_GAME");
    //sqlControl = getMoreResultClasses(playOffGame, sqlControl);
    int count = sqlEnginePlayOffGame.queryCount(sqlSession, playOffGame, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("count: " + count);
    }
    return count;
  }
  
  public int count(final PlayOffGame playOffGame, SqlControl sqlControl) {
    return count(sqlSessionFactory.getSqlSession(), playOffGame, sqlControl);
  }
  
  public int count(final SqlSession sqlSession, final PlayOffGame playOffGame) {
    return count(sqlSession, playOffGame, null);
  }
  
  public int count(final PlayOffGame playOffGame) {
    return count(playOffGame, null);
  }
}
