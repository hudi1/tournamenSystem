package org.tahom.repository.dao.impl;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.sqlproc.engine.SqlControl;
import org.sqlproc.engine.SqlEngineFactory;
import org.sqlproc.engine.SqlRowProcessor;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlSessionFactory;
import org.tahom.repository.dao.FinalStandingDao;
import org.tahom.repository.model.FinalStanding;

@SuppressWarnings("all")
public class FinalStandingDaoImpl implements FinalStandingDao {
  protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
  
  public FinalStandingDaoImpl() {
  }
  
  public FinalStandingDaoImpl(final SqlEngineFactory sqlEngineFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
  }
  
  public FinalStandingDaoImpl(final SqlEngineFactory sqlEngineFactory, final SqlSessionFactory sqlSessionFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
    this.sqlSessionFactory = sqlSessionFactory;
  }
  
  protected SqlEngineFactory sqlEngineFactory;
  
  protected SqlSessionFactory sqlSessionFactory;
  
  public FinalStanding insert(final SqlSession sqlSession, final FinalStanding finalStanding, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql insert finalStanding: " + finalStanding + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlInsertFinalStanding = sqlEngineFactory.getCheckedCrudEngine("INSERT_FINAL_STANDING");
    int count = sqlInsertFinalStanding.insert(sqlSession, finalStanding, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql insert finalStanding result: " + count + " " + finalStanding);
    }
    return (count > 0) ? finalStanding : null;
  }
  
  public FinalStanding insert(final FinalStanding finalStanding, SqlControl sqlControl) {
    return insert(sqlSessionFactory.getSqlSession(), finalStanding, sqlControl);
  }
  
  public FinalStanding insert(final SqlSession sqlSession, final FinalStanding finalStanding) {
    return insert(sqlSession, finalStanding, null);
  }
  
  public FinalStanding insert(final FinalStanding finalStanding) {
    return insert(finalStanding, null);
  }
  
  public FinalStanding get(final SqlSession sqlSession, final FinalStanding finalStanding, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql get: " + finalStanding + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlGetEngineFinalStanding = sqlEngineFactory.getCheckedCrudEngine("GET_FINAL_STANDING");
    //sqlControl = getMoreResultClasses(finalStanding, sqlControl);
    FinalStanding finalStandingGot = sqlGetEngineFinalStanding.get(sqlSession, FinalStanding.class, finalStanding, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql get finalStanding result: " + finalStandingGot);
    }
    return finalStandingGot;
  }
  
  public FinalStanding get(final FinalStanding finalStanding, SqlControl sqlControl) {
    return get(sqlSessionFactory.getSqlSession(), finalStanding, sqlControl);
  }
  
  public FinalStanding get(final SqlSession sqlSession, final FinalStanding finalStanding) {
    return get(sqlSession, finalStanding, null);
  }
  
  public FinalStanding get(final FinalStanding finalStanding) {
    return get(finalStanding, null);
  }
  
  public int update(final SqlSession sqlSession, final FinalStanding finalStanding, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql update finalStanding: " + finalStanding + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlUpdateEngineFinalStanding = sqlEngineFactory.getCheckedCrudEngine("UPDATE_FINAL_STANDING");
    int count = sqlUpdateEngineFinalStanding.update(sqlSession, finalStanding, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql update finalStanding result count: " + count);
    }
    return count;
  }
  
  public int update(final FinalStanding finalStanding, SqlControl sqlControl) {
    return update(sqlSessionFactory.getSqlSession(), finalStanding, sqlControl);
  }
  
  public int update(final SqlSession sqlSession, final FinalStanding finalStanding) {
    return update(sqlSession, finalStanding, null);
  }
  
  public int update(final FinalStanding finalStanding) {
    return update(finalStanding, null);
  }
  
  public int delete(final SqlSession sqlSession, final FinalStanding finalStanding, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql delete finalStanding: " + finalStanding + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlDeleteEngineFinalStanding = sqlEngineFactory.getCheckedCrudEngine("DELETE_FINAL_STANDING");
    int count = sqlDeleteEngineFinalStanding.delete(sqlSession, finalStanding, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql delete finalStanding result count: " + count);
    }
    return count;
  }
  
  public int delete(final FinalStanding finalStanding, SqlControl sqlControl) {
    return delete(sqlSessionFactory.getSqlSession(), finalStanding, sqlControl);
  }
  
  public int delete(final SqlSession sqlSession, final FinalStanding finalStanding) {
    return delete(sqlSession, finalStanding, null);
  }
  
  public int delete(final FinalStanding finalStanding) {
    return delete(finalStanding, null);
  }
  
  public List<FinalStanding> list(final SqlSession sqlSession, final FinalStanding finalStanding, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list finalStanding: " + finalStanding + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineFinalStanding = sqlEngineFactory.getCheckedQueryEngine("SELECT_FINAL_STANDING");
    //sqlControl = getMoreResultClasses(finalStanding, sqlControl);
    List<FinalStanding> finalStandingList = sqlEngineFinalStanding.query(sqlSession, FinalStanding.class, finalStanding, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list finalStanding size: " + ((finalStandingList != null) ? finalStandingList.size() : "null"));
    }
    return finalStandingList;
  }
  
  public List<FinalStanding> list(final FinalStanding finalStanding, SqlControl sqlControl) {
    return list(sqlSessionFactory.getSqlSession(), finalStanding, sqlControl);
  }
  
  public List<FinalStanding> list(final SqlSession sqlSession, final FinalStanding finalStanding) {
    return list(sqlSession, finalStanding, null);
  }
  
  public List<FinalStanding> list(final FinalStanding finalStanding) {
    return list(finalStanding, null);
  }
  
  public int query(final SqlSession sqlSession, final FinalStanding finalStanding, SqlControl sqlControl, final SqlRowProcessor<FinalStanding> sqlRowProcessor) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql query finalStanding: " + finalStanding + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineFinalStanding = sqlEngineFactory.getCheckedQueryEngine("SELECT_FINAL_STANDING");
    //sqlControl = getMoreResultClasses(finalStanding, sqlControl);
    int rownums = sqlEngineFinalStanding.query(sqlSession, FinalStanding.class, finalStanding, sqlControl, sqlRowProcessor);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql query finalStanding size: " + rownums);
    }
    return rownums;
  }
  
  public int query(final FinalStanding finalStanding, SqlControl sqlControl, final SqlRowProcessor<FinalStanding> sqlRowProcessor) {
    return query(sqlSessionFactory.getSqlSession(), finalStanding, sqlControl, sqlRowProcessor);
  }
  
  public int query(final SqlSession sqlSession, final FinalStanding finalStanding, final SqlRowProcessor<FinalStanding> sqlRowProcessor) {
    return query(sqlSession, finalStanding, null, sqlRowProcessor);
  }
  
  public int query(final FinalStanding finalStanding, final SqlRowProcessor<FinalStanding> sqlRowProcessor) {
    return query(finalStanding, null, sqlRowProcessor);
  }
  
  public List<FinalStanding> listFromTo(final SqlSession sqlSession, final FinalStanding finalStanding, SqlControl sqlControl) {
    if (sqlControl == null || sqlControl.getFirstResult() == null || sqlControl.getMaxResults() == null || finalStanding == null)
    	return list(sqlSession, finalStanding, sqlControl);
    
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list finalStanding: " + finalStanding + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineFinalStanding = sqlEngineFactory.getCheckedQueryEngine("SELECT_FINAL_STANDING");
    //sqlControl = getMoreResultClasses(finalStanding, sqlControl);
    finalStanding.setOnlyIds_(true);
    java.util.Set<String> initAssociations = finalStanding.getInitAssociations_();
    finalStanding.setInitAssociations_(new java.util.HashSet<String>());
    final java.util.List<java.lang.Integer> ids_ = sqlEngineFinalStanding.query(sqlSession, java.lang.Integer.class, finalStanding, sqlControl);
    finalStanding.setInitAssociations_(initAssociations);
    
    List<FinalStanding> finalStandingList = new java.util.ArrayList<FinalStanding>();
    if (!ids_.isEmpty()) {
    	org.sqlproc.engine.impl.SqlStandardControl sqlc = new org.sqlproc.engine.impl.SqlStandardControl(sqlControl);
    	sqlc.setFirstResult(0);
    	sqlc.setMaxResults(0);
    	sqlc.setOrder(null);
    	final Map<java.lang.Integer, FinalStanding> map = new java.util.HashMap<java.lang.Integer, FinalStanding>();
    	final SqlRowProcessor<FinalStanding> sqlRowProcessor = new SqlRowProcessor<FinalStanding>() {
    		@Override
    		public boolean processRow(FinalStanding result, int rownum) throws org.sqlproc.engine.SqlRuntimeException {
    			map.put(result.getId(), result);
    			return true;
    		}
    	};
    	sqlEngineFinalStanding.query(sqlSession, FinalStanding.class, new FinalStanding()._setIds_(ids_), sqlc, sqlRowProcessor);
    	for (java.lang.Integer id : ids_)
    		finalStandingList.add(map.get(id));
    }
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list finalStanding size: " + ((finalStandingList != null) ? finalStandingList.size() : "null"));
    }
    return finalStandingList;
  }
  
  public List<FinalStanding> listFromTo(final FinalStanding finalStanding, SqlControl sqlControl) {
    return listFromTo(sqlSessionFactory.getSqlSession(), finalStanding, sqlControl);
  }
  
  public List<FinalStanding> listFromTo(final SqlSession sqlSession, final FinalStanding finalStanding) {
    return listFromTo(sqlSession, finalStanding, null);
  }
  
  public List<FinalStanding> listFromTo(final FinalStanding finalStanding) {
    return listFromTo(finalStanding, null);
  }
  
  public int count(final SqlSession sqlSession, final FinalStanding finalStanding, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("count finalStanding: " + finalStanding + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineFinalStanding = sqlEngineFactory.getCheckedQueryEngine("SELECT_FINAL_STANDING");
    //sqlControl = getMoreResultClasses(finalStanding, sqlControl);
    int count = sqlEngineFinalStanding.queryCount(sqlSession, finalStanding, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("count: " + count);
    }
    return count;
  }
  
  public int count(final FinalStanding finalStanding, SqlControl sqlControl) {
    return count(sqlSessionFactory.getSqlSession(), finalStanding, sqlControl);
  }
  
  public int count(final SqlSession sqlSession, final FinalStanding finalStanding) {
    return count(sqlSession, finalStanding, null);
  }
  
  public int count(final FinalStanding finalStanding) {
    return count(finalStanding, null);
  }
}
