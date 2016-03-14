package org.tahom.repository.dao.impl;

import org.sqlproc.engine.SqlCrudEngine;
import org.sqlproc.engine.SqlEngineFactory;
import org.sqlproc.engine.SqlSessionFactory;
import org.tahom.repository.dao.FinalStandingExtDao;
import org.tahom.repository.model.Tournament;

public class FinalStandingExtDaoImp extends FinalStandingDaoImpl implements FinalStandingExtDao {

	public FinalStandingExtDaoImp(SqlEngineFactory sqlEngineFactory) {
		super(sqlEngineFactory);
	}

	public FinalStandingExtDaoImp(SqlEngineFactory sqlEngineFactory, SqlSessionFactory sqlSessionFactory) {
		super(sqlEngineFactory, sqlSessionFactory);
	}

	@Override
	public int deleteByTournament(Tournament tournament) {
		if (logger.isTraceEnabled()) {
			logger.trace("delete final standing: " + tournament);
		}
		SqlCrudEngine sqlDeleteEngineGroups = sqlEngineFactory
		        .getCheckedCrudEngine("DELETE_FINAL_STANDING_BY_TOURNAMENT");
		int count = sqlDeleteEngineGroups.delete(sqlSessionFactory.getSqlSession(), tournament);
		if (logger.isTraceEnabled()) {
			logger.trace("delete final standing result count: " + count);
		}
		return count;
	}

}
