package org.tahom.repository.dao;

import java.util.List;
import org.sqlproc.engine.SqlControl;
import org.sqlproc.engine.SqlRowProcessor;
import org.sqlproc.engine.SqlSession;
import org.tahom.repository.model.Groups;

@SuppressWarnings("all")
public interface GroupsDao {
  public Groups insert(final SqlSession sqlSession, final Groups groups, SqlControl sqlControl);
  
  public Groups insert(final Groups groups, SqlControl sqlControl);
  
  public Groups insert(final SqlSession sqlSession, final Groups groups);
  
  public Groups insert(final Groups groups);
  
  public Groups get(final SqlSession sqlSession, final Groups groups, SqlControl sqlControl);
  
  public Groups get(final Groups groups, SqlControl sqlControl);
  
  public Groups get(final SqlSession sqlSession, final Groups groups);
  
  public Groups get(final Groups groups);
  
  public int update(final SqlSession sqlSession, final Groups groups, SqlControl sqlControl);
  
  public int update(final Groups groups, SqlControl sqlControl);
  
  public int update(final SqlSession sqlSession, final Groups groups);
  
  public int update(final Groups groups);
  
  public int delete(final SqlSession sqlSession, final Groups groups, SqlControl sqlControl);
  
  public int delete(final Groups groups, SqlControl sqlControl);
  
  public int delete(final SqlSession sqlSession, final Groups groups);
  
  public int delete(final Groups groups);
  
  public List<Groups> list(final SqlSession sqlSession, final Groups groups, SqlControl sqlControl);
  
  public List<Groups> list(final Groups groups, SqlControl sqlControl);
  
  public List<Groups> list(final SqlSession sqlSession, final Groups groups);
  
  public List<Groups> list(final Groups groups);
  
  public int query(final SqlSession sqlSession, final Groups groups, SqlControl sqlControl, final SqlRowProcessor<Groups> sqlRowProcessor);
  
  public int query(final Groups groups, SqlControl sqlControl, final SqlRowProcessor<Groups> sqlRowProcessor);
  
  public int query(final SqlSession sqlSession, final Groups groups, final SqlRowProcessor<Groups> sqlRowProcessor);
  
  public int query(final Groups groups, final SqlRowProcessor<Groups> sqlRowProcessor);
  
  public List<Groups> listFromTo(final SqlSession sqlSession, final Groups groups, SqlControl sqlControl);
  
  public List<Groups> listFromTo(final Groups groups, SqlControl sqlControl);
  
  public List<Groups> listFromTo(final SqlSession sqlSession, final Groups groups);
  
  public List<Groups> listFromTo(final Groups groups);
  
  public int count(final SqlSession sqlSession, final Groups groups, SqlControl sqlControl);
  
  public int count(final Groups groups, SqlControl sqlControl);
  
  public int count(final SqlSession sqlSession, final Groups groups);
  
  public int count(final Groups groups);
}
