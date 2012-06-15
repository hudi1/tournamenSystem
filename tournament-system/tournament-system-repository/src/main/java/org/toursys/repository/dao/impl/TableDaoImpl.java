package org.toursys.repository.dao.impl;

import java.util.List;

import org.sqlproc.engine.SqlSession;
import org.toursys.repository.dao.TableDao;
import org.toursys.repository.form.TableForm;
import org.toursys.repository.model.Table;

public class TableDaoImpl extends BaseDaoImpl implements TableDao {

	@Override
	public void createTable(Table table) {
		SqlSession session = getSqlSession();
		getCrudEngine("INSERT_TABLE").insert(session, table);
	}

	@Override
	public void updateTable(Table table) {
		SqlSession session = getSqlSession();
		getCrudEngine("UPDATE_TABLE").update(session, table);
	}

	@Override
	public void deleteTable(Table table) {
		SqlSession session = getSqlSession();
		getCrudEngine("DELETE_TABLE").delete(session, table);
	}

	@Override
	public List<Table> findTable(TableForm tableForm) {
		SqlSession session = getSqlSession();
		return getQueryEngine("GET_TABLE").query(session, Table.class,
				tableForm);
	}

}
