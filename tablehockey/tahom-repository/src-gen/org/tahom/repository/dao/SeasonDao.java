package org.tahom.repository.dao;

import java.util.List;
import org.sqlproc.engine.SqlControl;
import org.sqlproc.engine.SqlRowProcessor;
import org.sqlproc.engine.SqlSession;
import org.tahom.repository.model.Season;

@SuppressWarnings("all")
public interface SeasonDao {
  public Season insert(final SqlSession sqlSession, final Season season, SqlControl sqlControl);
  
  public Season insert(final Season season, SqlControl sqlControl);
  
  public Season insert(final SqlSession sqlSession, final Season season);
  
  public Season insert(final Season season);
  
  public Season get(final SqlSession sqlSession, final Season season, SqlControl sqlControl);
  
  public Season get(final Season season, SqlControl sqlControl);
  
  public Season get(final SqlSession sqlSession, final Season season);
  
  public Season get(final Season season);
  
  public int update(final SqlSession sqlSession, final Season season, SqlControl sqlControl);
  
  public int update(final Season season, SqlControl sqlControl);
  
  public int update(final SqlSession sqlSession, final Season season);
  
  public int update(final Season season);
  
  public int delete(final SqlSession sqlSession, final Season season, SqlControl sqlControl);
  
  public int delete(final Season season, SqlControl sqlControl);
  
  public int delete(final SqlSession sqlSession, final Season season);
  
  public int delete(final Season season);
  
  public List<Season> list(final SqlSession sqlSession, final Season season, SqlControl sqlControl);
  
  public List<Season> list(final Season season, SqlControl sqlControl);
  
  public List<Season> list(final SqlSession sqlSession, final Season season);
  
  public List<Season> list(final Season season);
  
  public int query(final SqlSession sqlSession, final Season season, SqlControl sqlControl, final SqlRowProcessor<Season> sqlRowProcessor);
  
  public int query(final Season season, SqlControl sqlControl, final SqlRowProcessor<Season> sqlRowProcessor);
  
  public int query(final SqlSession sqlSession, final Season season, final SqlRowProcessor<Season> sqlRowProcessor);
  
  public int query(final Season season, final SqlRowProcessor<Season> sqlRowProcessor);
  
  public List<Season> listFromTo(final SqlSession sqlSession, final Season season, SqlControl sqlControl);
  
  public List<Season> listFromTo(final Season season, SqlControl sqlControl);
  
  public List<Season> listFromTo(final SqlSession sqlSession, final Season season);
  
  public List<Season> listFromTo(final Season season);
  
  public int count(final SqlSession sqlSession, final Season season, SqlControl sqlControl);
  
  public int count(final Season season, SqlControl sqlControl);
  
  public int count(final SqlSession sqlSession, final Season season);
  
  public int count(final Season season);
}
