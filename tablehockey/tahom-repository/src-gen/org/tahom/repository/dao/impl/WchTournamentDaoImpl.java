package org.tahom.repository.dao.impl;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.sqlproc.engine.SqlControl;
import org.sqlproc.engine.SqlEngineFactory;
import org.sqlproc.engine.SqlRowProcessor;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlSessionFactory;
import org.tahom.repository.dao.WchTournamentDao;
import org.tahom.repository.model.WchTournament;

@SuppressWarnings("all")
public class WchTournamentDaoImpl implements WchTournamentDao {
  protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
  
  public WchTournamentDaoImpl() {
  }
  
  public WchTournamentDaoImpl(final SqlEngineFactory sqlEngineFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
  }
  
  public WchTournamentDaoImpl(final SqlEngineFactory sqlEngineFactory, final SqlSessionFactory sqlSessionFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
    this.sqlSessionFactory = sqlSessionFactory;
  }
  
  protected SqlEngineFactory sqlEngineFactory;
  
  protected SqlSessionFactory sqlSessionFactory;
  
  public WchTournament insert(final SqlSession sqlSession, final WchTournament wchTournament, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql insert wchTournament: " + wchTournament + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlInsertWchTournament = sqlEngineFactory.getCheckedCrudEngine("INSERT_WCH_TOURNAMENT");
    int count = sqlInsertWchTournament.insert(sqlSession, wchTournament, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql insert wchTournament result: " + count + " " + wchTournament);
    }
    return (count > 0) ? wchTournament : null;
  }
  
  public WchTournament insert(final WchTournament wchTournament, SqlControl sqlControl) {
    return insert(sqlSessionFactory.getSqlSession(), wchTournament, sqlControl);
  }
  
  public WchTournament insert(final SqlSession sqlSession, final WchTournament wchTournament) {
    return insert(sqlSession, wchTournament, null);
  }
  
  public WchTournament insert(final WchTournament wchTournament) {
    return insert(wchTournament, null);
  }
  
  public WchTournament get(final SqlSession sqlSession, final WchTournament wchTournament, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql get: " + wchTournament + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlGetEngineWchTournament = sqlEngineFactory.getCheckedCrudEngine("GET_WCH_TOURNAMENT");
    //sqlControl = getMoreResultClasses(wchTournament, sqlControl);
    WchTournament wchTournamentGot = sqlGetEngineWchTournament.get(sqlSession, WchTournament.class, wchTournament, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql get wchTournament result: " + wchTournamentGot);
    }
    return wchTournamentGot;
  }
  
  public WchTournament get(final WchTournament wchTournament, SqlControl sqlControl) {
    return get(sqlSessionFactory.getSqlSession(), wchTournament, sqlControl);
  }
  
  public WchTournament get(final SqlSession sqlSession, final WchTournament wchTournament) {
    return get(sqlSession, wchTournament, null);
  }
  
  public WchTournament get(final WchTournament wchTournament) {
    return get(wchTournament, null);
  }
  
  public int update(final SqlSession sqlSession, final WchTournament wchTournament, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql update wchTournament: " + wchTournament + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlUpdateEngineWchTournament = sqlEngineFactory.getCheckedCrudEngine("UPDATE_WCH_TOURNAMENT");
    int count = sqlUpdateEngineWchTournament.update(sqlSession, wchTournament, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql update wchTournament result count: " + count);
    }
    return count;
  }
  
  public int update(final WchTournament wchTournament, SqlControl sqlControl) {
    return update(sqlSessionFactory.getSqlSession(), wchTournament, sqlControl);
  }
  
  public int update(final SqlSession sqlSession, final WchTournament wchTournament) {
    return update(sqlSession, wchTournament, null);
  }
  
  public int update(final WchTournament wchTournament) {
    return update(wchTournament, null);
  }
  
  public int delete(final SqlSession sqlSession, final WchTournament wchTournament, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql delete wchTournament: " + wchTournament + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlDeleteEngineWchTournament = sqlEngineFactory.getCheckedCrudEngine("DELETE_WCH_TOURNAMENT");
    int count = sqlDeleteEngineWchTournament.delete(sqlSession, wchTournament, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql delete wchTournament result count: " + count);
    }
    return count;
  }
  
  public int delete(final WchTournament wchTournament, SqlControl sqlControl) {
    return delete(sqlSessionFactory.getSqlSession(), wchTournament, sqlControl);
  }
  
  public int delete(final SqlSession sqlSession, final WchTournament wchTournament) {
    return delete(sqlSession, wchTournament, null);
  }
  
  public int delete(final WchTournament wchTournament) {
    return delete(wchTournament, null);
  }
  
  public List<WchTournament> list(final SqlSession sqlSession, final WchTournament wchTournament, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list wchTournament: " + wchTournament + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineWchTournament = sqlEngineFactory.getCheckedQueryEngine("SELECT_WCH_TOURNAMENT");
    //sqlControl = getMoreResultClasses(wchTournament, sqlControl);
    List<WchTournament> wchTournamentList = sqlEngineWchTournament.query(sqlSession, WchTournament.class, wchTournament, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list wchTournament size: " + ((wchTournamentList != null) ? wchTournamentList.size() : "null"));
    }
    return wchTournamentList;
  }
  
  public List<WchTournament> list(final WchTournament wchTournament, SqlControl sqlControl) {
    return list(sqlSessionFactory.getSqlSession(), wchTournament, sqlControl);
  }
  
  public List<WchTournament> list(final SqlSession sqlSession, final WchTournament wchTournament) {
    return list(sqlSession, wchTournament, null);
  }
  
  public List<WchTournament> list(final WchTournament wchTournament) {
    return list(wchTournament, null);
  }
  
  public int query(final SqlSession sqlSession, final WchTournament wchTournament, SqlControl sqlControl, final SqlRowProcessor<WchTournament> sqlRowProcessor) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql query wchTournament: " + wchTournament + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineWchTournament = sqlEngineFactory.getCheckedQueryEngine("SELECT_WCH_TOURNAMENT");
    //sqlControl = getMoreResultClasses(wchTournament, sqlControl);
    int rownums = sqlEngineWchTournament.query(sqlSession, WchTournament.class, wchTournament, sqlControl, sqlRowProcessor);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql query wchTournament size: " + rownums);
    }
    return rownums;
  }
  
  public int query(final WchTournament wchTournament, SqlControl sqlControl, final SqlRowProcessor<WchTournament> sqlRowProcessor) {
    return query(sqlSessionFactory.getSqlSession(), wchTournament, sqlControl, sqlRowProcessor);
  }
  
  public int query(final SqlSession sqlSession, final WchTournament wchTournament, final SqlRowProcessor<WchTournament> sqlRowProcessor) {
    return query(sqlSession, wchTournament, null, sqlRowProcessor);
  }
  
  public int query(final WchTournament wchTournament, final SqlRowProcessor<WchTournament> sqlRowProcessor) {
    return query(wchTournament, null, sqlRowProcessor);
  }
  
  public List<WchTournament> listFromTo(final SqlSession sqlSession, final WchTournament wchTournament, SqlControl sqlControl) {
    if (sqlControl == null || sqlControl.getFirstResult() == null || sqlControl.getMaxResults() == null || wchTournament == null)
    	return list(sqlSession, wchTournament, sqlControl);
    
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list wchTournament: " + wchTournament + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineWchTournament = sqlEngineFactory.getCheckedQueryEngine("SELECT_WCH_TOURNAMENT");
    //sqlControl = getMoreResultClasses(wchTournament, sqlControl);
    wchTournament.setOnlyIds(true);
    java.util.Set<String> initAssociations = wchTournament.getInitAssociations();
    wchTournament.setInitAssociations(new java.util.HashSet<String>());
    final java.util.List<java.lang.Integer> ids = sqlEngineWchTournament.query(sqlSession, java.lang.Integer.class, wchTournament, sqlControl);
    wchTournament.setInitAssociations(initAssociations);
    
    List<WchTournament> wchTournamentList = new java.util.ArrayList<WchTournament>();
    if (!ids.isEmpty()) {
    	org.sqlproc.engine.impl.SqlStandardControl sqlc = new org.sqlproc.engine.impl.SqlStandardControl(sqlControl);
    	sqlc.setFirstResult(0);
    	sqlc.setMaxResults(0);
    	sqlc.setOrder(null);
    	final Map<java.lang.Integer, WchTournament> map = new java.util.HashMap<java.lang.Integer, WchTournament>();
    	final SqlRowProcessor<WchTournament> sqlRowProcessor = new SqlRowProcessor<WchTournament>() {
    		@Override
    		public boolean processRow(WchTournament result, int rownum) throws org.sqlproc.engine.SqlRuntimeException {
    			map.put(result.getId(), result);
    			return true;
    		}
    	};
    	sqlEngineWchTournament.query(sqlSession, WchTournament.class, new WchTournament()._setIds(ids), sqlc, sqlRowProcessor);
    	for (java.lang.Integer id : ids)
    		wchTournamentList.add(map.get(id));
    }
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list wchTournament size: " + ((wchTournamentList != null) ? wchTournamentList.size() : "null"));
    }
    return wchTournamentList;
  }
  
  public List<WchTournament> listFromTo(final WchTournament wchTournament, SqlControl sqlControl) {
    return listFromTo(sqlSessionFactory.getSqlSession(), wchTournament, sqlControl);
  }
  
  public List<WchTournament> listFromTo(final SqlSession sqlSession, final WchTournament wchTournament) {
    return listFromTo(sqlSession, wchTournament, null);
  }
  
  public List<WchTournament> listFromTo(final WchTournament wchTournament) {
    return listFromTo(wchTournament, null);
  }
  
  public int count(final SqlSession sqlSession, final WchTournament wchTournament, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("count wchTournament: " + wchTournament + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineWchTournament = sqlEngineFactory.getCheckedQueryEngine("SELECT_WCH_TOURNAMENT");
    //sqlControl = getMoreResultClasses(wchTournament, sqlControl);
    int count = sqlEngineWchTournament.queryCount(sqlSession, wchTournament, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("count: " + count);
    }
    return count;
  }
  
  public int count(final WchTournament wchTournament, SqlControl sqlControl) {
    return count(sqlSessionFactory.getSqlSession(), wchTournament, sqlControl);
  }
  
  public int count(final SqlSession sqlSession, final WchTournament wchTournament) {
    return count(sqlSession, wchTournament, null);
  }
  
  public int count(final WchTournament wchTournament) {
    return count(wchTournament, null);
  }
}
