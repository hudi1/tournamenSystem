package org.tahom.repository.dao;

import java.util.List;
import org.sqlproc.engine.SqlControl;
import org.sqlproc.engine.SqlRowProcessor;
import org.sqlproc.engine.SqlSession;
import org.tahom.repository.model.PlayOffGame;

@SuppressWarnings("all")
public interface PlayOffGameDao {
  public PlayOffGame insert(final SqlSession sqlSession, final PlayOffGame playOffGame, SqlControl sqlControl);
  
  public PlayOffGame insert(final PlayOffGame playOffGame, SqlControl sqlControl);
  
  public PlayOffGame insert(final SqlSession sqlSession, final PlayOffGame playOffGame);
  
  public PlayOffGame insert(final PlayOffGame playOffGame);
  
  public PlayOffGame get(final SqlSession sqlSession, final PlayOffGame playOffGame, SqlControl sqlControl);
  
  public PlayOffGame get(final PlayOffGame playOffGame, SqlControl sqlControl);
  
  public PlayOffGame get(final SqlSession sqlSession, final PlayOffGame playOffGame);
  
  public PlayOffGame get(final PlayOffGame playOffGame);
  
  public int update(final SqlSession sqlSession, final PlayOffGame playOffGame, SqlControl sqlControl);
  
  public int update(final PlayOffGame playOffGame, SqlControl sqlControl);
  
  public int update(final SqlSession sqlSession, final PlayOffGame playOffGame);
  
  public int update(final PlayOffGame playOffGame);
  
  public int delete(final SqlSession sqlSession, final PlayOffGame playOffGame, SqlControl sqlControl);
  
  public int delete(final PlayOffGame playOffGame, SqlControl sqlControl);
  
  public int delete(final SqlSession sqlSession, final PlayOffGame playOffGame);
  
  public int delete(final PlayOffGame playOffGame);
  
  public List<PlayOffGame> list(final SqlSession sqlSession, final PlayOffGame playOffGame, SqlControl sqlControl);
  
  public List<PlayOffGame> list(final PlayOffGame playOffGame, SqlControl sqlControl);
  
  public List<PlayOffGame> list(final SqlSession sqlSession, final PlayOffGame playOffGame);
  
  public List<PlayOffGame> list(final PlayOffGame playOffGame);
  
  public int query(final SqlSession sqlSession, final PlayOffGame playOffGame, SqlControl sqlControl, final SqlRowProcessor<PlayOffGame> sqlRowProcessor);
  
  public int query(final PlayOffGame playOffGame, SqlControl sqlControl, final SqlRowProcessor<PlayOffGame> sqlRowProcessor);
  
  public int query(final SqlSession sqlSession, final PlayOffGame playOffGame, final SqlRowProcessor<PlayOffGame> sqlRowProcessor);
  
  public int query(final PlayOffGame playOffGame, final SqlRowProcessor<PlayOffGame> sqlRowProcessor);
  
  public List<PlayOffGame> listFromTo(final SqlSession sqlSession, final PlayOffGame playOffGame, SqlControl sqlControl);
  
  public List<PlayOffGame> listFromTo(final PlayOffGame playOffGame, SqlControl sqlControl);
  
  public List<PlayOffGame> listFromTo(final SqlSession sqlSession, final PlayOffGame playOffGame);
  
  public List<PlayOffGame> listFromTo(final PlayOffGame playOffGame);
  
  public int count(final SqlSession sqlSession, final PlayOffGame playOffGame, SqlControl sqlControl);
  
  public int count(final PlayOffGame playOffGame, SqlControl sqlControl);
  
  public int count(final SqlSession sqlSession, final PlayOffGame playOffGame);
  
  public int count(final PlayOffGame playOffGame);
}
