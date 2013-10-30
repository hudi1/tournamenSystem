package org.toursys.repository.dao.impl;

import org.toursys.repository.dao.GroupsDao;

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
import org.toursys.repository.model.Groups;

public class GroupsDaoImpl implements GroupsDao {
  protected final Logger logger = LoggerFactory.getLogger(getClass());

  protected SqlEngineFactory sqlEngineFactory;
  protected SqlSessionFactory sqlSessionFactory;
    	
  public GroupsDaoImpl(SqlEngineFactory sqlEngineFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
  }
    	
  public GroupsDaoImpl(SqlEngineFactory sqlEngineFactory, SqlSessionFactory sqlSessionFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
    this.sqlSessionFactory = sqlSessionFactory;
  }
  
  
  public Groups insert(SqlSession sqlSession, Groups groups, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("insert groups: " + groups + " " + sqlControl);
    }
    SqlCrudEngine sqlInsertGroups = sqlEngineFactory.getCheckedCrudEngine("INSERT_GROUPS");
    int count = sqlInsertGroups.insert(sqlSession, groups, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("insert groups result: " + count + " " + groups);
    }
    return (count > 0) ? groups : null;
  }
  
  public Groups insert(Groups groups, SqlControl sqlControl) {
  	return insert(sqlSessionFactory.getSqlSession(), groups, sqlControl);
  }
  
  public Groups insert(SqlSession sqlSession, Groups groups) {
    return insert(sqlSession, groups, null);
  }
  
  public Groups insert(Groups groups) {
    return insert(groups, null);
  }
  
  public Groups get(SqlSession sqlSession, Groups groups, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("get get: " + groups + " " + sqlControl);
    }
    SqlCrudEngine sqlGetEngineGroups = sqlEngineFactory.getCheckedCrudEngine("GET_GROUPS");
    //sqlControl = getMoreResultClasses(groups, sqlControl);
    Groups groupsGot = sqlGetEngineGroups.get(sqlSession, Groups.class, groups, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("get groups result: " + groupsGot);
    }
    return groupsGot;
  }
  	
  public Groups get(Groups groups, SqlControl sqlControl) {
  	return get(sqlSessionFactory.getSqlSession(), groups, sqlControl);
  }
  
  public Groups get(SqlSession sqlSession, Groups groups) {
    return get(sqlSession, groups, null);
  }
  
  public Groups get(Groups groups) {
    return get(groups, null);
  }
  
  public int update(SqlSession sqlSession, Groups groups, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("update groups: " + groups + " " + sqlControl);
    }
    SqlCrudEngine sqlUpdateEngineGroups = sqlEngineFactory.getCheckedCrudEngine("UPDATE_GROUPS");
    int count = sqlUpdateEngineGroups.update(sqlSession, groups, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("update groups result count: " + count);
    }
    return count;
  }
  
  public int update(Groups groups, SqlControl sqlControl) {
  	return update(sqlSessionFactory.getSqlSession(), groups, sqlControl);
  }
  
  public int update(SqlSession sqlSession, Groups groups) {
    return update(sqlSession, groups, null);
  }
  
  public int update(Groups groups) {
    return update(groups, null);
  }
  
  public int delete(SqlSession sqlSession, Groups groups, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("delete groups: " + groups + " " + sqlControl);
    }
    SqlCrudEngine sqlDeleteEngineGroups = sqlEngineFactory.getCheckedCrudEngine("DELETE_GROUPS");
    int count = sqlDeleteEngineGroups.delete(sqlSession, groups, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("delete groups result count: " + count);
    }
    return count;
  }
  
  public int delete(Groups groups, SqlControl sqlControl) {
  	return delete(sqlSessionFactory.getSqlSession(), groups, sqlControl);
  }
  
  public int delete(SqlSession sqlSession, Groups groups) {
    return delete(sqlSession, groups, null);
  }
  
  public int delete(Groups groups) {
    return delete(groups, null);
  }
  
  public List<Groups> list(SqlSession sqlSession, Groups groups, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("list groups: " + groups + " " + sqlControl);
    }
    SqlQueryEngine sqlEngineGroups = sqlEngineFactory.getCheckedQueryEngine("SELECT_GROUPS");
    //sqlControl = getMoreResultClasses(groups, sqlControl);
    List<Groups> groupsList = sqlEngineGroups.query(sqlSession, Groups.class, groups, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("list groups size: " + ((groupsList != null) ? groupsList.size() : "null"));
    }
    return groupsList;
  }
  
  public List<Groups> list(Groups groups, SqlControl sqlControl) {
  	return list(sqlSessionFactory.getSqlSession(), groups, sqlControl);
  }
  
  public List<Groups> list(SqlSession sqlSession, Groups groups) {
    return list(sqlSession, groups, null);
  }
  
  public List<Groups> list(Groups groups) {
    return list(groups, null);
  }
  
  public int count(SqlSession sqlSession, Groups groups, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("count groups: " + groups + " " + sqlControl);
    }
    SqlQueryEngine sqlEngineGroups = sqlEngineFactory.getCheckedQueryEngine("SELECT_GROUPS");
    //sqlControl = getMoreResultClasses(groups, sqlControl);
    int count = sqlEngineGroups.queryCount(sqlSession, groups, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("count: " + count);
    }
    return count;
  }
  
  public int count(Groups groups, SqlControl sqlControl) {
  	return count(sqlSessionFactory.getSqlSession(), groups, sqlControl);
  }
  
  public int count(SqlSession sqlSession, Groups groups) {
    return count(sqlSession, groups, null);
  }
  
  public int count(Groups groups) {
    return count(groups, null);
  }
}
