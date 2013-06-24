package org.toursys.repository.dao.impl;

import java.util.List;

import org.sqlproc.engine.SqlEngineFactory;
import org.sqlproc.engine.SqlQueryEngine;
import org.sqlproc.engine.SqlSessionFactory;
import org.toursys.repository.dao.PlayerResultExtDao;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Tournament;

public class PlayerResultExtDaoImpl extends PlayerResultDaoImpl implements PlayerResultExtDao {

    public PlayerResultExtDaoImpl(SqlEngineFactory sqlEngineFactory) {
        super(sqlEngineFactory);
    }

    public PlayerResultExtDaoImpl(SqlEngineFactory sqlEngineFactory, SqlSessionFactory sqlSessionFactory) {
        super(sqlEngineFactory, sqlSessionFactory);
    }

    public List<PlayerResult> listRegistratedPlayerResult(Tournament tournament) {
        if (logger.isTraceEnabled()) {
            logger.trace("list registrated player results: " + tournament);
        }
        SqlQueryEngine sqlEnginePlayer = sqlEngineFactory.getCheckedQueryEngine("GET_PLAYER_RESULT_IN_TOURNAMENT");
        List<PlayerResult> playerResultList = sqlEnginePlayer.query(sqlSessionFactory.getSqlSession(),
                PlayerResult.class, tournament);

        if (logger.isTraceEnabled()) {
            logger.trace("list player size: " + ((playerResultList != null) ? playerResultList.size() : "null"));
        }
        return playerResultList;
    }

}
