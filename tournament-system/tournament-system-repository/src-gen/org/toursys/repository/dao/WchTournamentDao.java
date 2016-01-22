package org.toursys.repository.dao;

import java.util.List;
import org.sqlproc.engine.SqlControl;
import org.sqlproc.engine.SqlRowProcessor;
import org.sqlproc.engine.SqlSession;
import org.toursys.repository.model.WchTournament;

@SuppressWarnings("all")
public interface WchTournamentDao {
  public WchTournament insert(final SqlSession sqlSession, final WchTournament wchTournament, SqlControl sqlControl);
  
  public WchTournament insert(final WchTournament wchTournament, SqlControl sqlControl);
  
  public WchTournament insert(final SqlSession sqlSession, final WchTournament wchTournament);
  
  public WchTournament insert(final WchTournament wchTournament);
  
  public WchTournament get(final SqlSession sqlSession, final WchTournament wchTournament, SqlControl sqlControl);
  
  public WchTournament get(final WchTournament wchTournament, SqlControl sqlControl);
  
  public WchTournament get(final SqlSession sqlSession, final WchTournament wchTournament);
  
  public WchTournament get(final WchTournament wchTournament);
  
  public int update(final SqlSession sqlSession, final WchTournament wchTournament, SqlControl sqlControl);
  
  public int update(final WchTournament wchTournament, SqlControl sqlControl);
  
  public int update(final SqlSession sqlSession, final WchTournament wchTournament);
  
  public int update(final WchTournament wchTournament);
  
  public int delete(final SqlSession sqlSession, final WchTournament wchTournament, SqlControl sqlControl);
  
  public int delete(final WchTournament wchTournament, SqlControl sqlControl);
  
  public int delete(final SqlSession sqlSession, final WchTournament wchTournament);
  
  public int delete(final WchTournament wchTournament);
  
  public List<WchTournament> list(final SqlSession sqlSession, final WchTournament wchTournament, SqlControl sqlControl);
  
  public List<WchTournament> list(final WchTournament wchTournament, SqlControl sqlControl);
  
  public List<WchTournament> list(final SqlSession sqlSession, final WchTournament wchTournament);
  
  public List<WchTournament> list(final WchTournament wchTournament);
  
  public int query(final SqlSession sqlSession, final WchTournament wchTournament, SqlControl sqlControl, final SqlRowProcessor<WchTournament> sqlRowProcessor);
  
  public int query(final WchTournament wchTournament, SqlControl sqlControl, final SqlRowProcessor<WchTournament> sqlRowProcessor);
  
  public int query(final SqlSession sqlSession, final WchTournament wchTournament, final SqlRowProcessor<WchTournament> sqlRowProcessor);
  
  public int query(final WchTournament wchTournament, final SqlRowProcessor<WchTournament> sqlRowProcessor);
  
  public List<WchTournament> listFromTo(final SqlSession sqlSession, final WchTournament wchTournament, SqlControl sqlControl);
  
  public List<WchTournament> listFromTo(final WchTournament wchTournament, SqlControl sqlControl);
  
  public List<WchTournament> listFromTo(final SqlSession sqlSession, final WchTournament wchTournament);
  
  public List<WchTournament> listFromTo(final WchTournament wchTournament);
  
  public int count(final SqlSession sqlSession, final WchTournament wchTournament, SqlControl sqlControl);
  
  public int count(final WchTournament wchTournament, SqlControl sqlControl);
  
  public int count(final SqlSession sqlSession, final WchTournament wchTournament);
  
  public int count(final WchTournament wchTournament);
}
