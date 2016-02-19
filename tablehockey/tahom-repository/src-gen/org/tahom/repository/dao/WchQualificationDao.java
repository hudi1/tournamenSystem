package org.tahom.repository.dao;

import java.util.List;
import org.sqlproc.engine.SqlControl;
import org.sqlproc.engine.SqlRowProcessor;
import org.sqlproc.engine.SqlSession;
import org.tahom.repository.model.WchQualification;

@SuppressWarnings("all")
public interface WchQualificationDao {
  public WchQualification insert(final SqlSession sqlSession, final WchQualification wchQualification, SqlControl sqlControl);
  
  public WchQualification insert(final WchQualification wchQualification, SqlControl sqlControl);
  
  public WchQualification insert(final SqlSession sqlSession, final WchQualification wchQualification);
  
  public WchQualification insert(final WchQualification wchQualification);
  
  public WchQualification get(final SqlSession sqlSession, final WchQualification wchQualification, SqlControl sqlControl);
  
  public WchQualification get(final WchQualification wchQualification, SqlControl sqlControl);
  
  public WchQualification get(final SqlSession sqlSession, final WchQualification wchQualification);
  
  public WchQualification get(final WchQualification wchQualification);
  
  public int update(final SqlSession sqlSession, final WchQualification wchQualification, SqlControl sqlControl);
  
  public int update(final WchQualification wchQualification, SqlControl sqlControl);
  
  public int update(final SqlSession sqlSession, final WchQualification wchQualification);
  
  public int update(final WchQualification wchQualification);
  
  public int delete(final SqlSession sqlSession, final WchQualification wchQualification, SqlControl sqlControl);
  
  public int delete(final WchQualification wchQualification, SqlControl sqlControl);
  
  public int delete(final SqlSession sqlSession, final WchQualification wchQualification);
  
  public int delete(final WchQualification wchQualification);
  
  public List<WchQualification> list(final SqlSession sqlSession, final WchQualification wchQualification, SqlControl sqlControl);
  
  public List<WchQualification> list(final WchQualification wchQualification, SqlControl sqlControl);
  
  public List<WchQualification> list(final SqlSession sqlSession, final WchQualification wchQualification);
  
  public List<WchQualification> list(final WchQualification wchQualification);
  
  public int query(final SqlSession sqlSession, final WchQualification wchQualification, SqlControl sqlControl, final SqlRowProcessor<WchQualification> sqlRowProcessor);
  
  public int query(final WchQualification wchQualification, SqlControl sqlControl, final SqlRowProcessor<WchQualification> sqlRowProcessor);
  
  public int query(final SqlSession sqlSession, final WchQualification wchQualification, final SqlRowProcessor<WchQualification> sqlRowProcessor);
  
  public int query(final WchQualification wchQualification, final SqlRowProcessor<WchQualification> sqlRowProcessor);
  
  public List<WchQualification> listFromTo(final SqlSession sqlSession, final WchQualification wchQualification, SqlControl sqlControl);
  
  public List<WchQualification> listFromTo(final WchQualification wchQualification, SqlControl sqlControl);
  
  public List<WchQualification> listFromTo(final SqlSession sqlSession, final WchQualification wchQualification);
  
  public List<WchQualification> listFromTo(final WchQualification wchQualification);
  
  public int count(final SqlSession sqlSession, final WchQualification wchQualification, SqlControl sqlControl);
  
  public int count(final WchQualification wchQualification, SqlControl sqlControl);
  
  public int count(final SqlSession sqlSession, final WchQualification wchQualification);
  
  public int count(final WchQualification wchQualification);
}
