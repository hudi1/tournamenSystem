package org.toursys.repository.dao.impl;

import org.sqlproc.engine.SqlCrudEngine;
import org.sqlproc.engine.SqlEngineFactory;
import org.sqlproc.engine.SqlSessionFactory;
import org.toursys.repository.dao.GroupsExtDao;
import org.toursys.repository.model.Tournament;

public class GroupsExtDaoImpl extends GroupsDaoImpl implements GroupsExtDao {

    public GroupsExtDaoImpl(SqlEngineFactory sqlEngineFactory) {
        super(sqlEngineFactory);
    }

    public GroupsExtDaoImpl(SqlEngineFactory sqlEngineFactory, SqlSessionFactory sqlSessionFactory) {
        super(sqlEngineFactory, sqlSessionFactory);
    }

    @Override
    public int deleteFinalGroups(Tournament tournament) {
        if (logger.isTraceEnabled()) {
            logger.trace("delete final groups: " + tournament);
        }
        SqlCrudEngine sqlDeleteEngineGroups = sqlEngineFactory.getCheckedCrudEngine("DELETE_FINAL_GROUPS");
        int count = sqlDeleteEngineGroups.delete(sqlSessionFactory.getSqlSession(), tournament);
        if (logger.isTraceEnabled()) {
            logger.trace("delete final groups result count: " + count);
        }
        return count;
    }

}
