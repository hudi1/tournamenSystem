package org.toursys.repository.dao.impl;

import java.util.List;

import org.sqlproc.engine.SqlSession;
import org.toursys.repository.dao.PlayerDao;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.Tournament;

public class PlayerDaoImpl extends BaseDaoImpl implements PlayerDao {

    @Override
    public Player createPlayer(Player player) {
        SqlSession session = getSqlSession();
        int count = getCrudEngine("INSERT_PLAYER").insert(session, player);
        logger.info("insert player: " + count + ": " + player);
        return (count > 0 ? player : null);
    }

    @Override
    public Player updatePlayer(Player player) {
        SqlSession session = getSqlSession();
        int count = getCrudEngine("UPDATE_PLAYER").update(session, player);
        logger.info("update player: " + count + ": " + player);
        return (count > 0 ? player : null);
    }

    @Override
    public boolean deletePlayer(Player player) {
        SqlSession session = getSqlSession();
        int count = getCrudEngine("DELETE_PLAYER").delete(session, player);
        logger.info("delete player: " + count + ": " + player);
        return (count > 0);
    }

    @Override
    public Player getPlayer(Player player) {
        SqlSession session = getSqlSession();
        Player p = getCrudEngine("GET_PLAYER").get(session, Player.class, player);
        logger.info("get player: " + p);
        return p;
    }

    @Override
    public List<Player> getListPlayers(Player player) {
        SqlSession session = getSqlSession();
        logger.info("select players " + player);
        return getQueryEngine("SELECT_PLAYER").query(session, Player.class, player);
    }

    @Override
    public List<Player> getNotRegistratedPlayers(Tournament tournament) {
        SqlSession session = getSqlSession();
        logger.info("get player not in tournament");
        return getQueryEngine("GET_PLAYER_NOT_IN_TOURNAMENT").query(session, Player.class, tournament);
    }
}
