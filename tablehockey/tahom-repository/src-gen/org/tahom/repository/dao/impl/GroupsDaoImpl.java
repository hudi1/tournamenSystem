package org.tahom.repository.dao.impl;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.sqlproc.engine.SqlControl;
import org.sqlproc.engine.SqlEngineFactory;
import org.sqlproc.engine.SqlRowProcessor;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlSessionFactory;
import org.tahom.repository.dao.GroupsDao;
import org.tahom.repository.model.Groups;

@SuppressWarnings("all")
public class GroupsDaoImpl implements GroupsDao {
  protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
  
  public GroupsDaoImpl() {
  }
  
  public GroupsDaoImpl(final SqlEngineFactory sqlEngineFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
  }
  
  public GroupsDaoImpl(final SqlEngineFactory sqlEngineFactory, final SqlSessionFactory sqlSessionFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
    this.sqlSessionFactory = sqlSessionFactory;
  }
  
  protected SqlEngineFactory sqlEngineFactory;
  
  protected SqlSessionFactory sqlSessionFactory;
  
  public Groups insert(final SqlSession sqlSession, final Groups groups, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql insert groups: " + groups + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlInsertGroups = sqlEngineFactory.getCheckedCrudEngine("INSERT_GROUPS");
    int count = sqlInsertGroups.insert(sqlSession, groups, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql insert groups result: " + count + " " + groups);
    }
    return (count > 0) ? groups : null;
  }
  
  public Groups insert(final Groups groups, SqlControl sqlControl) {
    return insert(sqlSessionFactory.getSqlSession(), groups, sqlControl);
  }
  
  public Groups insert(final SqlSession sqlSession, final Groups groups) {
    return insert(sqlSession, groups, null);
  }
  
  public Groups insert(final Groups groups) {
    return insert(groups, null);
  }
  
  public Groups get(final SqlSession sqlSession, final Groups groups, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql get: " + groups + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlGetEngineGroups = sqlEngineFactory.getCheckedCrudEngine("GET_GROUPS");
    //sqlControl = getMoreResultClasses(groups, sqlControl);
    Groups groupsGot = sqlGetEngineGroups.get(sqlSession, Groups.class, groups, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql get groups result: " + groupsGot);
    }
    return groupsGot;
  }
  
  public Groups get(final Groups groups, SqlControl sqlControl) {
    return get(sqlSessionFactory.getSqlSession(), groups, sqlControl);
  }
  
  public Groups get(final SqlSession sqlSession, final Groups groups) {
    return get(sqlSession, groups, null);
  }
  
  public Groups get(final Groups groups) {
    return get(groups, null);
  }
  
  public int update(final SqlSession sqlSession, final Groups groups, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql update groups: " + groups + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlUpdateEngineGroups = sqlEngineFactory.getCheckedCrudEngine("UPDATE_GROUPS");
    int count = sqlUpdateEngineGroups.update(sqlSession, groups, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql update groups result count: " + count);
    }
    return count;
  }
  
  public int update(final Groups groups, SqlControl sqlControl) {
    return update(sqlSessionFactory.getSqlSession(), groups, sqlControl);
  }
  
  public int update(final SqlSession sqlSession, final Groups groups) {
    return update(sqlSession, groups, null);
  }
  
  public int update(final Groups groups) {
    return update(groups, null);
  }
  
  public int delete(final SqlSession sqlSession, final Groups groups, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql delete groups: " + groups + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlDeleteEngineGroups = sqlEngineFactory.getCheckedCrudEngine("DELETE_GROUPS");
    int count = sqlDeleteEngineGroups.delete(sqlSession, groups, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql delete groups result count: " + count);
    }
    return count;
  }
  
  public int delete(final Groups groups, SqlControl sqlControl) {
    return delete(sqlSessionFactory.getSqlSession(), groups, sqlControl);
  }
  
  public int delete(final SqlSession sqlSession, final Groups groups) {
    return delete(sqlSession, groups, null);
  }
  
  public int delete(final Groups groups) {
    return delete(groups, null);
  }
  
  public List<Groups> list(final SqlSession sqlSession, final Groups groups, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list groups: " + groups + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineGroups = sqlEngineFactory.getCheckedQueryEngine("SELECT_GROUPS");
    //sqlControl = getMoreResultClasses(groups, sqlControl);
    List<Groups> groupsList = sqlEngineGroups.query(sqlSession, Groups.class, groups, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list groups size: " + ((groupsList != null) ? groupsList.size() : "null"));
    }
    return groupsList;
  }
  
  public List<Groups> list(final Groups groups, SqlControl sqlControl) {
    return list(sqlSessionFactory.getSqlSession(), groups, sqlControl);
  }
  
  public List<Groups> list(final SqlSession sqlSession, final Groups groups) {
    return list(sqlSession, groups, null);
  }
  
  public List<Groups> list(final Groups groups) {
    return list(groups, null);
  }
  
  public int query(final SqlSession sqlSession, final Groups groups, SqlControl sqlControl, final SqlRowProcessor<Groups> sqlRowProcessor) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql query groups: " + groups + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineGroups = sqlEngineFactory.getCheckedQueryEngine("SELECT_GROUPS");
    //sqlControl = getMoreResultClasses(groups, sqlControl);
    int rownums = sqlEngineGroups.query(sqlSession, Groups.class, groups, sqlControl, sqlRowProcessor);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql query groups size: " + rownums);
    }
    return rownums;
  }
  
  public int query(final Groups groups, SqlControl sqlControl, final SqlRowProcessor<Groups> sqlRowProcessor) {
    return query(sqlSessionFactory.getSqlSession(), groups, sqlControl, sqlRowProcessor);
  }
  
  public int query(final SqlSession sqlSession, final Groups groups, final SqlRowProcessor<Groups> sqlRowProcessor) {
    return query(sqlSession, groups, null, sqlRowProcessor);
  }
  
  public int query(final Groups groups, final SqlRowProcessor<Groups> sqlRowProcessor) {
    return query(groups, null, sqlRowProcessor);
  }
  
  public List<Groups> listFromTo(final SqlSession sqlSession, final Groups groups, SqlControl sqlControl) {
    if (sqlControl == null || sqlControl.getFirstResult() == null || sqlControl.getMaxResults() == null || groups == null)
    	return list(sqlSession, groups, sqlControl);
    
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list groups: " + groups + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineGroups = sqlEngineFactory.getCheckedQueryEngine("SELECT_GROUPS");
    //sqlControl = getMoreResultClasses(groups, sqlControl);
    groups.setOnlyIds_(true);
    java.util.Set<String> initAssociations = groups.getInitAssociations_();
    groups.setInitAssociations_(new java.util.HashSet<String>());
    final java.util.List<java.lang.Integer> ids_ = sqlEngineGroups.query(sqlSession, java.lang.Integer.class, groups, sqlControl);
    groups.setInitAssociations_(initAssociations);
    
    List<Groups> groupsList = new java.util.ArrayList<Groups>();
    if (!ids_.isEmpty()) {
    	org.sqlproc.engine.impl.SqlStandardControl sqlc = new org.sqlproc.engine.impl.SqlStandardControl(sqlControl);
    	sqlc.setFirstResult(0);
    	sqlc.setMaxResults(0);
    	sqlc.setOrder(null);
    	final Map<java.lang.Integer, Groups> map = new java.util.HashMap<java.lang.Integer, Groups>();
    	final SqlRowProcessor<Groups> sqlRowProcessor = new SqlRowProcessor<Groups>() {
    		@Override
    		public boolean processRow(Groups result, int rownum) throws org.sqlproc.engine.SqlRuntimeException {
    			map.put(result.getId(), result);
    			return true;
    		}
    	};
    	sqlEngineGroups.query(sqlSession, Groups.class, new Groups()._setIds_(ids_), sqlc, sqlRowProcessor);
    	for (java.lang.Integer id : ids_)
    		groupsList.add(map.get(id));
    }
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list groups size: " + ((groupsList != null) ? groupsList.size() : "null"));
    }
    return groupsList;
  }
  
  public List<Groups> listFromTo(final Groups groups, SqlControl sqlControl) {
    return listFromTo(sqlSessionFactory.getSqlSession(), groups, sqlControl);
  }
  
  public List<Groups> listFromTo(final SqlSession sqlSession, final Groups groups) {
    return listFromTo(sqlSession, groups, null);
  }
  
  public List<Groups> listFromTo(final Groups groups) {
    return listFromTo(groups, null);
  }
  
  public int count(final SqlSession sqlSession, final Groups groups, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("count groups: " + groups + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineGroups = sqlEngineFactory.getCheckedQueryEngine("SELECT_GROUPS");
    //sqlControl = getMoreResultClasses(groups, sqlControl);
    int count = sqlEngineGroups.queryCount(sqlSession, groups, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("count: " + count);
    }
    return count;
  }
  
  public int count(final Groups groups, SqlControl sqlControl) {
    return count(sqlSessionFactory.getSqlSession(), groups, sqlControl);
  }
  
  public int count(final SqlSession sqlSession, final Groups groups) {
    return count(sqlSession, groups, null);
  }
  
  public int count(final Groups groups) {
    return count(groups, null);
  }
}
