package org.toursys.repository.dao;

import java.util.List;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlControl;
import org.toursys.repository.model.PlayOffResult;

public interface PlayOffResultDao {
  
  public PlayOffResult insert(SqlSession sqlSession, PlayOffResult playOffResult, SqlControl sqlControl);
  
  public PlayOffResult insert(PlayOffResult playOffResult, SqlControl sqlControl);
  
  public PlayOffResult insert(SqlSession sqlSession, PlayOffResult playOffResult);
  
  public PlayOffResult insert(PlayOffResult playOffResult);
  
  public PlayOffResult get(SqlSession sqlSession, PlayOffResult playOffResult, SqlControl sqlControl);
  	
  public PlayOffResult get(PlayOffResult playOffResult, SqlControl sqlControl);
  	
  public PlayOffResult get(SqlSession sqlSession, PlayOffResult playOffResult);
  	
  public PlayOffResult get(PlayOffResult playOffResult);
  
  public int update(SqlSession sqlSession, PlayOffResult playOffResult, SqlControl sqlControl);
  
  public int update(PlayOffResult playOffResult, SqlControl sqlControl);
  
  public int update(SqlSession sqlSession, PlayOffResult playOffResult);
  
  public int update(PlayOffResult playOffResult);
  
  public int delete(SqlSession sqlSession, PlayOffResult playOffResult, SqlControl sqlControl);
  
  public int delete(PlayOffResult playOffResult, SqlControl sqlControl);
  
  public int delete(SqlSession sqlSession, PlayOffResult playOffResult);
  
  public int delete(PlayOffResult playOffResult);
  
  public List<PlayOffResult> list(SqlSession sqlSession, PlayOffResult playOffResult, SqlControl sqlControl);
  
  public List<PlayOffResult> list(PlayOffResult playOffResult, SqlControl sqlControl);
  
  public List<PlayOffResult> list(SqlSession sqlSession, PlayOffResult playOffResult);
  
  public List<PlayOffResult> list(PlayOffResult playOffResult);
  
  public int count(SqlSession sqlSession, PlayOffResult playOffResult, SqlControl sqlControl);
  
  public int count(PlayOffResult playOffResult, SqlControl sqlControl);
  
  public int count(SqlSession sqlSession, PlayOffResult playOffResult);
  
  public int count(PlayOffResult playOffResult);
}
