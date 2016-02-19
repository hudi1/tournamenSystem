package org.tahom.repository.dao;

import java.util.List;
import org.sqlproc.engine.SqlControl;
import org.sqlproc.engine.SqlRowProcessor;
import org.sqlproc.engine.SqlSession;
import org.tahom.repository.model.FinalStanding;

@SuppressWarnings("all")
public interface FinalStandingDao {
  public FinalStanding insert(final SqlSession sqlSession, final FinalStanding finalStanding, SqlControl sqlControl);
  
  public FinalStanding insert(final FinalStanding finalStanding, SqlControl sqlControl);
  
  public FinalStanding insert(final SqlSession sqlSession, final FinalStanding finalStanding);
  
  public FinalStanding insert(final FinalStanding finalStanding);
  
  public FinalStanding get(final SqlSession sqlSession, final FinalStanding finalStanding, SqlControl sqlControl);
  
  public FinalStanding get(final FinalStanding finalStanding, SqlControl sqlControl);
  
  public FinalStanding get(final SqlSession sqlSession, final FinalStanding finalStanding);
  
  public FinalStanding get(final FinalStanding finalStanding);
  
  public int update(final SqlSession sqlSession, final FinalStanding finalStanding, SqlControl sqlControl);
  
  public int update(final FinalStanding finalStanding, SqlControl sqlControl);
  
  public int update(final SqlSession sqlSession, final FinalStanding finalStanding);
  
  public int update(final FinalStanding finalStanding);
  
  public int delete(final SqlSession sqlSession, final FinalStanding finalStanding, SqlControl sqlControl);
  
  public int delete(final FinalStanding finalStanding, SqlControl sqlControl);
  
  public int delete(final SqlSession sqlSession, final FinalStanding finalStanding);
  
  public int delete(final FinalStanding finalStanding);
  
  public List<FinalStanding> list(final SqlSession sqlSession, final FinalStanding finalStanding, SqlControl sqlControl);
  
  public List<FinalStanding> list(final FinalStanding finalStanding, SqlControl sqlControl);
  
  public List<FinalStanding> list(final SqlSession sqlSession, final FinalStanding finalStanding);
  
  public List<FinalStanding> list(final FinalStanding finalStanding);
  
  public int query(final SqlSession sqlSession, final FinalStanding finalStanding, SqlControl sqlControl, final SqlRowProcessor<FinalStanding> sqlRowProcessor);
  
  public int query(final FinalStanding finalStanding, SqlControl sqlControl, final SqlRowProcessor<FinalStanding> sqlRowProcessor);
  
  public int query(final SqlSession sqlSession, final FinalStanding finalStanding, final SqlRowProcessor<FinalStanding> sqlRowProcessor);
  
  public int query(final FinalStanding finalStanding, final SqlRowProcessor<FinalStanding> sqlRowProcessor);
  
  public List<FinalStanding> listFromTo(final SqlSession sqlSession, final FinalStanding finalStanding, SqlControl sqlControl);
  
  public List<FinalStanding> listFromTo(final FinalStanding finalStanding, SqlControl sqlControl);
  
  public List<FinalStanding> listFromTo(final SqlSession sqlSession, final FinalStanding finalStanding);
  
  public List<FinalStanding> listFromTo(final FinalStanding finalStanding);
  
  public int count(final SqlSession sqlSession, final FinalStanding finalStanding, SqlControl sqlControl);
  
  public int count(final FinalStanding finalStanding, SqlControl sqlControl);
  
  public int count(final SqlSession sqlSession, final FinalStanding finalStanding);
  
  public int count(final FinalStanding finalStanding);
}
