package org.tahom.repository.dao.impl;

import org.sqlproc.engine.SqlEngineFactory;
import org.sqlproc.engine.SqlSessionFactory;
import org.tahom.repository.dao.IthfTournamentExtDao;
import org.tahom.repository.model.IthfTournament;

public class IthfTournamentExtDaoImpl extends IthfTournamentDaoImpl implements IthfTournamentExtDao {

	public IthfTournamentExtDaoImpl(SqlEngineFactory sqlEngineFactory) {
		super(sqlEngineFactory);
	}

	public IthfTournamentExtDaoImpl(SqlEngineFactory sqlEngineFactory, SqlSessionFactory sqlSessionFactory) {
		super(sqlEngineFactory, sqlSessionFactory);
	}

	@Override
	public int customCount(final IthfTournament ithfTournament) {
		if (logger.isTraceEnabled()) {
			logger.trace("custom count ithfTournament: " + ithfTournament);
		}
		org.sqlproc.engine.SqlQueryEngine sqlEngineIthfTournament = sqlEngineFactory
		        .getCheckedQueryEngine("SELECT_CUSTOM_ITHF_TOURNAMENT");
		int count = sqlEngineIthfTournament.queryCount(sqlSessionFactory.getSqlSession(), ithfTournament);
		if (logger.isTraceEnabled()) {
			logger.trace("custom count: " + count);
		}
		return count;
	}
}
