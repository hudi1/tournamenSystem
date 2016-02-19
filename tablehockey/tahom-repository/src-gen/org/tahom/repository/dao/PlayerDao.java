package org.tahom.repository.dao;

import java.util.List;
import org.sqlproc.engine.SqlControl;
import org.sqlproc.engine.SqlRowProcessor;
import org.sqlproc.engine.SqlSession;
import org.tahom.repository.model.Player;

@SuppressWarnings("all")
public interface PlayerDao {
  public Player insert(final SqlSession sqlSession, final Player player, SqlControl sqlControl);
  
  public Player insert(final Player player, SqlControl sqlControl);
  
  public Player insert(final SqlSession sqlSession, final Player player);
  
  public Player insert(final Player player);
  
  public Player get(final SqlSession sqlSession, final Player player, SqlControl sqlControl);
  
  public Player get(final Player player, SqlControl sqlControl);
  
  public Player get(final SqlSession sqlSession, final Player player);
  
  public Player get(final Player player);
  
  public int update(final SqlSession sqlSession, final Player player, SqlControl sqlControl);
  
  public int update(final Player player, SqlControl sqlControl);
  
  public int update(final SqlSession sqlSession, final Player player);
  
  public int update(final Player player);
  
  public int delete(final SqlSession sqlSession, final Player player, SqlControl sqlControl);
  
  public int delete(final Player player, SqlControl sqlControl);
  
  public int delete(final SqlSession sqlSession, final Player player);
  
  public int delete(final Player player);
  
  public List<Player> list(final SqlSession sqlSession, final Player player, SqlControl sqlControl);
  
  public List<Player> list(final Player player, SqlControl sqlControl);
  
  public List<Player> list(final SqlSession sqlSession, final Player player);
  
  public List<Player> list(final Player player);
  
  public int query(final SqlSession sqlSession, final Player player, SqlControl sqlControl, final SqlRowProcessor<Player> sqlRowProcessor);
  
  public int query(final Player player, SqlControl sqlControl, final SqlRowProcessor<Player> sqlRowProcessor);
  
  public int query(final SqlSession sqlSession, final Player player, final SqlRowProcessor<Player> sqlRowProcessor);
  
  public int query(final Player player, final SqlRowProcessor<Player> sqlRowProcessor);
  
  public List<Player> listFromTo(final SqlSession sqlSession, final Player player, SqlControl sqlControl);
  
  public List<Player> listFromTo(final Player player, SqlControl sqlControl);
  
  public List<Player> listFromTo(final SqlSession sqlSession, final Player player);
  
  public List<Player> listFromTo(final Player player);
  
  public int count(final SqlSession sqlSession, final Player player, SqlControl sqlControl);
  
  public int count(final Player player, SqlControl sqlControl);
  
  public int count(final SqlSession sqlSession, final Player player);
  
  public int count(final Player player);
}
