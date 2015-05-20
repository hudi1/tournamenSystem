package org.toursys.repository.dao.impl;

import org.toursys.repository.dao.ParticipantDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlproc.engine.SqlControl;
import org.sqlproc.engine.SqlCrudEngine;
import org.sqlproc.engine.SqlEngineFactory;
import org.sqlproc.engine.SqlQueryEngine;
import org.sqlproc.engine.SqlProcedureEngine;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlSessionFactory;
import org.sqlproc.engine.impl.SqlStandardControl;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.PlayOffGame;

public class ParticipantDaoImpl implements ParticipantDao {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected SqlEngineFactory sqlEngineFactory;
	protected SqlSessionFactory sqlSessionFactory;
			
	public ParticipantDaoImpl() {
	}
			
	public ParticipantDaoImpl(SqlEngineFactory sqlEngineFactory) {
		this.sqlEngineFactory = sqlEngineFactory;
	}
			
	public ParticipantDaoImpl(SqlEngineFactory sqlEngineFactory, SqlSessionFactory sqlSessionFactory) {
		this.sqlEngineFactory = sqlEngineFactory;
		this.sqlSessionFactory = sqlSessionFactory;
	}
	

	public Participant insert(SqlSession sqlSession, Participant participant, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("insert participant: " + participant + " " + sqlControl);
		}
		SqlCrudEngine sqlInsertParticipant = sqlEngineFactory.getCheckedCrudEngine("INSERT_PARTICIPANT");
		int count = sqlInsertParticipant.insert(sqlSession, participant, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("insert participant result: " + count + " " + participant);
		}
		return (count > 0) ? participant : null;
	}
	public Participant insert(Participant participant, SqlControl sqlControl) {
		return insert(sqlSessionFactory.getSqlSession(), participant, sqlControl);
	}
	public Participant insert(SqlSession sqlSession, Participant participant) {
		return insert(sqlSession, participant, null);
	}
	public Participant insert(Participant participant) {
		return insert(participant, null);
	}

	public Participant get(SqlSession sqlSession, Participant participant, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("get get: " + participant + " " + sqlControl);
		}
		SqlCrudEngine sqlGetEngineParticipant = sqlEngineFactory.getCheckedCrudEngine("GET_PARTICIPANT");
		sqlControl = getMoreResultClasses(participant, sqlControl);
		Participant participantGot = sqlGetEngineParticipant.get(sqlSession, Participant.class, participant, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("get participant result: " + participantGot);
		}
		return participantGot;
	}
	public Participant get(Participant participant, SqlControl sqlControl) {
		return get(sqlSessionFactory.getSqlSession(), participant, sqlControl);
	}
	public Participant get(SqlSession sqlSession, Participant participant) {
		return get(sqlSession, participant, null);
	}
	public Participant get(Participant participant) {
		return get(participant, null);
	}

	public int update(SqlSession sqlSession, Participant participant, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("update participant: " + participant + " " + sqlControl);
		}
		SqlCrudEngine sqlUpdateEngineParticipant = sqlEngineFactory.getCheckedCrudEngine("UPDATE_PARTICIPANT");
		int count = sqlUpdateEngineParticipant.update(sqlSession, participant, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("update participant result count: " + count);
		}
		return count;
	}
	public int update(Participant participant, SqlControl sqlControl) {
		return update(sqlSessionFactory.getSqlSession(), participant, sqlControl);
	}
	public int update(SqlSession sqlSession, Participant participant) {
		return update(sqlSession, participant, null);
	}
	public int update(Participant participant) {
		return update(participant, null);
	}

	public int delete(SqlSession sqlSession, Participant participant, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("delete participant: " + participant + " " + sqlControl);
		}
		SqlCrudEngine sqlDeleteEngineParticipant = sqlEngineFactory.getCheckedCrudEngine("DELETE_PARTICIPANT");
		int count = sqlDeleteEngineParticipant.delete(sqlSession, participant, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("delete participant result count: " + count);
		}
		return count;
	}
	public int delete(Participant participant, SqlControl sqlControl) {
		return delete(sqlSessionFactory.getSqlSession(), participant, sqlControl);
	}
	public int delete(SqlSession sqlSession, Participant participant) {
		return delete(sqlSession, participant, null);
	}
	public int delete(Participant participant) {
		return delete(participant, null);
	}

	public List<Participant> list(SqlSession sqlSession, Participant participant, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("list participant: " + participant + " " + sqlControl);
		}
		SqlQueryEngine sqlEngineParticipant = sqlEngineFactory.getCheckedQueryEngine("SELECT_PARTICIPANT");
		sqlControl = getMoreResultClasses(participant, sqlControl);
		List<Participant> participantList = sqlEngineParticipant.query(sqlSession, Participant.class, participant, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("list participant size: " + ((participantList != null) ? participantList.size() : "null"));
		}
		return participantList;
	}
	public List<Participant> list(Participant participant, SqlControl sqlControl) {
		return list(sqlSessionFactory.getSqlSession(), participant, sqlControl);
	}
	public List<Participant> list(SqlSession sqlSession, Participant participant) {
		return list(sqlSession, participant, null);
	}
	public List<Participant> list(Participant participant) {
		return list(participant, null);
	}

	public int count(SqlSession sqlSession, Participant participant, SqlControl sqlControl) {
		if (logger.isTraceEnabled()) {
			logger.trace("count participant: " + participant + " " + sqlControl);
		}
		SqlQueryEngine sqlEngineParticipant = sqlEngineFactory.getCheckedQueryEngine("SELECT_PARTICIPANT");
		sqlControl = getMoreResultClasses(participant, sqlControl);
		int count = sqlEngineParticipant.queryCount(sqlSession, participant, sqlControl);
		if (logger.isTraceEnabled()) {
			logger.trace("count: " + count);
		}
		return count;
	}
	public int count(Participant participant, SqlControl sqlControl) {
		return count(sqlSessionFactory.getSqlSession(), participant, sqlControl);
	}
	public int count(SqlSession sqlSession, Participant participant) {
		return count(sqlSession, participant, null);
	}
	public int count(Participant participant) {
		return count(participant, null);
	}

	SqlControl getMoreResultClasses(Participant participant, SqlControl sqlControl) {
		if (sqlControl != null && sqlControl.getMoreResultClasses() != null)
			return sqlControl;
		Map<String, Class<?>> moreResultClasses = null;
				if (participant != null && participant.toInit(Participant.Association.games.name())) {
			if (moreResultClasses == null)
				moreResultClasses = new HashMap<String, Class<?>>();
				moreResultClasses.put("playOffGame", PlayOffGame.class);
		}
		if (moreResultClasses != null) {
			sqlControl = new SqlStandardControl(sqlControl);
			((SqlStandardControl) sqlControl).setMoreResultClasses(moreResultClasses);
		}
		return sqlControl;
	}
}
