package org.tahom.repository.dao;

import java.util.List;
import org.sqlproc.engine.SqlControl;
import org.sqlproc.engine.SqlRowProcessor;
import org.sqlproc.engine.SqlSession;
import org.tahom.repository.model.Tournament;

@SuppressWarnings("all")
public interface TournamentDao {
  public Tournament insert(final SqlSession sqlSession, final Tournament tournament, SqlControl sqlControl);
  
  public Tournament insert(final Tournament tournament, SqlControl sqlControl);
  
  public Tournament insert(final SqlSession sqlSession, final Tournament tournament);
  
  public Tournament insert(final Tournament tournament);
  
  public Tournament get(final SqlSession sqlSession, final Tournament tournament, SqlControl sqlControl);
  
  public Tournament get(final Tournament tournament, SqlControl sqlControl);
  
  public Tournament get(final SqlSession sqlSession, final Tournament tournament);
  
  public Tournament get(final Tournament tournament);
  
  public int update(final SqlSession sqlSession, final Tournament tournament, SqlControl sqlControl);
  
  public int update(final Tournament tournament, SqlControl sqlControl);
  
  public int update(final SqlSession sqlSession, final Tournament tournament);
  
  public int update(final Tournament tournament);
  
  public int delete(final SqlSession sqlSession, final Tournament tournament, SqlControl sqlControl);
  
  public int delete(final Tournament tournament, SqlControl sqlControl);
  
  public int delete(final SqlSession sqlSession, final Tournament tournament);
  
  public int delete(final Tournament tournament);
  
  public List<Tournament> list(final SqlSession sqlSession, final Tournament tournament, SqlControl sqlControl);
  
  public List<Tournament> list(final Tournament tournament, SqlControl sqlControl);
  
  public List<Tournament> list(final SqlSession sqlSession, final Tournament tournament);
  
  public List<Tournament> list(final Tournament tournament);
  
  public int query(final SqlSession sqlSession, final Tournament tournament, SqlControl sqlControl, final SqlRowProcessor<Tournament> sqlRowProcessor);
  
  public int query(final Tournament tournament, SqlControl sqlControl, final SqlRowProcessor<Tournament> sqlRowProcessor);
  
  public int query(final SqlSession sqlSession, final Tournament tournament, final SqlRowProcessor<Tournament> sqlRowProcessor);
  
  public int query(final Tournament tournament, final SqlRowProcessor<Tournament> sqlRowProcessor);
  
  public List<Tournament> listFromTo(final SqlSession sqlSession, final Tournament tournament, SqlControl sqlControl);
  
  public List<Tournament> listFromTo(final Tournament tournament, SqlControl sqlControl);
  
  public List<Tournament> listFromTo(final SqlSession sqlSession, final Tournament tournament);
  
  public List<Tournament> listFromTo(final Tournament tournament);
  
  public int count(final SqlSession sqlSession, final Tournament tournament, SqlControl sqlControl);
  
  public int count(final Tournament tournament, SqlControl sqlControl);
  
  public int count(final SqlSession sqlSession, final Tournament tournament);
  
  public int count(final Tournament tournament);
}
