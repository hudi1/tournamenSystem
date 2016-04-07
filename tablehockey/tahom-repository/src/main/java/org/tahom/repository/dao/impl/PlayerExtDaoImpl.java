package org.tahom.repository.dao.impl;

import java.util.List;

import org.sqlproc.engine.SqlEngineFactory;
import org.sqlproc.engine.SqlQueryEngine;
import org.sqlproc.engine.SqlSessionFactory;
import org.tahom.repository.dao.PlayerExtDao;
import org.tahom.repository.dao.impl.PlayerDaoImpl;
import org.tahom.repository.model.Player;
import org.tahom.repository.model.Tournament;
import org.tahom.repository.model.impl.StatisticForm;

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

    public List<Player> listPlayersGames(StatisticForm statisticForm) {
        if (logger.isTraceEnabled()) {
            logger.trace("list players games: " + statisticForm);
        }
        SqlQueryEngine sqlEnginePlayer = sqlEngineFactory.getCheckedQueryEngine("SELECT_PLAYER_GAME");
        List<Player> playerList = sqlEnginePlayer.query(sqlSessionFactory.getSqlSession(), Player.class, statisticForm);

        if (logger.isTraceEnabled()) {
            logger.trace("list player size: " + ((playerList != null) ? playerList.size() : "null"));
        }
        return playerList;
    }

}
