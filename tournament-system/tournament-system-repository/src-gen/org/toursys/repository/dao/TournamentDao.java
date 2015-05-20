package org.toursys.repository.dao;

import java.util.List;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlControl;
import org.toursys.repository.model.Tournament;

public interface TournamentDao {
	
		public Tournament insert(SqlSession sqlSession, Tournament tournament, SqlControl sqlControl);
		public Tournament insert(Tournament tournament, SqlControl sqlControl);
		public Tournament insert(SqlSession sqlSession, Tournament tournament);
		public Tournament insert(Tournament tournament);
	
		public Tournament get(SqlSession sqlSession, Tournament tournament, SqlControl sqlControl);
		public Tournament get(Tournament tournament, SqlControl sqlControl);
			public Tournament get(SqlSession sqlSession, Tournament tournament);
		public Tournament get(Tournament tournament);
	
		public int update(SqlSession sqlSession, Tournament tournament, SqlControl sqlControl);
		public int update(Tournament tournament, SqlControl sqlControl);
		public int update(SqlSession sqlSession, Tournament tournament);
		public int update(Tournament tournament);
	
		public int delete(SqlSession sqlSession, Tournament tournament, SqlControl sqlControl);
		public int delete(Tournament tournament, SqlControl sqlControl);
		public int delete(SqlSession sqlSession, Tournament tournament);
		public int delete(Tournament tournament);
	
		public List<Tournament> list(SqlSession sqlSession, Tournament tournament, SqlControl sqlControl);
		public List<Tournament> list(Tournament tournament, SqlControl sqlControl);
		public List<Tournament> list(SqlSession sqlSession, Tournament tournament);
		public List<Tournament> list(Tournament tournament);
	
		public int count(SqlSession sqlSession, Tournament tournament, SqlControl sqlControl);
		public int count(Tournament tournament, SqlControl sqlControl);
		public int count(SqlSession sqlSession, Tournament tournament);
		public int count(Tournament tournament);
}
