package org.tahom.repository.dao.impl;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.sqlproc.engine.SqlControl;
import org.sqlproc.engine.SqlEngineFactory;
import org.sqlproc.engine.SqlRowProcessor;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlSessionFactory;
import org.tahom.repository.dao.WchQualificationDao;
import org.tahom.repository.model.WchQualification;

@SuppressWarnings("all")
public class WchQualificationDaoImpl implements WchQualificationDao {
  protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
  
  public WchQualificationDaoImpl() {
  }
  
  public WchQualificationDaoImpl(final SqlEngineFactory sqlEngineFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
  }
  
  public WchQualificationDaoImpl(final SqlEngineFactory sqlEngineFactory, final SqlSessionFactory sqlSessionFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
    this.sqlSessionFactory = sqlSessionFactory;
  }
  
  protected SqlEngineFactory sqlEngineFactory;
  
  protected SqlSessionFactory sqlSessionFactory;
  
  public WchQualification insert(final SqlSession sqlSession, final WchQualification wchQualification, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql insert wchQualification: " + wchQualification + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlInsertWchQualification = sqlEngineFactory.getCheckedCrudEngine("INSERT_WCH_QUALIFICATION");
    int count = sqlInsertWchQualification.insert(sqlSession, wchQualification, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql insert wchQualification result: " + count + " " + wchQualification);
    }
    return (count > 0) ? wchQualification : null;
  }
  
  public WchQualification insert(final WchQualification wchQualification, SqlControl sqlControl) {
    return insert(sqlSessionFactory.getSqlSession(), wchQualification, sqlControl);
  }
  
  public WchQualification insert(final SqlSession sqlSession, final WchQualification wchQualification) {
    return insert(sqlSession, wchQualification, null);
  }
  
  public WchQualification insert(final WchQualification wchQualification) {
    return insert(wchQualification, null);
  }
  
  public WchQualification get(final SqlSession sqlSession, final WchQualification wchQualification, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql get: " + wchQualification + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlGetEngineWchQualification = sqlEngineFactory.getCheckedCrudEngine("GET_WCH_QUALIFICATION");
    //sqlControl = getMoreResultClasses(wchQualification, sqlControl);
    WchQualification wchQualificationGot = sqlGetEngineWchQualification.get(sqlSession, WchQualification.class, wchQualification, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql get wchQualification result: " + wchQualificationGot);
    }
    return wchQualificationGot;
  }
  
  public WchQualification get(final WchQualification wchQualification, SqlControl sqlControl) {
    return get(sqlSessionFactory.getSqlSession(), wchQualification, sqlControl);
  }
  
  public WchQualification get(final SqlSession sqlSession, final WchQualification wchQualification) {
    return get(sqlSession, wchQualification, null);
  }
  
  public WchQualification get(final WchQualification wchQualification) {
    return get(wchQualification, null);
  }
  
  public int update(final SqlSession sqlSession, final WchQualification wchQualification, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql update wchQualification: " + wchQualification + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlUpdateEngineWchQualification = sqlEngineFactory.getCheckedCrudEngine("UPDATE_WCH_QUALIFICATION");
    int count = sqlUpdateEngineWchQualification.update(sqlSession, wchQualification, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql update wchQualification result count: " + count);
    }
    return count;
  }
  
  public int update(final WchQualification wchQualification, SqlControl sqlControl) {
    return update(sqlSessionFactory.getSqlSession(), wchQualification, sqlControl);
  }
  
  public int update(final SqlSession sqlSession, final WchQualification wchQualification) {
    return update(sqlSession, wchQualification, null);
  }
  
  public int update(final WchQualification wchQualification) {
    return update(wchQualification, null);
  }
  
  public int delete(final SqlSession sqlSession, final WchQualification wchQualification, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql delete wchQualification: " + wchQualification + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlDeleteEngineWchQualification = sqlEngineFactory.getCheckedCrudEngine("DELETE_WCH_QUALIFICATION");
    int count = sqlDeleteEngineWchQualification.delete(sqlSession, wchQualification, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql delete wchQualification result count: " + count);
    }
    return count;
  }
  
  public int delete(final WchQualification wchQualification, SqlControl sqlControl) {
    return delete(sqlSessionFactory.getSqlSession(), wchQualification, sqlControl);
  }
  
  public int delete(final SqlSession sqlSession, final WchQualification wchQualification) {
    return delete(sqlSession, wchQualification, null);
  }
  
  public int delete(final WchQualification wchQualification) {
    return delete(wchQualification, null);
  }
  
  public List<WchQualification> list(final SqlSession sqlSession, final WchQualification wchQualification, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list wchQualification: " + wchQualification + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineWchQualification = sqlEngineFactory.getCheckedQueryEngine("SELECT_WCH_QUALIFICATION");
    //sqlControl = getMoreResultClasses(wchQualification, sqlControl);
    List<WchQualification> wchQualificationList = sqlEngineWchQualification.query(sqlSession, WchQualification.class, wchQualification, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list wchQualification size: " + ((wchQualificationList != null) ? wchQualificationList.size() : "null"));
    }
    return wchQualificationList;
  }
  
  public List<WchQualification> list(final WchQualification wchQualification, SqlControl sqlControl) {
    return list(sqlSessionFactory.getSqlSession(), wchQualification, sqlControl);
  }
  
  public List<WchQualification> list(final SqlSession sqlSession, final WchQualification wchQualification) {
    return list(sqlSession, wchQualification, null);
  }
  
  public List<WchQualification> list(final WchQualification wchQualification) {
    return list(wchQualification, null);
  }
  
  public int query(final SqlSession sqlSession, final WchQualification wchQualification, SqlControl sqlControl, final SqlRowProcessor<WchQualification> sqlRowProcessor) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql query wchQualification: " + wchQualification + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineWchQualification = sqlEngineFactory.getCheckedQueryEngine("SELECT_WCH_QUALIFICATION");
    //sqlControl = getMoreResultClasses(wchQualification, sqlControl);
    int rownums = sqlEngineWchQualification.query(sqlSession, WchQualification.class, wchQualification, sqlControl, sqlRowProcessor);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql query wchQualification size: " + rownums);
    }
    return rownums;
  }
  
  public int query(final WchQualification wchQualification, SqlControl sqlControl, final SqlRowProcessor<WchQualification> sqlRowProcessor) {
    return query(sqlSessionFactory.getSqlSession(), wchQualification, sqlControl, sqlRowProcessor);
  }
  
  public int query(final SqlSession sqlSession, final WchQualification wchQualification, final SqlRowProcessor<WchQualification> sqlRowProcessor) {
    return query(sqlSession, wchQualification, null, sqlRowProcessor);
  }
  
  public int query(final WchQualification wchQualification, final SqlRowProcessor<WchQualification> sqlRowProcessor) {
    return query(wchQualification, null, sqlRowProcessor);
  }
  
  public List<WchQualification> listFromTo(final SqlSession sqlSession, final WchQualification wchQualification, SqlControl sqlControl) {
    if (sqlControl == null || sqlControl.getFirstResult() == null || sqlControl.getMaxResults() == null || wchQualification == null)
    	return list(sqlSession, wchQualification, sqlControl);
    
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list wchQualification: " + wchQualification + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineWchQualification = sqlEngineFactory.getCheckedQueryEngine("SELECT_WCH_QUALIFICATION");
    //sqlControl = getMoreResultClasses(wchQualification, sqlControl);
    wchQualification.setOnlyIds(true);
    java.util.Set<String> initAssociations = wchQualification.getInitAssociations();
    wchQualification.setInitAssociations(new java.util.HashSet<String>());
    final java.util.List<java.lang.Integer> ids = sqlEngineWchQualification.query(sqlSession, java.lang.Integer.class, wchQualification, sqlControl);
    wchQualification.setInitAssociations(initAssociations);
    
    List<WchQualification> wchQualificationList = new java.util.ArrayList<WchQualification>();
    if (!ids.isEmpty()) {
    	org.sqlproc.engine.impl.SqlStandardControl sqlc = new org.sqlproc.engine.impl.SqlStandardControl(sqlControl);
    	sqlc.setFirstResult(0);
    	sqlc.setMaxResults(0);
    	sqlc.setOrder(null);
    	final Map<java.lang.Integer, WchQualification> map = new java.util.HashMap<java.lang.Integer, WchQualification>();
    	final SqlRowProcessor<WchQualification> sqlRowProcessor = new SqlRowProcessor<WchQualification>() {
    		@Override
    		public boolean processRow(WchQualification result, int rownum) throws org.sqlproc.engine.SqlRuntimeException {
    			map.put(result.getId(), result);
    			return true;
    		}
    	};
    	sqlEngineWchQualification.query(sqlSession, WchQualification.class, new WchQualification()._setIds(ids), sqlc, sqlRowProcessor);
    	for (java.lang.Integer id : ids)
    		wchQualificationList.add(map.get(id));
    }
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list wchQualification size: " + ((wchQualificationList != null) ? wchQualificationList.size() : "null"));
    }
    return wchQualificationList;
  }
  
  public List<WchQualification> listFromTo(final WchQualification wchQualification, SqlControl sqlControl) {
    return listFromTo(sqlSessionFactory.getSqlSession(), wchQualification, sqlControl);
  }
  
  public List<WchQualification> listFromTo(final SqlSession sqlSession, final WchQualification wchQualification) {
    return listFromTo(sqlSession, wchQualification, null);
  }
  
  public List<WchQualification> listFromTo(final WchQualification wchQualification) {
    return listFromTo(wchQualification, null);
  }
  
  public int count(final SqlSession sqlSession, final WchQualification wchQualification, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("count wchQualification: " + wchQualification + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineWchQualification = sqlEngineFactory.getCheckedQueryEngine("SELECT_WCH_QUALIFICATION");
    //sqlControl = getMoreResultClasses(wchQualification, sqlControl);
    int count = sqlEngineWchQualification.queryCount(sqlSession, wchQualification, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("count: " + count);
    }
    return count;
  }
  
  public int count(final WchQualification wchQualification, SqlControl sqlControl) {
    return count(sqlSessionFactory.getSqlSession(), wchQualification, sqlControl);
  }
  
  public int count(final SqlSession sqlSession, final WchQualification wchQualification) {
    return count(sqlSession, wchQualification, null);
  }
  
  public int count(final WchQualification wchQualification) {
    return count(wchQualification, null);
  }
}
