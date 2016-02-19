package org.tahom.repository.dao;

import java.util.List;
import org.sqlproc.engine.SqlControl;
import org.sqlproc.engine.SqlRowProcessor;
import org.sqlproc.engine.SqlSession;
import org.tahom.repository.model.User;

@SuppressWarnings("all")
public interface UserDao {
  public User insert(final SqlSession sqlSession, final User user, SqlControl sqlControl);
  
  public User insert(final User user, SqlControl sqlControl);
  
  public User insert(final SqlSession sqlSession, final User user);
  
  public User insert(final User user);
  
  public User get(final SqlSession sqlSession, final User user, SqlControl sqlControl);
  
  public User get(final User user, SqlControl sqlControl);
  
  public User get(final SqlSession sqlSession, final User user);
  
  public User get(final User user);
  
  public int update(final SqlSession sqlSession, final User user, SqlControl sqlControl);
  
  public int update(final User user, SqlControl sqlControl);
  
  public int update(final SqlSession sqlSession, final User user);
  
  public int update(final User user);
  
  public int delete(final SqlSession sqlSession, final User user, SqlControl sqlControl);
  
  public int delete(final User user, SqlControl sqlControl);
  
  public int delete(final SqlSession sqlSession, final User user);
  
  public int delete(final User user);
  
  public List<User> list(final SqlSession sqlSession, final User user, SqlControl sqlControl);
  
  public List<User> list(final User user, SqlControl sqlControl);
  
  public List<User> list(final SqlSession sqlSession, final User user);
  
  public List<User> list(final User user);
  
  public int query(final SqlSession sqlSession, final User user, SqlControl sqlControl, final SqlRowProcessor<User> sqlRowProcessor);
  
  public int query(final User user, SqlControl sqlControl, final SqlRowProcessor<User> sqlRowProcessor);
  
  public int query(final SqlSession sqlSession, final User user, final SqlRowProcessor<User> sqlRowProcessor);
  
  public int query(final User user, final SqlRowProcessor<User> sqlRowProcessor);
  
  public List<User> listFromTo(final SqlSession sqlSession, final User user, SqlControl sqlControl);
  
  public List<User> listFromTo(final User user, SqlControl sqlControl);
  
  public List<User> listFromTo(final SqlSession sqlSession, final User user);
  
  public List<User> listFromTo(final User user);
  
  public int count(final SqlSession sqlSession, final User user, SqlControl sqlControl);
  
  public int count(final User user, SqlControl sqlControl);
  
  public int count(final SqlSession sqlSession, final User user);
  
  public int count(final User user);
}
