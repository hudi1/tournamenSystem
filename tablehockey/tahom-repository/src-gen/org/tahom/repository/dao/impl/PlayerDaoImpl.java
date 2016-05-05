package org.tahom.repository.dao.impl;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.sqlproc.engine.SqlControl;
import org.sqlproc.engine.SqlEngineFactory;
import org.sqlproc.engine.SqlRowProcessor;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlSessionFactory;
import org.tahom.repository.dao.PlayerDao;
import org.tahom.repository.model.Player;

@SuppressWarnings("all")
public class PlayerDaoImpl implements PlayerDao {
  protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
  
  public PlayerDaoImpl() {
  }
  
  public PlayerDaoImpl(final SqlEngineFactory sqlEngineFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
  }
  
  public PlayerDaoImpl(final SqlEngineFactory sqlEngineFactory, final SqlSessionFactory sqlSessionFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
    this.sqlSessionFactory = sqlSessionFactory;
  }
  
  protected SqlEngineFactory sqlEngineFactory;
  
  protected SqlSessionFactory sqlSessionFactory;
  
  public Player insert(final SqlSession sqlSession, final Player player, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql insert player: " + player + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlInsertPlayer = sqlEngineFactory.getCheckedCrudEngine("INSERT_PLAYER");
    int count = sqlInsertPlayer.insert(sqlSession, player, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql insert player result: " + count + " " + player);
    }
    return (count > 0) ? player : null;
  }
  
  public Player insert(final Player player, SqlControl sqlControl) {
    return insert(sqlSessionFactory.getSqlSession(), player, sqlControl);
  }
  
  public Player insert(final SqlSession sqlSession, final Player player) {
    return insert(sqlSession, player, null);
  }
  
  public Player insert(final Player player) {
    return insert(player, null);
  }
  
  public Player get(final SqlSession sqlSession, final Player player, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql get: " + player + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlGetEnginePlayer = sqlEngineFactory.getCheckedCrudEngine("GET_PLAYER");
    //sqlControl = getMoreResultClasses(player, sqlControl);
    Player playerGot = sqlGetEnginePlayer.get(sqlSession, Player.class, player, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql get player result: " + playerGot);
    }
    return playerGot;
  }
  
  public Player get(final Player player, SqlControl sqlControl) {
    return get(sqlSessionFactory.getSqlSession(), player, sqlControl);
  }
  
  public Player get(final SqlSession sqlSession, final Player player) {
    return get(sqlSession, player, null);
  }
  
  public Player get(final Player player) {
    return get(player, null);
  }
  
  public int update(final SqlSession sqlSession, final Player player, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql update player: " + player + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlUpdateEnginePlayer = sqlEngineFactory.getCheckedCrudEngine("UPDATE_PLAYER");
    int count = sqlUpdateEnginePlayer.update(sqlSession, player, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql update player result count: " + count);
    }
    return count;
  }
  
  public int update(final Player player, SqlControl sqlControl) {
    return update(sqlSessionFactory.getSqlSession(), player, sqlControl);
  }
  
  public int update(final SqlSession sqlSession, final Player player) {
    return update(sqlSession, player, null);
  }
  
  public int update(final Player player) {
    return update(player, null);
  }
  
  public int delete(final SqlSession sqlSession, final Player player, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql delete player: " + player + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlDeleteEnginePlayer = sqlEngineFactory.getCheckedCrudEngine("DELETE_PLAYER");
    int count = sqlDeleteEnginePlayer.delete(sqlSession, player, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql delete player result count: " + count);
    }
    return count;
  }
  
  public int delete(final Player player, SqlControl sqlControl) {
    return delete(sqlSessionFactory.getSqlSession(), player, sqlControl);
  }
  
  public int delete(final SqlSession sqlSession, final Player player) {
    return delete(sqlSession, player, null);
  }
  
  public int delete(final Player player) {
    return delete(player, null);
  }
  
  public List<Player> list(final SqlSession sqlSession, final Player player, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list player: " + player + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEnginePlayer = sqlEngineFactory.getCheckedQueryEngine("SELECT_PLAYER");
    //sqlControl = getMoreResultClasses(player, sqlControl);
    List<Player> playerList = sqlEnginePlayer.query(sqlSession, Player.class, player, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list player size: " + ((playerList != null) ? playerList.size() : "null"));
    }
    return playerList;
  }
  
  public List<Player> list(final Player player, SqlControl sqlControl) {
    return list(sqlSessionFactory.getSqlSession(), player, sqlControl);
  }
  
  public List<Player> list(final SqlSession sqlSession, final Player player) {
    return list(sqlSession, player, null);
  }
  
  public List<Player> list(final Player player) {
    return list(player, null);
  }
  
  public int query(final SqlSession sqlSession, final Player player, SqlControl sqlControl, final SqlRowProcessor<Player> sqlRowProcessor) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql query player: " + player + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEnginePlayer = sqlEngineFactory.getCheckedQueryEngine("SELECT_PLAYER");
    //sqlControl = getMoreResultClasses(player, sqlControl);
    int rownums = sqlEnginePlayer.query(sqlSession, Player.class, player, sqlControl, sqlRowProcessor);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql query player size: " + rownums);
    }
    return rownums;
  }
  
  public int query(final Player player, SqlControl sqlControl, final SqlRowProcessor<Player> sqlRowProcessor) {
    return query(sqlSessionFactory.getSqlSession(), player, sqlControl, sqlRowProcessor);
  }
  
  public int query(final SqlSession sqlSession, final Player player, final SqlRowProcessor<Player> sqlRowProcessor) {
    return query(sqlSession, player, null, sqlRowProcessor);
  }
  
  public int query(final Player player, final SqlRowProcessor<Player> sqlRowProcessor) {
    return query(player, null, sqlRowProcessor);
  }
  
  public List<Player> listFromTo(final SqlSession sqlSession, final Player player, SqlControl sqlControl) {
    if (sqlControl == null || sqlControl.getFirstResult() == null || sqlControl.getMaxResults() == null || player == null)
    	return list(sqlSession, player, sqlControl);
    
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list player: " + player + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEnginePlayer = sqlEngineFactory.getCheckedQueryEngine("SELECT_PLAYER");
    //sqlControl = getMoreResultClasses(player, sqlControl);
    player.setOnlyIds_(true);
    java.util.Set<String> initAssociations = player.getInitAssociations_();
    player.setInitAssociations_(new java.util.HashSet<String>());
    final java.util.List<java.lang.Integer> ids_ = sqlEnginePlayer.query(sqlSession, java.lang.Integer.class, player, sqlControl);
    player.setInitAssociations_(initAssociations);
    
    List<Player> playerList = new java.util.ArrayList<Player>();
    if (!ids_.isEmpty()) {
    	org.sqlproc.engine.impl.SqlStandardControl sqlc = new org.sqlproc.engine.impl.SqlStandardControl(sqlControl);
    	sqlc.setFirstResult(0);
    	sqlc.setMaxResults(0);
    	sqlc.setOrder(null);
    	final Map<java.lang.Integer, Player> map = new java.util.HashMap<java.lang.Integer, Player>();
    	final SqlRowProcessor<Player> sqlRowProcessor = new SqlRowProcessor<Player>() {
    		@Override
    		public boolean processRow(Player result, int rownum) throws org.sqlproc.engine.SqlRuntimeException {
    			map.put(result.getId(), result);
    			return true;
    		}
    	};
    	sqlEnginePlayer.query(sqlSession, Player.class, new Player()._setIds_(ids_), sqlc, sqlRowProcessor);
    	for (java.lang.Integer id : ids_)
    		playerList.add(map.get(id));
    }
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list player size: " + ((playerList != null) ? playerList.size() : "null"));
    }
    return playerList;
  }
  
  public List<Player> listFromTo(final Player player, SqlControl sqlControl) {
    return listFromTo(sqlSessionFactory.getSqlSession(), player, sqlControl);
  }
  
  public List<Player> listFromTo(final SqlSession sqlSession, final Player player) {
    return listFromTo(sqlSession, player, null);
  }
  
  public List<Player> listFromTo(final Player player) {
    return listFromTo(player, null);
  }
  
  public int count(final SqlSession sqlSession, final Player player, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("count player: " + player + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEnginePlayer = sqlEngineFactory.getCheckedQueryEngine("SELECT_PLAYER");
    //sqlControl = getMoreResultClasses(player, sqlControl);
    int count = sqlEnginePlayer.queryCount(sqlSession, player, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("count: " + count);
    }
    return count;
  }
  
  public int count(final Player player, SqlControl sqlControl) {
    return count(sqlSessionFactory.getSqlSession(), player, sqlControl);
  }
  
  public int count(final SqlSession sqlSession, final Player player) {
    return count(sqlSession, player, null);
  }
  
  public int count(final Player player) {
    return count(player, null);
  }
}
