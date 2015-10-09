package org.toursys.repository.dao;

import java.util.List;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlControl;
import org.toursys.repository.model.WchTournament;

public interface WchTournamentDao {
	
		public WchTournament insert(SqlSession sqlSession, WchTournament wchTournament, SqlControl sqlControl);
		public WchTournament insert(WchTournament wchTournament, SqlControl sqlControl);
		public WchTournament insert(SqlSession sqlSession, WchTournament wchTournament);
		public WchTournament insert(WchTournament wchTournament);
	
		public WchTournament get(SqlSession sqlSession, WchTournament wchTournament, SqlControl sqlControl);
		public WchTournament get(WchTournament wchTournament, SqlControl sqlControl);
			public WchTournament get(SqlSession sqlSession, WchTournament wchTournament);
		public WchTournament get(WchTournament wchTournament);
	
		public int update(SqlSession sqlSession, WchTournament wchTournament, SqlControl sqlControl);
		public int update(WchTournament wchTournament, SqlControl sqlControl);
		public int update(SqlSession sqlSession, WchTournament wchTournament);
		public int update(WchTournament wchTournament);
	
		public int delete(SqlSession sqlSession, WchTournament wchTournament, SqlControl sqlControl);
		public int delete(WchTournament wchTournament, SqlControl sqlControl);
		public int delete(SqlSession sqlSession, WchTournament wchTournament);
		public int delete(WchTournament wchTournament);
	
		public List<WchTournament> list(SqlSession sqlSession, WchTournament wchTournament, SqlControl sqlControl);
		public List<WchTournament> list(WchTournament wchTournament, SqlControl sqlControl);
		public List<WchTournament> list(SqlSession sqlSession, WchTournament wchTournament);
		public List<WchTournament> list(WchTournament wchTournament);
	
		public int count(SqlSession sqlSession, WchTournament wchTournament, SqlControl sqlControl);
		public int count(WchTournament wchTournament, SqlControl sqlControl);
		public int count(SqlSession sqlSession, WchTournament wchTournament);
		public int count(WchTournament wchTournament);
}
