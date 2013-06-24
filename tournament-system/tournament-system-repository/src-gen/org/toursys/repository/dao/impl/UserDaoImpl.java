package org.toursys.repository.dao.impl;

import org.toursys.repository.dao.UserDao;

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
import org.toursys.repository.model.User;

public class UserDaoImpl implements UserDao {
  protected final Logger logger = LoggerFactory.getLogger(getClass());

  protected SqlEngineFactory sqlEngineFactory;
  protected SqlSessionFactory sqlSessionFactory;
    	
  public UserDaoImpl(SqlEngineFactory sqlEngineFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
  }
    	
  public UserDaoImpl(SqlEngineFactory sqlEngineFactory, SqlSessionFactory sqlSessionFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
    this.sqlSessionFactory = sqlSessionFactory;
  }
  
  
  public User insert(SqlSession sqlSession, User user, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("insert user: " + user + " " + sqlControl);
    }
    SqlCrudEngine sqlInsertUser = sqlEngineFactory.getCheckedCrudEngine("INSERT_USER");
    int count = sqlInsertUser.insert(sqlSession, user, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("insert user result: " + count + " " + user);
    }
    return (count > 0) ? user : null;
  }
  
  public User insert(User user, SqlControl sqlControl) {
  	return insert(sqlSessionFactory.getSqlSession(), user, sqlControl);
  }
  
  public User insert(SqlSession sqlSession, User user) {
    return insert(sqlSession, user, null);
  }
  
  public User insert(User user) {
    return insert(user, null);
  }
  
  public User get(SqlSession sqlSession, User user, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("get get: " + user + " " + sqlControl);
    }
    SqlCrudEngine sqlGetEngineUser = sqlEngineFactory.getCheckedCrudEngine("GET_USER");
    //sqlControl = getMoreResultClasses(user, sqlControl);
    User userGot = sqlGetEngineUser.get(sqlSession, User.class, user, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("get user result: " + userGot);
    }
    return userGot;
  }
  	
  public User get(User user, SqlControl sqlControl) {
  	return get(sqlSessionFactory.getSqlSession(), user, sqlControl);
  }
  
  public User get(SqlSession sqlSession, User user) {
    return get(sqlSession, user, null);
  }
  
  public User get(User user) {
    return get(user, null);
  }
  
  public int update(SqlSession sqlSession, User user, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("update user: " + user + " " + sqlControl);
    }
    SqlCrudEngine sqlUpdateEngineUser = sqlEngineFactory.getCheckedCrudEngine("UPDATE_USER");
    int count = sqlUpdateEngineUser.update(sqlSession, user, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("update user result count: " + count);
    }
    return count;
  }
  
  public int update(User user, SqlControl sqlControl) {
  	return update(sqlSessionFactory.getSqlSession(), user, sqlControl);
  }
  
  public int update(SqlSession sqlSession, User user) {
    return update(sqlSession, user, null);
  }
  
  public int update(User user) {
    return update(user, null);
  }
  
  public int delete(SqlSession sqlSession, User user, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("delete user: " + user + " " + sqlControl);
    }
    SqlCrudEngine sqlDeleteEngineUser = sqlEngineFactory.getCheckedCrudEngine("DELETE_USER");
    int count = sqlDeleteEngineUser.delete(sqlSession, user, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("delete user result count: " + count);
    }
    return count;
  }
  
  public int delete(User user, SqlControl sqlControl) {
  	return delete(sqlSessionFactory.getSqlSession(), user, sqlControl);
  }
  
  public int delete(SqlSession sqlSession, User user) {
    return delete(sqlSession, user, null);
  }
  
  public int delete(User user) {
    return delete(user, null);
  }
  
  public List<User> list(SqlSession sqlSession, User user, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("list user: " + user + " " + sqlControl);
    }
    SqlQueryEngine sqlEngineUser = sqlEngineFactory.getCheckedQueryEngine("SELECT_USER");
    //sqlControl = getMoreResultClasses(user, sqlControl);
    List<User> userList = sqlEngineUser.query(sqlSession, User.class, user, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("list user size: " + ((userList != null) ? userList.size() : "null"));
    }
    return userList;
  }
  
  public List<User> list(User user, SqlControl sqlControl) {
  	return list(sqlSessionFactory.getSqlSession(), user, sqlControl);
  }
  
  public List<User> list(SqlSession sqlSession, User user) {
    return list(sqlSession, user, null);
  }
  
  public List<User> list(User user) {
    return list(user, null);
  }
  
  public int count(SqlSession sqlSession, User user, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
      logger.trace("count user: " + user + " " + sqlControl);
    }
    SqlQueryEngine sqlEngineUser = sqlEngineFactory.getCheckedQueryEngine("SELECT_USER");
    //sqlControl = getMoreResultClasses(user, sqlControl);
    int count = sqlEngineUser.queryCount(sqlSession, user, sqlControl);
    if (logger.isTraceEnabled()) {
      logger.trace("count: " + count);
    }
    return count;
  }
  
  public int count(User user, SqlControl sqlControl) {
  	return count(sqlSessionFactory.getSqlSession(), user, sqlControl);
  }
  
  public int count(SqlSession sqlSession, User user) {
    return count(sqlSession, user, null);
  }
  
  public int count(User user) {
    return count(user, null);
  }
}
