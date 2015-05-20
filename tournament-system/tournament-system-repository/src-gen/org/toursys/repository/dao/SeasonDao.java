package org.toursys.repository.dao;

import java.util.List;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlControl;
import org.toursys.repository.model.Season;

public interface SeasonDao {
	
		public Season insert(SqlSession sqlSession, Season season, SqlControl sqlControl);
		public Season insert(Season season, SqlControl sqlControl);
		public Season insert(SqlSession sqlSession, Season season);
		public Season insert(Season season);
	
		public Season get(SqlSession sqlSession, Season season, SqlControl sqlControl);
		public Season get(Season season, SqlControl sqlControl);
			public Season get(SqlSession sqlSession, Season season);
		public Season get(Season season);
	
		public int update(SqlSession sqlSession, Season season, SqlControl sqlControl);
		public int update(Season season, SqlControl sqlControl);
		public int update(SqlSession sqlSession, Season season);
		public int update(Season season);
	
		public int delete(SqlSession sqlSession, Season season, SqlControl sqlControl);
		public int delete(Season season, SqlControl sqlControl);
		public int delete(SqlSession sqlSession, Season season);
		public int delete(Season season);
	
		public List<Season> list(SqlSession sqlSession, Season season, SqlControl sqlControl);
		public List<Season> list(Season season, SqlControl sqlControl);
		public List<Season> list(SqlSession sqlSession, Season season);
		public List<Season> list(Season season);
	
		public int count(SqlSession sqlSession, Season season, SqlControl sqlControl);
		public int count(Season season, SqlControl sqlControl);
		public int count(SqlSession sqlSession, Season season);
		public int count(Season season);
}
