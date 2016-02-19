package org.tahom.repository.dao.impl;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.sqlproc.engine.SqlControl;
import org.sqlproc.engine.SqlEngineFactory;
import org.sqlproc.engine.SqlRowProcessor;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlSessionFactory;
import org.tahom.repository.dao.SeasonDao;
import org.tahom.repository.model.Season;

@SuppressWarnings("all")
public class SeasonDaoImpl implements SeasonDao {
  protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
  
  public SeasonDaoImpl() {
  }
  
  public SeasonDaoImpl(final SqlEngineFactory sqlEngineFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
  }
  
  public SeasonDaoImpl(final SqlEngineFactory sqlEngineFactory, final SqlSessionFactory sqlSessionFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
    this.sqlSessionFactory = sqlSessionFactory;
  }
  
  protected SqlEngineFactory sqlEngineFactory;
  
  protected SqlSessionFactory sqlSessionFactory;
  
  public Season insert(final SqlSession sqlSession, final Season season, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql insert season: " + season + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlInsertSeason = sqlEngineFactory.getCheckedCrudEngine("INSERT_SEASON");
    int count = sqlInsertSeason.insert(sqlSession, season, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql insert season result: " + count + " " + season);
    }
    return (count > 0) ? season : null;
  }
  
  public Season insert(final Season season, SqlControl sqlControl) {
    return insert(sqlSessionFactory.getSqlSession(), season, sqlControl);
  }
  
  public Season insert(final SqlSession sqlSession, final Season season) {
    return insert(sqlSession, season, null);
  }
  
  public Season insert(final Season season) {
    return insert(season, null);
  }
  
  public Season get(final SqlSession sqlSession, final Season season, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql get: " + season + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlGetEngineSeason = sqlEngineFactory.getCheckedCrudEngine("GET_SEASON");
    //sqlControl = getMoreResultClasses(season, sqlControl);
    Season seasonGot = sqlGetEngineSeason.get(sqlSession, Season.class, season, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql get season result: " + seasonGot);
    }
    return seasonGot;
  }
  
  public Season get(final Season season, SqlControl sqlControl) {
    return get(sqlSessionFactory.getSqlSession(), season, sqlControl);
  }
  
  public Season get(final SqlSession sqlSession, final Season season) {
    return get(sqlSession, season, null);
  }
  
  public Season get(final Season season) {
    return get(season, null);
  }
  
  public int update(final SqlSession sqlSession, final Season season, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql update season: " + season + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlUpdateEngineSeason = sqlEngineFactory.getCheckedCrudEngine("UPDATE_SEASON");
    int count = sqlUpdateEngineSeason.update(sqlSession, season, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql update season result count: " + count);
    }
    return count;
  }
  
  public int update(final Season season, SqlControl sqlControl) {
    return update(sqlSessionFactory.getSqlSession(), season, sqlControl);
  }
  
  public int update(final SqlSession sqlSession, final Season season) {
    return update(sqlSession, season, null);
  }
  
  public int update(final Season season) {
    return update(season, null);
  }
  
  public int delete(final SqlSession sqlSession, final Season season, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql delete season: " + season + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlDeleteEngineSeason = sqlEngineFactory.getCheckedCrudEngine("DELETE_SEASON");
    int count = sqlDeleteEngineSeason.delete(sqlSession, season, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql delete season result count: " + count);
    }
    return count;
  }
  
  public int delete(final Season season, SqlControl sqlControl) {
    return delete(sqlSessionFactory.getSqlSession(), season, sqlControl);
  }
  
  public int delete(final SqlSession sqlSession, final Season season) {
    return delete(sqlSession, season, null);
  }
  
  public int delete(final Season season) {
    return delete(season, null);
  }
  
  public List<Season> list(final SqlSession sqlSession, final Season season, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list season: " + season + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineSeason = sqlEngineFactory.getCheckedQueryEngine("SELECT_SEASON");
    //sqlControl = getMoreResultClasses(season, sqlControl);
    List<Season> seasonList = sqlEngineSeason.query(sqlSession, Season.class, season, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list season size: " + ((seasonList != null) ? seasonList.size() : "null"));
    }
    return seasonList;
  }
  
  public List<Season> list(final Season season, SqlControl sqlControl) {
    return list(sqlSessionFactory.getSqlSession(), season, sqlControl);
  }
  
  public List<Season> list(final SqlSession sqlSession, final Season season) {
    return list(sqlSession, season, null);
  }
  
  public List<Season> list(final Season season) {
    return list(season, null);
  }
  
  public int query(final SqlSession sqlSession, final Season season, SqlControl sqlControl, final SqlRowProcessor<Season> sqlRowProcessor) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql query season: " + season + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineSeason = sqlEngineFactory.getCheckedQueryEngine("SELECT_SEASON");
    //sqlControl = getMoreResultClasses(season, sqlControl);
    int rownums = sqlEngineSeason.query(sqlSession, Season.class, season, sqlControl, sqlRowProcessor);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql query season size: " + rownums);
    }
    return rownums;
  }
  
  public int query(final Season season, SqlControl sqlControl, final SqlRowProcessor<Season> sqlRowProcessor) {
    return query(sqlSessionFactory.getSqlSession(), season, sqlControl, sqlRowProcessor);
  }
  
  public int query(final SqlSession sqlSession, final Season season, final SqlRowProcessor<Season> sqlRowProcessor) {
    return query(sqlSession, season, null, sqlRowProcessor);
  }
  
  public int query(final Season season, final SqlRowProcessor<Season> sqlRowProcessor) {
    return query(season, null, sqlRowProcessor);
  }
  
  public List<Season> listFromTo(final SqlSession sqlSession, final Season season, SqlControl sqlControl) {
    if (sqlControl == null || sqlControl.getFirstResult() == null || sqlControl.getMaxResults() == null || season == null)
    	return list(sqlSession, season, sqlControl);
    
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list season: " + season + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineSeason = sqlEngineFactory.getCheckedQueryEngine("SELECT_SEASON");
    //sqlControl = getMoreResultClasses(season, sqlControl);
    season.setOnlyIds(true);
    java.util.Set<String> initAssociations = season.getInitAssociations();
    season.setInitAssociations(new java.util.HashSet<String>());
    final java.util.List<java.lang.Integer> ids = sqlEngineSeason.query(sqlSession, java.lang.Integer.class, season, sqlControl);
    season.setInitAssociations(initAssociations);
    
    List<Season> seasonList = new java.util.ArrayList<Season>();
    if (!ids.isEmpty()) {
    	org.sqlproc.engine.impl.SqlStandardControl sqlc = new org.sqlproc.engine.impl.SqlStandardControl(sqlControl);
    	sqlc.setFirstResult(0);
    	sqlc.setMaxResults(0);
    	sqlc.setOrder(null);
    	final Map<java.lang.Integer, Season> map = new java.util.HashMap<java.lang.Integer, Season>();
    	final SqlRowProcessor<Season> sqlRowProcessor = new SqlRowProcessor<Season>() {
    		@Override
    		public boolean processRow(Season result, int rownum) throws org.sqlproc.engine.SqlRuntimeException {
    			map.put(result.getId(), result);
    			return true;
    		}
    	};
    	sqlEngineSeason.query(sqlSession, Season.class, new Season()._setIds(ids), sqlc, sqlRowProcessor);
    	for (java.lang.Integer id : ids)
    		seasonList.add(map.get(id));
    }
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list season size: " + ((seasonList != null) ? seasonList.size() : "null"));
    }
    return seasonList;
  }
  
  public List<Season> listFromTo(final Season season, SqlControl sqlControl) {
    return listFromTo(sqlSessionFactory.getSqlSession(), season, sqlControl);
  }
  
  public List<Season> listFromTo(final SqlSession sqlSession, final Season season) {
    return listFromTo(sqlSession, season, null);
  }
  
  public List<Season> listFromTo(final Season season) {
    return listFromTo(season, null);
  }
  
  public int count(final SqlSession sqlSession, final Season season, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("count season: " + season + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineSeason = sqlEngineFactory.getCheckedQueryEngine("SELECT_SEASON");
    //sqlControl = getMoreResultClasses(season, sqlControl);
    int count = sqlEngineSeason.queryCount(sqlSession, season, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("count: " + count);
    }
    return count;
  }
  
  public int count(final Season season, SqlControl sqlControl) {
    return count(sqlSessionFactory.getSqlSession(), season, sqlControl);
  }
  
  public int count(final SqlSession sqlSession, final Season season) {
    return count(sqlSession, season, null);
  }
  
  public int count(final Season season) {
    return count(season, null);
  }
}
