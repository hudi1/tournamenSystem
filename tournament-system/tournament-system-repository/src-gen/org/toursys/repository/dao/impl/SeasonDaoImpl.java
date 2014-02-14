package org.toursys.repository.dao.impl;

import org.toursys.repository.dao.SeasonDao;

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
import org.toursys.repository.model.Season;

public class SeasonDaoImpl implements SeasonDao {
  protected final Logger logger = LoggerFactory.getLogger(getClass());

  protected SqlEngineFactory sqlEngineFactory;
  protected SqlSessionFactory sqlSessionFactory;
    	
  public SeasonDaoImpl() {
  }
    	
  public SeasonDaoImpl(SqlEngineFactory sqlEngineFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
  }
    	
  public SeasonDaoImpl(SqlEngineFactory sqlEngineFactory, SqlSessionFactory sqlSessionFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
    this.sqlSessionFactory = sqlSessionFactory;
  }
  
  
  public Season insert(SqlSession sqlSession, Season season, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("insert season: " + season + " " + sqlControl);
    }
    SqlCrudEngine sqlInsertSeason = sqlEngineFactory.getCheckedCrudEngine("INSERT_SEASON");
    int count = sqlInsertSeason.insert(sqlSession, season, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("insert season result: " + count + " " + season);
    }
    return (count > 0) ? season : null;
  }
  public Season insert(Season season, SqlControl sqlControl) {
  	return insert(sqlSessionFactory.getSqlSession(), season, sqlControl);
  }
  public Season insert(SqlSession sqlSession, Season season) {
    return insert(sqlSession, season, null);
  }
  public Season insert(Season season) {
    return insert(season, null);
  }
  
  public Season get(SqlSession sqlSession, Season season, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("get get: " + season + " " + sqlControl);
    }
    SqlCrudEngine sqlGetEngineSeason = sqlEngineFactory.getCheckedCrudEngine("GET_SEASON");
    //sqlControl = getMoreResultClasses(season, sqlControl);
    Season seasonGot = sqlGetEngineSeason.get(sqlSession, Season.class, season, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("get season result: " + seasonGot);
    }
    return seasonGot;
  }
  public Season get(Season season, SqlControl sqlControl) {
  	return get(sqlSessionFactory.getSqlSession(), season, sqlControl);
  }
  public Season get(SqlSession sqlSession, Season season) {
    return get(sqlSession, season, null);
  }
  public Season get(Season season) {
    return get(season, null);
  }
  
  public int update(SqlSession sqlSession, Season season, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("update season: " + season + " " + sqlControl);
    }
    SqlCrudEngine sqlUpdateEngineSeason = sqlEngineFactory.getCheckedCrudEngine("UPDATE_SEASON");
    int count = sqlUpdateEngineSeason.update(sqlSession, season, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("update season result count: " + count);
    }
    return count;
  }
  public int update(Season season, SqlControl sqlControl) {
  	return update(sqlSessionFactory.getSqlSession(), season, sqlControl);
  }
  public int update(SqlSession sqlSession, Season season) {
    return update(sqlSession, season, null);
  }
  public int update(Season season) {
    return update(season, null);
  }
  
  public int delete(SqlSession sqlSession, Season season, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("delete season: " + season + " " + sqlControl);
    }
    SqlCrudEngine sqlDeleteEngineSeason = sqlEngineFactory.getCheckedCrudEngine("DELETE_SEASON");
    int count = sqlDeleteEngineSeason.delete(sqlSession, season, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("delete season result count: " + count);
    }
    return count;
  }
  public int delete(Season season, SqlControl sqlControl) {
  	return delete(sqlSessionFactory.getSqlSession(), season, sqlControl);
  }
  public int delete(SqlSession sqlSession, Season season) {
    return delete(sqlSession, season, null);
  }
  public int delete(Season season) {
    return delete(season, null);
  }
  
  public List<Season> list(SqlSession sqlSession, Season season, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("list season: " + season + " " + sqlControl);
    }
    SqlQueryEngine sqlEngineSeason = sqlEngineFactory.getCheckedQueryEngine("SELECT_SEASON");
    //sqlControl = getMoreResultClasses(season, sqlControl);
    List<Season> seasonList = sqlEngineSeason.query(sqlSession, Season.class, season, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("list season size: " + ((seasonList != null) ? seasonList.size() : "null"));
    }
    return seasonList;
  }
  public List<Season> list(Season season, SqlControl sqlControl) {
  	return list(sqlSessionFactory.getSqlSession(), season, sqlControl);
  }
      public List<Season> list(SqlSession sqlSession, Season season) {
    return list(sqlSession, season, null);
  }
  public List<Season> list(Season season) {
    return list(season, null);
  }
  
  public int count(SqlSession sqlSession, Season season, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("count season: " + season + " " + sqlControl);
    }
    SqlQueryEngine sqlEngineSeason = sqlEngineFactory.getCheckedQueryEngine("SELECT_SEASON");
    //sqlControl = getMoreResultClasses(season, sqlControl);
    int count = sqlEngineSeason.queryCount(sqlSession, season, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("count: " + count);
    }
    return count;
  }
  public int count(Season season, SqlControl sqlControl) {
  	return count(sqlSessionFactory.getSqlSession(), season, sqlControl);
  }
      public int count(SqlSession sqlSession, Season season) {
    return count(sqlSession, season, null);
  }
  public int count(Season season) {
    return count(season, null);
  }
}
