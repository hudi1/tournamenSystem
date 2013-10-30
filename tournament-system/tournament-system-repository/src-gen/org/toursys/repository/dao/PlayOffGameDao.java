package org.toursys.repository.dao;

import java.util.List;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlControl;
import org.toursys.repository.model.PlayOffGame;

public interface PlayOffGameDao {
  
  public PlayOffGame insert(SqlSession sqlSession, PlayOffGame playOffGame, SqlControl sqlControl);
  
  public PlayOffGame insert(PlayOffGame playOffGame, SqlControl sqlControl);
  
  public PlayOffGame insert(SqlSession sqlSession, PlayOffGame playOffGame);
  
  public PlayOffGame insert(PlayOffGame playOffGame);
  
  public PlayOffGame get(SqlSession sqlSession, PlayOffGame playOffGame, SqlControl sqlControl);
  	
  public PlayOffGame get(PlayOffGame playOffGame, SqlControl sqlControl);
  	
  public PlayOffGame get(SqlSession sqlSession, PlayOffGame playOffGame);
  	
  public PlayOffGame get(PlayOffGame playOffGame);
  
  public int update(SqlSession sqlSession, PlayOffGame playOffGame, SqlControl sqlControl);
  
  public int update(PlayOffGame playOffGame, SqlControl sqlControl);
  
  public int update(SqlSession sqlSession, PlayOffGame playOffGame);
  
  public int update(PlayOffGame playOffGame);
  
  public int delete(SqlSession sqlSession, PlayOffGame playOffGame, SqlControl sqlControl);
  
  public int delete(PlayOffGame playOffGame, SqlControl sqlControl);
  
  public int delete(SqlSession sqlSession, PlayOffGame playOffGame);
  
  public int delete(PlayOffGame playOffGame);
  
  public List<PlayOffGame> list(SqlSession sqlSession, PlayOffGame playOffGame, SqlControl sqlControl);
  
  public List<PlayOffGame> list(PlayOffGame playOffGame, SqlControl sqlControl);
  
  public List<PlayOffGame> list(SqlSession sqlSession, PlayOffGame playOffGame);
  
  public List<PlayOffGame> list(PlayOffGame playOffGame);
  
  public int count(SqlSession sqlSession, PlayOffGame playOffGame, SqlControl sqlControl);
  
  public int count(PlayOffGame playOffGame, SqlControl sqlControl);
  
  public int count(SqlSession sqlSession, PlayOffGame playOffGame);
  
  public int count(PlayOffGame playOffGame);
}
