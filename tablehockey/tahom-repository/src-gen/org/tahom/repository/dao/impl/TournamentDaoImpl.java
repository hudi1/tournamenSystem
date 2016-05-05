package org.tahom.repository.dao.impl;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.sqlproc.engine.SqlControl;
import org.sqlproc.engine.SqlEngineFactory;
import org.sqlproc.engine.SqlRowProcessor;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlSessionFactory;
import org.tahom.repository.dao.TournamentDao;
import org.tahom.repository.model.Tournament;

@SuppressWarnings("all")
public class TournamentDaoImpl implements TournamentDao {
  protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
  
  public TournamentDaoImpl() {
  }
  
  public TournamentDaoImpl(final SqlEngineFactory sqlEngineFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
  }
  
  public TournamentDaoImpl(final SqlEngineFactory sqlEngineFactory, final SqlSessionFactory sqlSessionFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
    this.sqlSessionFactory = sqlSessionFactory;
  }
  
  protected SqlEngineFactory sqlEngineFactory;
  
  protected SqlSessionFactory sqlSessionFactory;
  
  public Tournament insert(final SqlSession sqlSession, final Tournament tournament, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql insert tournament: " + tournament + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlInsertTournament = sqlEngineFactory.getCheckedCrudEngine("INSERT_TOURNAMENT");
    int count = sqlInsertTournament.insert(sqlSession, tournament, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql insert tournament result: " + count + " " + tournament);
    }
    return (count > 0) ? tournament : null;
  }
  
  public Tournament insert(final Tournament tournament, SqlControl sqlControl) {
    return insert(sqlSessionFactory.getSqlSession(), tournament, sqlControl);
  }
  
  public Tournament insert(final SqlSession sqlSession, final Tournament tournament) {
    return insert(sqlSession, tournament, null);
  }
  
  public Tournament insert(final Tournament tournament) {
    return insert(tournament, null);
  }
  
  public Tournament get(final SqlSession sqlSession, final Tournament tournament, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql get: " + tournament + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlGetEngineTournament = sqlEngineFactory.getCheckedCrudEngine("GET_TOURNAMENT");
    //sqlControl = getMoreResultClasses(tournament, sqlControl);
    Tournament tournamentGot = sqlGetEngineTournament.get(sqlSession, Tournament.class, tournament, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql get tournament result: " + tournamentGot);
    }
    return tournamentGot;
  }
  
  public Tournament get(final Tournament tournament, SqlControl sqlControl) {
    return get(sqlSessionFactory.getSqlSession(), tournament, sqlControl);
  }
  
  public Tournament get(final SqlSession sqlSession, final Tournament tournament) {
    return get(sqlSession, tournament, null);
  }
  
  public Tournament get(final Tournament tournament) {
    return get(tournament, null);
  }
  
  public int update(final SqlSession sqlSession, final Tournament tournament, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql update tournament: " + tournament + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlUpdateEngineTournament = sqlEngineFactory.getCheckedCrudEngine("UPDATE_TOURNAMENT");
    int count = sqlUpdateEngineTournament.update(sqlSession, tournament, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql update tournament result count: " + count);
    }
    return count;
  }
  
  public int update(final Tournament tournament, SqlControl sqlControl) {
    return update(sqlSessionFactory.getSqlSession(), tournament, sqlControl);
  }
  
  public int update(final SqlSession sqlSession, final Tournament tournament) {
    return update(sqlSession, tournament, null);
  }
  
  public int update(final Tournament tournament) {
    return update(tournament, null);
  }
  
  public int delete(final SqlSession sqlSession, final Tournament tournament, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql delete tournament: " + tournament + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlDeleteEngineTournament = sqlEngineFactory.getCheckedCrudEngine("DELETE_TOURNAMENT");
    int count = sqlDeleteEngineTournament.delete(sqlSession, tournament, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql delete tournament result count: " + count);
    }
    return count;
  }
  
  public int delete(final Tournament tournament, SqlControl sqlControl) {
    return delete(sqlSessionFactory.getSqlSession(), tournament, sqlControl);
  }
  
  public int delete(final SqlSession sqlSession, final Tournament tournament) {
    return delete(sqlSession, tournament, null);
  }
  
  public int delete(final Tournament tournament) {
    return delete(tournament, null);
  }
  
  public List<Tournament> list(final SqlSession sqlSession, final Tournament tournament, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list tournament: " + tournament + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineTournament = sqlEngineFactory.getCheckedQueryEngine("SELECT_TOURNAMENT");
    //sqlControl = getMoreResultClasses(tournament, sqlControl);
    List<Tournament> tournamentList = sqlEngineTournament.query(sqlSession, Tournament.class, tournament, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list tournament size: " + ((tournamentList != null) ? tournamentList.size() : "null"));
    }
    return tournamentList;
  }
  
  public List<Tournament> list(final Tournament tournament, SqlControl sqlControl) {
    return list(sqlSessionFactory.getSqlSession(), tournament, sqlControl);
  }
  
  public List<Tournament> list(final SqlSession sqlSession, final Tournament tournament) {
    return list(sqlSession, tournament, null);
  }
  
  public List<Tournament> list(final Tournament tournament) {
    return list(tournament, null);
  }
  
  public int query(final SqlSession sqlSession, final Tournament tournament, SqlControl sqlControl, final SqlRowProcessor<Tournament> sqlRowProcessor) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql query tournament: " + tournament + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineTournament = sqlEngineFactory.getCheckedQueryEngine("SELECT_TOURNAMENT");
    //sqlControl = getMoreResultClasses(tournament, sqlControl);
    int rownums = sqlEngineTournament.query(sqlSession, Tournament.class, tournament, sqlControl, sqlRowProcessor);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql query tournament size: " + rownums);
    }
    return rownums;
  }
  
  public int query(final Tournament tournament, SqlControl sqlControl, final SqlRowProcessor<Tournament> sqlRowProcessor) {
    return query(sqlSessionFactory.getSqlSession(), tournament, sqlControl, sqlRowProcessor);
  }
  
  public int query(final SqlSession sqlSession, final Tournament tournament, final SqlRowProcessor<Tournament> sqlRowProcessor) {
    return query(sqlSession, tournament, null, sqlRowProcessor);
  }
  
  public int query(final Tournament tournament, final SqlRowProcessor<Tournament> sqlRowProcessor) {
    return query(tournament, null, sqlRowProcessor);
  }
  
  public List<Tournament> listFromTo(final SqlSession sqlSession, final Tournament tournament, SqlControl sqlControl) {
    if (sqlControl == null || sqlControl.getFirstResult() == null || sqlControl.getMaxResults() == null || tournament == null)
    	return list(sqlSession, tournament, sqlControl);
    
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list tournament: " + tournament + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineTournament = sqlEngineFactory.getCheckedQueryEngine("SELECT_TOURNAMENT");
    //sqlControl = getMoreResultClasses(tournament, sqlControl);
    tournament.setOnlyIds_(true);
    java.util.Set<String> initAssociations = tournament.getInitAssociations_();
    tournament.setInitAssociations_(new java.util.HashSet<String>());
    final java.util.List<java.lang.Integer> ids_ = sqlEngineTournament.query(sqlSession, java.lang.Integer.class, tournament, sqlControl);
    tournament.setInitAssociations_(initAssociations);
    
    List<Tournament> tournamentList = new java.util.ArrayList<Tournament>();
    if (!ids_.isEmpty()) {
    	org.sqlproc.engine.impl.SqlStandardControl sqlc = new org.sqlproc.engine.impl.SqlStandardControl(sqlControl);
    	sqlc.setFirstResult(0);
    	sqlc.setMaxResults(0);
    	sqlc.setOrder(null);
    	final Map<java.lang.Integer, Tournament> map = new java.util.HashMap<java.lang.Integer, Tournament>();
    	final SqlRowProcessor<Tournament> sqlRowProcessor = new SqlRowProcessor<Tournament>() {
    		@Override
    		public boolean processRow(Tournament result, int rownum) throws org.sqlproc.engine.SqlRuntimeException {
    			map.put(result.getId(), result);
    			return true;
    		}
    	};
    	sqlEngineTournament.query(sqlSession, Tournament.class, new Tournament()._setIds_(ids_), sqlc, sqlRowProcessor);
    	for (java.lang.Integer id : ids_)
    		tournamentList.add(map.get(id));
    }
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list tournament size: " + ((tournamentList != null) ? tournamentList.size() : "null"));
    }
    return tournamentList;
  }
  
  public List<Tournament> listFromTo(final Tournament tournament, SqlControl sqlControl) {
    return listFromTo(sqlSessionFactory.getSqlSession(), tournament, sqlControl);
  }
  
  public List<Tournament> listFromTo(final SqlSession sqlSession, final Tournament tournament) {
    return listFromTo(sqlSession, tournament, null);
  }
  
  public List<Tournament> listFromTo(final Tournament tournament) {
    return listFromTo(tournament, null);
  }
  
  public int count(final SqlSession sqlSession, final Tournament tournament, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("count tournament: " + tournament + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineTournament = sqlEngineFactory.getCheckedQueryEngine("SELECT_TOURNAMENT");
    //sqlControl = getMoreResultClasses(tournament, sqlControl);
    int count = sqlEngineTournament.queryCount(sqlSession, tournament, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("count: " + count);
    }
    return count;
  }
  
  public int count(final Tournament tournament, SqlControl sqlControl) {
    return count(sqlSessionFactory.getSqlSession(), tournament, sqlControl);
  }
  
  public int count(final SqlSession sqlSession, final Tournament tournament) {
    return count(sqlSession, tournament, null);
  }
  
  public int count(final Tournament tournament) {
    return count(tournament, null);
  }
}
