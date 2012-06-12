package org.toursys.repository.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.sqlproc.engine.SqlCrudEngine;
import org.sqlproc.engine.SqlEngineFactory;
import org.sqlproc.engine.SqlQueryEngine;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.spring.SpringSimpleSession;

public class BaseDaoImpl {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected JdbcTemplate jdbcTemplate;
	protected SqlEngineFactory sqlFactory;

	protected SqlSession getSqlSession() {
		SqlSession session = new SpringSimpleSession(jdbcTemplate);
		return session;
	}

	public SqlCrudEngine getCrudEngine(String name) {
		SqlCrudEngine queryEngine = sqlFactory.getCrudEngine(name);
		if (queryEngine == null)
			throw new RuntimeException("Missing SqlQueryEngine " + name);
		return queryEngine;
	}

	public SqlQueryEngine getQueryEngine(String name) {
		SqlQueryEngine queryEngine = sqlFactory.getQueryEngine(name);
		if (queryEngine == null)
			throw new RuntimeException("Missing SqlQueryEngine " + name);
		return queryEngine;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setSqlFactory(SqlEngineFactory sqlFactory) {
		this.sqlFactory = sqlFactory;
	}
}
