package org.tahom.repository.dao.impl;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.sqlproc.engine.SqlControl;
import org.sqlproc.engine.SqlEngineFactory;
import org.sqlproc.engine.SqlRowProcessor;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlSessionFactory;
import org.tahom.repository.dao.ParticipantDao;
import org.tahom.repository.model.Participant;

@SuppressWarnings("all")
public class ParticipantDaoImpl implements ParticipantDao {
  protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
  
  public ParticipantDaoImpl() {
  }
  
  public ParticipantDaoImpl(final SqlEngineFactory sqlEngineFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
  }
  
  public ParticipantDaoImpl(final SqlEngineFactory sqlEngineFactory, final SqlSessionFactory sqlSessionFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
    this.sqlSessionFactory = sqlSessionFactory;
  }
  
  protected SqlEngineFactory sqlEngineFactory;
  
  protected SqlSessionFactory sqlSessionFactory;
  
  public Participant insert(final SqlSession sqlSession, final Participant participant, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql insert participant: " + participant + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlInsertParticipant = sqlEngineFactory.getCheckedCrudEngine("INSERT_PARTICIPANT");
    int count = sqlInsertParticipant.insert(sqlSession, participant, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql insert participant result: " + count + " " + participant);
    }
    return (count > 0) ? participant : null;
  }
  
  public Participant insert(final Participant participant, SqlControl sqlControl) {
    return insert(sqlSessionFactory.getSqlSession(), participant, sqlControl);
  }
  
  public Participant insert(final SqlSession sqlSession, final Participant participant) {
    return insert(sqlSession, participant, null);
  }
  
  public Participant insert(final Participant participant) {
    return insert(participant, null);
  }
  
  public Participant get(final SqlSession sqlSession, final Participant participant, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql get: " + participant + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlGetEngineParticipant = sqlEngineFactory.getCheckedCrudEngine("GET_PARTICIPANT");
    //sqlControl = getMoreResultClasses(participant, sqlControl);
    Participant participantGot = sqlGetEngineParticipant.get(sqlSession, Participant.class, participant, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql get participant result: " + participantGot);
    }
    return participantGot;
  }
  
  public Participant get(final Participant participant, SqlControl sqlControl) {
    return get(sqlSessionFactory.getSqlSession(), participant, sqlControl);
  }
  
  public Participant get(final SqlSession sqlSession, final Participant participant) {
    return get(sqlSession, participant, null);
  }
  
  public Participant get(final Participant participant) {
    return get(participant, null);
  }
  
  public int update(final SqlSession sqlSession, final Participant participant, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql update participant: " + participant + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlUpdateEngineParticipant = sqlEngineFactory.getCheckedCrudEngine("UPDATE_PARTICIPANT");
    int count = sqlUpdateEngineParticipant.update(sqlSession, participant, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql update participant result count: " + count);
    }
    return count;
  }
  
  public int update(final Participant participant, SqlControl sqlControl) {
    return update(sqlSessionFactory.getSqlSession(), participant, sqlControl);
  }
  
  public int update(final SqlSession sqlSession, final Participant participant) {
    return update(sqlSession, participant, null);
  }
  
  public int update(final Participant participant) {
    return update(participant, null);
  }
  
  public int delete(final SqlSession sqlSession, final Participant participant, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql delete participant: " + participant + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlDeleteEngineParticipant = sqlEngineFactory.getCheckedCrudEngine("DELETE_PARTICIPANT");
    int count = sqlDeleteEngineParticipant.delete(sqlSession, participant, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql delete participant result count: " + count);
    }
    return count;
  }
  
  public int delete(final Participant participant, SqlControl sqlControl) {
    return delete(sqlSessionFactory.getSqlSession(), participant, sqlControl);
  }
  
  public int delete(final SqlSession sqlSession, final Participant participant) {
    return delete(sqlSession, participant, null);
  }
  
  public int delete(final Participant participant) {
    return delete(participant, null);
  }
  
  public List<Participant> list(final SqlSession sqlSession, final Participant participant, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list participant: " + participant + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineParticipant = sqlEngineFactory.getCheckedQueryEngine("SELECT_PARTICIPANT");
    //sqlControl = getMoreResultClasses(participant, sqlControl);
    List<Participant> participantList = sqlEngineParticipant.query(sqlSession, Participant.class, participant, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list participant size: " + ((participantList != null) ? participantList.size() : "null"));
    }
    return participantList;
  }
  
  public List<Participant> list(final Participant participant, SqlControl sqlControl) {
    return list(sqlSessionFactory.getSqlSession(), participant, sqlControl);
  }
  
  public List<Participant> list(final SqlSession sqlSession, final Participant participant) {
    return list(sqlSession, participant, null);
  }
  
  public List<Participant> list(final Participant participant) {
    return list(participant, null);
  }
  
  public int query(final SqlSession sqlSession, final Participant participant, SqlControl sqlControl, final SqlRowProcessor<Participant> sqlRowProcessor) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql query participant: " + participant + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineParticipant = sqlEngineFactory.getCheckedQueryEngine("SELECT_PARTICIPANT");
    //sqlControl = getMoreResultClasses(participant, sqlControl);
    int rownums = sqlEngineParticipant.query(sqlSession, Participant.class, participant, sqlControl, sqlRowProcessor);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql query participant size: " + rownums);
    }
    return rownums;
  }
  
  public int query(final Participant participant, SqlControl sqlControl, final SqlRowProcessor<Participant> sqlRowProcessor) {
    return query(sqlSessionFactory.getSqlSession(), participant, sqlControl, sqlRowProcessor);
  }
  
  public int query(final SqlSession sqlSession, final Participant participant, final SqlRowProcessor<Participant> sqlRowProcessor) {
    return query(sqlSession, participant, null, sqlRowProcessor);
  }
  
  public int query(final Participant participant, final SqlRowProcessor<Participant> sqlRowProcessor) {
    return query(participant, null, sqlRowProcessor);
  }
  
  public List<Participant> listFromTo(final SqlSession sqlSession, final Participant participant, SqlControl sqlControl) {
    if (sqlControl == null || sqlControl.getFirstResult() == null || sqlControl.getMaxResults() == null || participant == null)
    	return list(sqlSession, participant, sqlControl);
    
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list participant: " + participant + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineParticipant = sqlEngineFactory.getCheckedQueryEngine("SELECT_PARTICIPANT");
    //sqlControl = getMoreResultClasses(participant, sqlControl);
    participant.setOnlyIds_(true);
    java.util.Set<String> initAssociations = participant.getInitAssociations_();
    participant.setInitAssociations_(new java.util.HashSet<String>());
    final java.util.List<java.lang.Integer> ids_ = sqlEngineParticipant.query(sqlSession, java.lang.Integer.class, participant, sqlControl);
    participant.setInitAssociations_(initAssociations);
    
    List<Participant> participantList = new java.util.ArrayList<Participant>();
    if (!ids_.isEmpty()) {
    	org.sqlproc.engine.impl.SqlStandardControl sqlc = new org.sqlproc.engine.impl.SqlStandardControl(sqlControl);
    	sqlc.setFirstResult(0);
    	sqlc.setMaxResults(0);
    	sqlc.setOrder(null);
    	final Map<java.lang.Integer, Participant> map = new java.util.HashMap<java.lang.Integer, Participant>();
    	final SqlRowProcessor<Participant> sqlRowProcessor = new SqlRowProcessor<Participant>() {
    		@Override
    		public boolean processRow(Participant result, int rownum) throws org.sqlproc.engine.SqlRuntimeException {
    			map.put(result.getId(), result);
    			return true;
    		}
    	};
    	sqlEngineParticipant.query(sqlSession, Participant.class, new Participant()._setIds_(ids_), sqlc, sqlRowProcessor);
    	for (java.lang.Integer id : ids_)
    		participantList.add(map.get(id));
    }
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list participant size: " + ((participantList != null) ? participantList.size() : "null"));
    }
    return participantList;
  }
  
  public List<Participant> listFromTo(final Participant participant, SqlControl sqlControl) {
    return listFromTo(sqlSessionFactory.getSqlSession(), participant, sqlControl);
  }
  
  public List<Participant> listFromTo(final SqlSession sqlSession, final Participant participant) {
    return listFromTo(sqlSession, participant, null);
  }
  
  public List<Participant> listFromTo(final Participant participant) {
    return listFromTo(participant, null);
  }
  
  public int count(final SqlSession sqlSession, final Participant participant, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("count participant: " + participant + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineParticipant = sqlEngineFactory.getCheckedQueryEngine("SELECT_PARTICIPANT");
    //sqlControl = getMoreResultClasses(participant, sqlControl);
    int count = sqlEngineParticipant.queryCount(sqlSession, participant, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("count: " + count);
    }
    return count;
  }
  
  public int count(final Participant participant, SqlControl sqlControl) {
    return count(sqlSessionFactory.getSqlSession(), participant, sqlControl);
  }
  
  public int count(final SqlSession sqlSession, final Participant participant) {
    return count(sqlSession, participant, null);
  }
  
  public int count(final Participant participant) {
    return count(participant, null);
  }
}
