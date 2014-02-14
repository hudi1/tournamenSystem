package org.toursys.repository.dao;

import java.util.List;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlControl;
import org.toursys.repository.model.Participant;

public interface ParticipantDao {
  
  public Participant insert(SqlSession sqlSession, Participant participant, SqlControl sqlControl);
  public Participant insert(Participant participant, SqlControl sqlControl);
  public Participant insert(SqlSession sqlSession, Participant participant);
  public Participant insert(Participant participant);
  
  public Participant get(SqlSession sqlSession, Participant participant, SqlControl sqlControl);
  public Participant get(Participant participant, SqlControl sqlControl);
  	    public Participant get(SqlSession sqlSession, Participant participant);
  public Participant get(Participant participant);
  
  public int update(SqlSession sqlSession, Participant participant, SqlControl sqlControl);
  public int update(Participant participant, SqlControl sqlControl);
  public int update(SqlSession sqlSession, Participant participant);
  public int update(Participant participant);
  
  public int delete(SqlSession sqlSession, Participant participant, SqlControl sqlControl);
  public int delete(Participant participant, SqlControl sqlControl);
  public int delete(SqlSession sqlSession, Participant participant);
  public int delete(Participant participant);
  
  public List<Participant> list(SqlSession sqlSession, Participant participant, SqlControl sqlControl);
  public List<Participant> list(Participant participant, SqlControl sqlControl);
  public List<Participant> list(SqlSession sqlSession, Participant participant);
  public List<Participant> list(Participant participant);
  
  public int count(SqlSession sqlSession, Participant participant, SqlControl sqlControl);
  public int count(Participant participant, SqlControl sqlControl);
  public int count(SqlSession sqlSession, Participant participant);
  public int count(Participant participant);
}
