package org.tahom.repository.dao.impl;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.sqlproc.engine.SqlControl;
import org.sqlproc.engine.SqlEngineFactory;
import org.sqlproc.engine.SqlRowProcessor;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlSessionFactory;
import org.tahom.repository.dao.IthfTournamentDao;
import org.tahom.repository.model.IthfTournament;

@SuppressWarnings("all")
public class IthfTournamentDaoImpl implements IthfTournamentDao {
  protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
  
  public IthfTournamentDaoImpl() {
  }
  
  public IthfTournamentDaoImpl(final SqlEngineFactory sqlEngineFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
  }
  
  public IthfTournamentDaoImpl(final SqlEngineFactory sqlEngineFactory, final SqlSessionFactory sqlSessionFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
    this.sqlSessionFactory = sqlSessionFactory;
  }
  
  protected SqlEngineFactory sqlEngineFactory;
  
  protected SqlSessionFactory sqlSessionFactory;
  
  public IthfTournament insert(final SqlSession sqlSession, final IthfTournament ithfTournament, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql insert ithfTournament: " + ithfTournament + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlInsertIthfTournament = sqlEngineFactory.getCheckedCrudEngine("INSERT_ITHF_TOURNAMENT");
    int count = sqlInsertIthfTournament.insert(sqlSession, ithfTournament, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql insert ithfTournament result: " + count + " " + ithfTournament);
    }
    return (count > 0) ? ithfTournament : null;
  }
  
  public IthfTournament insert(final IthfTournament ithfTournament, SqlControl sqlControl) {
    return insert(sqlSessionFactory.getSqlSession(), ithfTournament, sqlControl);
  }
  
  public IthfTournament insert(final SqlSession sqlSession, final IthfTournament ithfTournament) {
    return insert(sqlSession, ithfTournament, null);
  }
  
  public IthfTournament insert(final IthfTournament ithfTournament) {
    return insert(ithfTournament, null);
  }
  
  public IthfTournament get(final SqlSession sqlSession, final IthfTournament ithfTournament, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql get: " + ithfTournament + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlGetEngineIthfTournament = sqlEngineFactory.getCheckedCrudEngine("GET_ITHF_TOURNAMENT");
    //sqlControl = getMoreResultClasses(ithfTournament, sqlControl);
    IthfTournament ithfTournamentGot = sqlGetEngineIthfTournament.get(sqlSession, IthfTournament.class, ithfTournament, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql get ithfTournament result: " + ithfTournamentGot);
    }
    return ithfTournamentGot;
  }
  
  public IthfTournament get(final IthfTournament ithfTournament, SqlControl sqlControl) {
    return get(sqlSessionFactory.getSqlSession(), ithfTournament, sqlControl);
  }
  
  public IthfTournament get(final SqlSession sqlSession, final IthfTournament ithfTournament) {
    return get(sqlSession, ithfTournament, null);
  }
  
  public IthfTournament get(final IthfTournament ithfTournament) {
    return get(ithfTournament, null);
  }
  
  public int update(final SqlSession sqlSession, final IthfTournament ithfTournament, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql update ithfTournament: " + ithfTournament + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlUpdateEngineIthfTournament = sqlEngineFactory.getCheckedCrudEngine("UPDATE_ITHF_TOURNAMENT");
    int count = sqlUpdateEngineIthfTournament.update(sqlSession, ithfTournament, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql update ithfTournament result count: " + count);
    }
    return count;
  }
  
  public int update(final IthfTournament ithfTournament, SqlControl sqlControl) {
    return update(sqlSessionFactory.getSqlSession(), ithfTournament, sqlControl);
  }
  
  public int update(final SqlSession sqlSession, final IthfTournament ithfTournament) {
    return update(sqlSession, ithfTournament, null);
  }
  
  public int update(final IthfTournament ithfTournament) {
    return update(ithfTournament, null);
  }
  
  public int delete(final SqlSession sqlSession, final IthfTournament ithfTournament, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql delete ithfTournament: " + ithfTournament + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlDeleteEngineIthfTournament = sqlEngineFactory.getCheckedCrudEngine("DELETE_ITHF_TOURNAMENT");
    int count = sqlDeleteEngineIthfTournament.delete(sqlSession, ithfTournament, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql delete ithfTournament result count: " + count);
    }
    return count;
  }
  
  public int delete(final IthfTournament ithfTournament, SqlControl sqlControl) {
    return delete(sqlSessionFactory.getSqlSession(), ithfTournament, sqlControl);
  }
  
  public int delete(final SqlSession sqlSession, final IthfTournament ithfTournament) {
    return delete(sqlSession, ithfTournament, null);
  }
  
  public int delete(final IthfTournament ithfTournament) {
    return delete(ithfTournament, null);
  }
  
  public List<IthfTournament> list(final SqlSession sqlSession, final IthfTournament ithfTournament, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list ithfTournament: " + ithfTournament + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineIthfTournament = sqlEngineFactory.getCheckedQueryEngine("SELECT_ITHF_TOURNAMENT");
    //sqlControl = getMoreResultClasses(ithfTournament, sqlControl);
    List<IthfTournament> ithfTournamentList = sqlEngineIthfTournament.query(sqlSession, IthfTournament.class, ithfTournament, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list ithfTournament size: " + ((ithfTournamentList != null) ? ithfTournamentList.size() : "null"));
    }
    return ithfTournamentList;
  }
  
  public List<IthfTournament> list(final IthfTournament ithfTournament, SqlControl sqlControl) {
    return list(sqlSessionFactory.getSqlSession(), ithfTournament, sqlControl);
  }
  
  public List<IthfTournament> list(final SqlSession sqlSession, final IthfTournament ithfTournament) {
    return list(sqlSession, ithfTournament, null);
  }
  
  public List<IthfTournament> list(final IthfTournament ithfTournament) {
    return list(ithfTournament, null);
  }
  
  public int query(final SqlSession sqlSession, final IthfTournament ithfTournament, SqlControl sqlControl, final SqlRowProcessor<IthfTournament> sqlRowProcessor) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql query ithfTournament: " + ithfTournament + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineIthfTournament = sqlEngineFactory.getCheckedQueryEngine("SELECT_ITHF_TOURNAMENT");
    //sqlControl = getMoreResultClasses(ithfTournament, sqlControl);
    int rownums = sqlEngineIthfTournament.query(sqlSession, IthfTournament.class, ithfTournament, sqlControl, sqlRowProcessor);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql query ithfTournament size: " + rownums);
    }
    return rownums;
  }
  
  public int query(final IthfTournament ithfTournament, SqlControl sqlControl, final SqlRowProcessor<IthfTournament> sqlRowProcessor) {
    return query(sqlSessionFactory.getSqlSession(), ithfTournament, sqlControl, sqlRowProcessor);
  }
  
  public int query(final SqlSession sqlSession, final IthfTournament ithfTournament, final SqlRowProcessor<IthfTournament> sqlRowProcessor) {
    return query(sqlSession, ithfTournament, null, sqlRowProcessor);
  }
  
  public int query(final IthfTournament ithfTournament, final SqlRowProcessor<IthfTournament> sqlRowProcessor) {
    return query(ithfTournament, null, sqlRowProcessor);
  }
  
  public List<IthfTournament> listFromTo(final SqlSession sqlSession, final IthfTournament ithfTournament, SqlControl sqlControl) {
    if (sqlControl == null || sqlControl.getFirstResult() == null || sqlControl.getMaxResults() == null || ithfTournament == null)
    	return list(sqlSession, ithfTournament, sqlControl);
    
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list ithfTournament: " + ithfTournament + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineIthfTournament = sqlEngineFactory.getCheckedQueryEngine("SELECT_ITHF_TOURNAMENT");
    //sqlControl = getMoreResultClasses(ithfTournament, sqlControl);
    ithfTournament.setOnlyIds_(true);
    java.util.Set<String> initAssociations = ithfTournament.getInitAssociations_();
    ithfTournament.setInitAssociations_(new java.util.HashSet<String>());
    final java.util.List<java.lang.Integer> ids_ = sqlEngineIthfTournament.query(sqlSession, java.lang.Integer.class, ithfTournament, sqlControl);
    ithfTournament.setInitAssociations_(initAssociations);
    
    List<IthfTournament> ithfTournamentList = new java.util.ArrayList<IthfTournament>();
    if (!ids_.isEmpty()) {
    	org.sqlproc.engine.impl.SqlStandardControl sqlc = new org.sqlproc.engine.impl.SqlStandardControl(sqlControl);
    	sqlc.setFirstResult(0);
    	sqlc.setMaxResults(0);
    	sqlc.setOrder(null);
    	final Map<java.lang.Integer, IthfTournament> map = new java.util.HashMap<java.lang.Integer, IthfTournament>();
    	final SqlRowProcessor<IthfTournament> sqlRowProcessor = new SqlRowProcessor<IthfTournament>() {
    		@Override
    		public boolean processRow(IthfTournament result, int rownum) throws org.sqlproc.engine.SqlRuntimeException {
    			map.put(result.getId(), result);
    			return true;
    		}
    	};
    	sqlEngineIthfTournament.query(sqlSession, IthfTournament.class, new IthfTournament()._setIds_(ids_), sqlc, sqlRowProcessor);
    	for (java.lang.Integer id : ids_)
    		ithfTournamentList.add(map.get(id));
    }
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list ithfTournament size: " + ((ithfTournamentList != null) ? ithfTournamentList.size() : "null"));
    }
    return ithfTournamentList;
  }
  
  public List<IthfTournament> listFromTo(final IthfTournament ithfTournament, SqlControl sqlControl) {
    return listFromTo(sqlSessionFactory.getSqlSession(), ithfTournament, sqlControl);
  }
  
  public List<IthfTournament> listFromTo(final SqlSession sqlSession, final IthfTournament ithfTournament) {
    return listFromTo(sqlSession, ithfTournament, null);
  }
  
  public List<IthfTournament> listFromTo(final IthfTournament ithfTournament) {
    return listFromTo(ithfTournament, null);
  }
  
  public int count(final SqlSession sqlSession, final IthfTournament ithfTournament, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("count ithfTournament: " + ithfTournament + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineIthfTournament = sqlEngineFactory.getCheckedQueryEngine("SELECT_ITHF_TOURNAMENT");
    //sqlControl = getMoreResultClasses(ithfTournament, sqlControl);
    int count = sqlEngineIthfTournament.queryCount(sqlSession, ithfTournament, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("count: " + count);
    }
    return count;
  }
  
  public int count(final IthfTournament ithfTournament, SqlControl sqlControl) {
    return count(sqlSessionFactory.getSqlSession(), ithfTournament, sqlControl);
  }
  
  public int count(final SqlSession sqlSession, final IthfTournament ithfTournament) {
    return count(sqlSession, ithfTournament, null);
  }
  
  public int count(final IthfTournament ithfTournament) {
    return count(ithfTournament, null);
  }
}
