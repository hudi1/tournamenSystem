package org.toursys.repository.dao;

import java.util.List;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlControl;
import org.toursys.repository.model.Player;

public interface PlayerDao {
  
  public Player insert(SqlSession sqlSession, Player player, SqlControl sqlControl);
  
  public Player insert(Player player, SqlControl sqlControl);
  
  public Player insert(SqlSession sqlSession, Player player);
  
  public Player insert(Player player);
  
  public Player get(SqlSession sqlSession, Player player, SqlControl sqlControl);
  	
  public Player get(Player player, SqlControl sqlControl);
  	
  public Player get(SqlSession sqlSession, Player player);
  	
  public Player get(Player player);
  
  public int update(SqlSession sqlSession, Player player, SqlControl sqlControl);
  
  public int update(Player player, SqlControl sqlControl);
  
  public int update(SqlSession sqlSession, Player player);
  
  public int update(Player player);
  
  public int delete(SqlSession sqlSession, Player player, SqlControl sqlControl);
  
  public int delete(Player player, SqlControl sqlControl);
  
  public int delete(SqlSession sqlSession, Player player);
  
  public int delete(Player player);
  
  public List<Player> list(SqlSession sqlSession, Player player, SqlControl sqlControl);
  
  public List<Player> list(Player player, SqlControl sqlControl);
  
  public List<Player> list(SqlSession sqlSession, Player player);
  
  public List<Player> list(Player player);
  
  public int count(SqlSession sqlSession, Player player, SqlControl sqlControl);
  
  public int count(Player player, SqlControl sqlControl);
  
  public int count(SqlSession sqlSession, Player player);
  
  public int count(Player player);
}
