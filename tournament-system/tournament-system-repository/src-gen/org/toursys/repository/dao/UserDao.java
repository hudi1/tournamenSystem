package org.toursys.repository.dao;

import java.util.List;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlControl;
import org.toursys.repository.model.User;

public interface UserDao {
  
  public User insert(SqlSession sqlSession, User user, SqlControl sqlControl);
  
  public User insert(User user, SqlControl sqlControl);
  
  public User insert(SqlSession sqlSession, User user);
  
  public User insert(User user);
  
  public User get(SqlSession sqlSession, User user, SqlControl sqlControl);
  	
  public User get(User user, SqlControl sqlControl);
  	
  public User get(SqlSession sqlSession, User user);
  	
  public User get(User user);
  
  public int update(SqlSession sqlSession, User user, SqlControl sqlControl);
  
  public int update(User user, SqlControl sqlControl);
  
  public int update(SqlSession sqlSession, User user);
  
  public int update(User user);
  
  public int delete(SqlSession sqlSession, User user, SqlControl sqlControl);
  
  public int delete(User user, SqlControl sqlControl);
  
  public int delete(SqlSession sqlSession, User user);
  
  public int delete(User user);
  
  public List<User> list(SqlSession sqlSession, User user, SqlControl sqlControl);
  
  public List<User> list(User user, SqlControl sqlControl);
  
  public List<User> list(SqlSession sqlSession, User user);
  
  public List<User> list(User user);
  
  public int count(SqlSession sqlSession, User user, SqlControl sqlControl);
  
  public int count(User user, SqlControl sqlControl);
  
  public int count(SqlSession sqlSession, User user);
  
  public int count(User user);
}
