package org.toursys.repository.dao;

import java.util.List;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlControl;
import org.toursys.repository.model.Game;

public interface GameDao {
  
  public Game insert(SqlSession sqlSession, Game game, SqlControl sqlControl);
  
  public Game insert(Game game, SqlControl sqlControl);
  
  public Game insert(SqlSession sqlSession, Game game);
  
  public Game insert(Game game);
  
  public Game get(SqlSession sqlSession, Game game, SqlControl sqlControl);
  	
  public Game get(Game game, SqlControl sqlControl);
  	
  public Game get(SqlSession sqlSession, Game game);
  	
  public Game get(Game game);
  
  public int update(SqlSession sqlSession, Game game, SqlControl sqlControl);
  
  public int update(Game game, SqlControl sqlControl);
  
  public int update(SqlSession sqlSession, Game game);
  
  public int update(Game game);
  
  public int delete(SqlSession sqlSession, Game game, SqlControl sqlControl);
  
  public int delete(Game game, SqlControl sqlControl);
  
  public int delete(SqlSession sqlSession, Game game);
  
  public int delete(Game game);
  
  public List<Game> list(SqlSession sqlSession, Game game, SqlControl sqlControl);
  
  public List<Game> list(Game game, SqlControl sqlControl);
  
  public List<Game> list(SqlSession sqlSession, Game game);
  
  public List<Game> list(Game game);
  
  public int count(SqlSession sqlSession, Game game, SqlControl sqlControl);
  
  public int count(Game game, SqlControl sqlControl);
  
  public int count(SqlSession sqlSession, Game game);
  
  public int count(Game game);
}
