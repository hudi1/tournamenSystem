package org.tahom.repository.dao;

import java.util.List;
import org.sqlproc.engine.SqlControl;
import org.sqlproc.engine.SqlRowProcessor;
import org.sqlproc.engine.SqlSession;
import org.tahom.repository.model.Participant;

@SuppressWarnings("all")
public interface ParticipantDao {
  public Participant insert(final SqlSession sqlSession, final Participant participant, SqlControl sqlControl);
  
  public Participant insert(final Participant participant, SqlControl sqlControl);
  
  public Participant insert(final SqlSession sqlSession, final Participant participant);
  
  public Participant insert(final Participant participant);
  
  public Participant get(final SqlSession sqlSession, final Participant participant, SqlControl sqlControl);
  
  public Participant get(final Participant participant, SqlControl sqlControl);
  
  public Participant get(final SqlSession sqlSession, final Participant participant);
  
  public Participant get(final Participant participant);
  
  public int update(final SqlSession sqlSession, final Participant participant, SqlControl sqlControl);
  
  public int update(final Participant participant, SqlControl sqlControl);
  
  public int update(final SqlSession sqlSession, final Participant participant);
  
  public int update(final Participant participant);
  
  public int delete(final SqlSession sqlSession, final Participant participant, SqlControl sqlControl);
  
  public int delete(final Participant participant, SqlControl sqlControl);
  
  public int delete(final SqlSession sqlSession, final Participant participant);
  
  public int delete(final Participant participant);
  
  public List<Participant> list(final SqlSession sqlSession, final Participant participant, SqlControl sqlControl);
  
  public List<Participant> list(final Participant participant, SqlControl sqlControl);
  
  public List<Participant> list(final SqlSession sqlSession, final Participant participant);
  
  public List<Participant> list(final Participant participant);
  
  public int query(final SqlSession sqlSession, final Participant participant, SqlControl sqlControl, final SqlRowProcessor<Participant> sqlRowProcessor);
  
  public int query(final Participant participant, SqlControl sqlControl, final SqlRowProcessor<Participant> sqlRowProcessor);
  
  public int query(final SqlSession sqlSession, final Participant participant, final SqlRowProcessor<Participant> sqlRowProcessor);
  
  public int query(final Participant participant, final SqlRowProcessor<Participant> sqlRowProcessor);
  
  public List<Participant> listFromTo(final SqlSession sqlSession, final Participant participant, SqlControl sqlControl);
  
  public List<Participant> listFromTo(final Participant participant, SqlControl sqlControl);
  
  public List<Participant> listFromTo(final SqlSession sqlSession, final Participant participant);
  
  public List<Participant> listFromTo(final Participant participant);
  
  public int count(final SqlSession sqlSession, final Participant participant, SqlControl sqlControl);
  
  public int count(final Participant participant, SqlControl sqlControl);
  
  public int count(final SqlSession sqlSession, final Participant participant);
  
  public int count(final Participant participant);
}
