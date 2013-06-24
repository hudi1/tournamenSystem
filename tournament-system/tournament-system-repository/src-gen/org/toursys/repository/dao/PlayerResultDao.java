package org.toursys.repository.dao;

import java.util.List;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlControl;
import org.toursys.repository.model.PlayerResult;

public interface PlayerResultDao {
  
  public PlayerResult insert(SqlSession sqlSession, PlayerResult playerResult, SqlControl sqlControl);
  
  public PlayerResult insert(PlayerResult playerResult, SqlControl sqlControl);
  
  public PlayerResult insert(SqlSession sqlSession, PlayerResult playerResult);
  
  public PlayerResult insert(PlayerResult playerResult);
  
  public PlayerResult get(SqlSession sqlSession, PlayerResult playerResult, SqlControl sqlControl);
  	
  public PlayerResult get(PlayerResult playerResult, SqlControl sqlControl);
  	
  public PlayerResult get(SqlSession sqlSession, PlayerResult playerResult);
  	
  public PlayerResult get(PlayerResult playerResult);
  
  public int update(SqlSession sqlSession, PlayerResult playerResult, SqlControl sqlControl);
  
  public int update(PlayerResult playerResult, SqlControl sqlControl);
  
  public int update(SqlSession sqlSession, PlayerResult playerResult);
  
  public int update(PlayerResult playerResult);
  
  public int delete(SqlSession sqlSession, PlayerResult playerResult, SqlControl sqlControl);
  
  public int delete(PlayerResult playerResult, SqlControl sqlControl);
  
  public int delete(SqlSession sqlSession, PlayerResult playerResult);
  
  public int delete(PlayerResult playerResult);
  
  public List<PlayerResult> list(SqlSession sqlSession, PlayerResult playerResult, SqlControl sqlControl);
  
  public List<PlayerResult> list(PlayerResult playerResult, SqlControl sqlControl);
  
  public List<PlayerResult> list(SqlSession sqlSession, PlayerResult playerResult);
  
  public List<PlayerResult> list(PlayerResult playerResult);
  
  public int count(SqlSession sqlSession, PlayerResult playerResult, SqlControl sqlControl);
  
  public int count(PlayerResult playerResult, SqlControl sqlControl);
  
  public int count(SqlSession sqlSession, PlayerResult playerResult);
  
  public int count(PlayerResult playerResult);
}
