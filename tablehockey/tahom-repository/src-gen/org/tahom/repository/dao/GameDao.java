package org.tahom.repository.dao;

import java.util.List;
import org.sqlproc.engine.SqlControl;
import org.sqlproc.engine.SqlRowProcessor;
import org.sqlproc.engine.SqlSession;
import org.tahom.repository.model.Game;

@SuppressWarnings("all")
public interface GameDao {
  public Game insert(final SqlSession sqlSession, final Game game, SqlControl sqlControl);
  
  public Game insert(final Game game, SqlControl sqlControl);
  
  public Game insert(final SqlSession sqlSession, final Game game);
  
  public Game insert(final Game game);
  
  public Game get(final SqlSession sqlSession, final Game game, SqlControl sqlControl);
  
  public Game get(final Game game, SqlControl sqlControl);
  
  public Game get(final SqlSession sqlSession, final Game game);
  
  public Game get(final Game game);
  
  public int update(final SqlSession sqlSession, final Game game, SqlControl sqlControl);
  
  public int update(final Game game, SqlControl sqlControl);
  
  public int update(final SqlSession sqlSession, final Game game);
  
  public int update(final Game game);
  
  public int delete(final SqlSession sqlSession, final Game game, SqlControl sqlControl);
  
  public int delete(final Game game, SqlControl sqlControl);
  
  public int delete(final SqlSession sqlSession, final Game game);
  
  public int delete(final Game game);
  
  public List<Game> list(final SqlSession sqlSession, final Game game, SqlControl sqlControl);
  
  public List<Game> list(final Game game, SqlControl sqlControl);
  
  public List<Game> list(final SqlSession sqlSession, final Game game);
  
  public List<Game> list(final Game game);
  
  public int query(final SqlSession sqlSession, final Game game, SqlControl sqlControl, final SqlRowProcessor<Game> sqlRowProcessor);
  
  public int query(final Game game, SqlControl sqlControl, final SqlRowProcessor<Game> sqlRowProcessor);
  
  public int query(final SqlSession sqlSession, final Game game, final SqlRowProcessor<Game> sqlRowProcessor);
  
  public int query(final Game game, final SqlRowProcessor<Game> sqlRowProcessor);
  
  public List<Game> listFromTo(final SqlSession sqlSession, final Game game, SqlControl sqlControl);
  
  public List<Game> listFromTo(final Game game, SqlControl sqlControl);
  
  public List<Game> listFromTo(final SqlSession sqlSession, final Game game);
  
  public List<Game> listFromTo(final Game game);
  
  public int count(final SqlSession sqlSession, final Game game, SqlControl sqlControl);
  
  public int count(final Game game, SqlControl sqlControl);
  
  public int count(final SqlSession sqlSession, final Game game);
  
  public int count(final Game game);
}
