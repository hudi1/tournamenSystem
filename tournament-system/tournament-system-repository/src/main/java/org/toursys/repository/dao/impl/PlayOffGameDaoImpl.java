package org.toursys.repository.dao.impl;

import java.util.List;

import org.sqlproc.engine.SqlSession;
import org.toursys.repository.dao.PlayOffGameDao;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.Player;

public class PlayOffGameDaoImpl extends BaseDaoImpl implements PlayOffGameDao {

    @Override
    public PlayOffGame createPlayOffGame(Player homePlayer, Player awayPlayer, Groups Group, int position) {
        SqlSession session = getSqlSession();
        PlayOffGame playOffGame = new PlayOffGame(Group, position)._setAwayPlayer(awayPlayer)
                ._setHomePlayer(homePlayer);
        int count = getCrudEngine("INSERT_PLAY_OFF_GAME").insert(session, playOffGame);
        logger.info("insert playOffGame: " + count + ": " + playOffGame);
        return (count > 0 ? playOffGame : null);
    }

    @Override
    public PlayOffGame updatePlayOffGame(PlayOffGame playOffGame) {
        SqlSession session = getSqlSession();
        int count = getCrudEngine("UPDATE_PLAY_OFF_GAME").update(session, playOffGame);
        logger.info("update playOffGame: " + count + ": " + playOffGame);
        return (count > 0 ? playOffGame : null);
    }

    @Override
    public boolean deletePlayOffGame(PlayOffGame playOffGame) {
        SqlSession session = getSqlSession();
        int count = getCrudEngine("DELETE_PLAY_OFF_GAME").delete(session, playOffGame);
        logger.info("delete playOffGame: " + count + ": " + playOffGame);
        return (count > 0);
    }

    @Override
    public PlayOffGame getPlayOffGame(PlayOffGame playOffGame) {
        SqlSession session = getSqlSession();
        PlayOffGame g = getCrudEngine("GET_PLAY_OFF_GAME").get(session, PlayOffGame.class, playOffGame);
        logger.info("get playOffGame: " + g);
        return g;
    }

    @Override
    public List<PlayOffGame> findPlayOffGame(PlayOffGame playOffGame) {
        SqlSession session = getSqlSession();
        logger.info("select playOffGame");
        return getQueryEngine("SELECT_PLAY_OFF_GAME").query(session, PlayOffGame.class, playOffGame);
    }

}
