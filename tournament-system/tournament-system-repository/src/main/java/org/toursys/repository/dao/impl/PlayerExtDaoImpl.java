package org.toursys.repository.dao.impl;

import java.util.List;

import org.sqlproc.engine.SqlEngineFactory;
import org.sqlproc.engine.SqlQueryEngine;
import org.sqlproc.engine.SqlSessionFactory;
import org.toursys.repository.dao.PlayerExtDao;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.Tournament;

public class PlayerExtDaoImpl extends PlayerDaoImpl implements PlayerExtDao {

    public PlayerExtDaoImpl(SqlEngineFactory sqlEngineFactory) {
        super(sqlEngineFactory);
    }

    public PlayerExtDaoImpl(SqlEngineFactory sqlEngineFactory, SqlSessionFactory sqlSessionFactory) {
        super(sqlEngineFactory, sqlSessionFactory);
    }

    public List<Player> listNotRegisteredPlayers(Tournament tournament) {
        if (logger.isTraceEnabled()) {
            logger.trace("list not registrated players: " + tournament);
        }
        SqlQueryEngine sqlEnginePlayer = sqlEngineFactory.getCheckedQueryEngine("GET_PLAYER_NOT_IN_TOURNAMENT");
        List<Player> playerList = sqlEnginePlayer.query(sqlSessionFactory.getSqlSession(), Player.class, tournament);

        if (logger.isTraceEnabled()) {
            logger.trace("list player size: " + ((playerList != null) ? playerList.size() : "null"));
        }
        return playerList;
    }

}
