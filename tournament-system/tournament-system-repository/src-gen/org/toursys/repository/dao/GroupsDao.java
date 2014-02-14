package org.toursys.repository.dao;

import java.util.List;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlControl;
import org.toursys.repository.model.Groups;

public interface GroupsDao {
  
  public Groups insert(SqlSession sqlSession, Groups groups, SqlControl sqlControl);
  public Groups insert(Groups groups, SqlControl sqlControl);
  public Groups insert(SqlSession sqlSession, Groups groups);
  public Groups insert(Groups groups);
  
  public Groups get(SqlSession sqlSession, Groups groups, SqlControl sqlControl);
  public Groups get(Groups groups, SqlControl sqlControl);
  	    public Groups get(SqlSession sqlSession, Groups groups);
  public Groups get(Groups groups);
  
  public int update(SqlSession sqlSession, Groups groups, SqlControl sqlControl);
  public int update(Groups groups, SqlControl sqlControl);
  public int update(SqlSession sqlSession, Groups groups);
  public int update(Groups groups);
  
  public int delete(SqlSession sqlSession, Groups groups, SqlControl sqlControl);
  public int delete(Groups groups, SqlControl sqlControl);
  public int delete(SqlSession sqlSession, Groups groups);
  public int delete(Groups groups);
  
  public List<Groups> list(SqlSession sqlSession, Groups groups, SqlControl sqlControl);
  public List<Groups> list(Groups groups, SqlControl sqlControl);
  public List<Groups> list(SqlSession sqlSession, Groups groups);
  public List<Groups> list(Groups groups);
  
  public int count(SqlSession sqlSession, Groups groups, SqlControl sqlControl);
  public int count(Groups groups, SqlControl sqlControl);
  public int count(SqlSession sqlSession, Groups groups);
  public int count(Groups groups);
}
