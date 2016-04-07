package org.tahom.repository.dao;

import java.util.List;
import org.sqlproc.engine.SqlControl;
import org.sqlproc.engine.SqlRowProcessor;
import org.sqlproc.engine.SqlSession;
import org.tahom.repository.model.IthfTournament;

@SuppressWarnings("all")
public interface IthfTournamentDao {
  public IthfTournament insert(final SqlSession sqlSession, final IthfTournament ithfTournament, SqlControl sqlControl);
  
  public IthfTournament insert(final IthfTournament ithfTournament, SqlControl sqlControl);
  
  public IthfTournament insert(final SqlSession sqlSession, final IthfTournament ithfTournament);
  
  public IthfTournament insert(final IthfTournament ithfTournament);
  
  public IthfTournament get(final SqlSession sqlSession, final IthfTournament ithfTournament, SqlControl sqlControl);
  
  public IthfTournament get(final IthfTournament ithfTournament, SqlControl sqlControl);
  
  public IthfTournament get(final SqlSession sqlSession, final IthfTournament ithfTournament);
  
  public IthfTournament get(final IthfTournament ithfTournament);
  
  public int update(final SqlSession sqlSession, final IthfTournament ithfTournament, SqlControl sqlControl);
  
  public int update(final IthfTournament ithfTournament, SqlControl sqlControl);
  
  public int update(final SqlSession sqlSession, final IthfTournament ithfTournament);
  
  public int update(final IthfTournament ithfTournament);
  
  public int delete(final SqlSession sqlSession, final IthfTournament ithfTournament, SqlControl sqlControl);
  
  public int delete(final IthfTournament ithfTournament, SqlControl sqlControl);
  
  public int delete(final SqlSession sqlSession, final IthfTournament ithfTournament);
  
  public int delete(final IthfTournament ithfTournament);
  
  public List<IthfTournament> list(final SqlSession sqlSession, final IthfTournament ithfTournament, SqlControl sqlControl);
  
  public List<IthfTournament> list(final IthfTournament ithfTournament, SqlControl sqlControl);
  
  public List<IthfTournament> list(final SqlSession sqlSession, final IthfTournament ithfTournament);
  
  public List<IthfTournament> list(final IthfTournament ithfTournament);
  
  public int query(final SqlSession sqlSession, final IthfTournament ithfTournament, SqlControl sqlControl, final SqlRowProcessor<IthfTournament> sqlRowProcessor);
  
  public int query(final IthfTournament ithfTournament, SqlControl sqlControl, final SqlRowProcessor<IthfTournament> sqlRowProcessor);
  
  public int query(final SqlSession sqlSession, final IthfTournament ithfTournament, final SqlRowProcessor<IthfTournament> sqlRowProcessor);
  
  public int query(final IthfTournament ithfTournament, final SqlRowProcessor<IthfTournament> sqlRowProcessor);
  
  public List<IthfTournament> listFromTo(final SqlSession sqlSession, final IthfTournament ithfTournament, SqlControl sqlControl);
  
  public List<IthfTournament> listFromTo(final IthfTournament ithfTournament, SqlControl sqlControl);
  
  public List<IthfTournament> listFromTo(final SqlSession sqlSession, final IthfTournament ithfTournament);
  
  public List<IthfTournament> listFromTo(final IthfTournament ithfTournament);
  
  public int count(final SqlSession sqlSession, final IthfTournament ithfTournament, SqlControl sqlControl);
  
  public int count(final IthfTournament ithfTournament, SqlControl sqlControl);
  
  public int count(final SqlSession sqlSession, final IthfTournament ithfTournament);
  
  public int count(final IthfTournament ithfTournament);
}
