package org.toursys.repository.dao;

import java.util.List;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlControl;
import org.toursys.repository.model.WchQualification;

public interface WchQualificationDao {
	
		public WchQualification insert(SqlSession sqlSession, WchQualification wchQualification, SqlControl sqlControl);
		public WchQualification insert(WchQualification wchQualification, SqlControl sqlControl);
		public WchQualification insert(SqlSession sqlSession, WchQualification wchQualification);
		public WchQualification insert(WchQualification wchQualification);
	
		public WchQualification get(SqlSession sqlSession, WchQualification wchQualification, SqlControl sqlControl);
		public WchQualification get(WchQualification wchQualification, SqlControl sqlControl);
			public WchQualification get(SqlSession sqlSession, WchQualification wchQualification);
		public WchQualification get(WchQualification wchQualification);
	
		public int update(SqlSession sqlSession, WchQualification wchQualification, SqlControl sqlControl);
		public int update(WchQualification wchQualification, SqlControl sqlControl);
		public int update(SqlSession sqlSession, WchQualification wchQualification);
		public int update(WchQualification wchQualification);
	
		public int delete(SqlSession sqlSession, WchQualification wchQualification, SqlControl sqlControl);
		public int delete(WchQualification wchQualification, SqlControl sqlControl);
		public int delete(SqlSession sqlSession, WchQualification wchQualification);
		public int delete(WchQualification wchQualification);
	
		public List<WchQualification> list(SqlSession sqlSession, WchQualification wchQualification, SqlControl sqlControl);
		public List<WchQualification> list(WchQualification wchQualification, SqlControl sqlControl);
		public List<WchQualification> list(SqlSession sqlSession, WchQualification wchQualification);
		public List<WchQualification> list(WchQualification wchQualification);
	
		public int count(SqlSession sqlSession, WchQualification wchQualification, SqlControl sqlControl);
		public int count(WchQualification wchQualification, SqlControl sqlControl);
		public int count(SqlSession sqlSession, WchQualification wchQualification);
		public int count(WchQualification wchQualification);
}
