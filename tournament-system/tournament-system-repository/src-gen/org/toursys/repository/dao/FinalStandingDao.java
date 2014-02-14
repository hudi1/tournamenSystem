package org.toursys.repository.dao;

import java.util.List;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlControl;
import org.toursys.repository.model.FinalStanding;

public interface FinalStandingDao {
  
  public FinalStanding insert(SqlSession sqlSession, FinalStanding finalStanding, SqlControl sqlControl);
  public FinalStanding insert(FinalStanding finalStanding, SqlControl sqlControl);
  public FinalStanding insert(SqlSession sqlSession, FinalStanding finalStanding);
  public FinalStanding insert(FinalStanding finalStanding);
  
  public FinalStanding get(SqlSession sqlSession, FinalStanding finalStanding, SqlControl sqlControl);
  public FinalStanding get(FinalStanding finalStanding, SqlControl sqlControl);
  	    public FinalStanding get(SqlSession sqlSession, FinalStanding finalStanding);
  public FinalStanding get(FinalStanding finalStanding);
  
  public int update(SqlSession sqlSession, FinalStanding finalStanding, SqlControl sqlControl);
  public int update(FinalStanding finalStanding, SqlControl sqlControl);
  public int update(SqlSession sqlSession, FinalStanding finalStanding);
  public int update(FinalStanding finalStanding);
  
  public int delete(SqlSession sqlSession, FinalStanding finalStanding, SqlControl sqlControl);
  public int delete(FinalStanding finalStanding, SqlControl sqlControl);
  public int delete(SqlSession sqlSession, FinalStanding finalStanding);
  public int delete(FinalStanding finalStanding);
  
  public List<FinalStanding> list(SqlSession sqlSession, FinalStanding finalStanding, SqlControl sqlControl);
  public List<FinalStanding> list(FinalStanding finalStanding, SqlControl sqlControl);
  public List<FinalStanding> list(SqlSession sqlSession, FinalStanding finalStanding);
  public List<FinalStanding> list(FinalStanding finalStanding);
  
  public int count(SqlSession sqlSession, FinalStanding finalStanding, SqlControl sqlControl);
  public int count(FinalStanding finalStanding, SqlControl sqlControl);
  public int count(SqlSession sqlSession, FinalStanding finalStanding);
  public int count(FinalStanding finalStanding);
}
