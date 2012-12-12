package org.toursys.repository.dao.impl;

import java.util.List;

import org.sqlproc.engine.SqlSession;
import org.toursys.repository.dao.GameDao;
import org.toursys.repository.form.GameForm;
import org.toursys.repository.model.Game;
import org.toursys.repository.model.PlayerResult;

public class GameDaoImpl extends BaseDaoImpl implements GameDao {

    @Override
    public Game createGame(PlayerResult homePlayer, PlayerResult awayPlayer) {
        SqlSession session = getSqlSession();
        Game game = new Game(homePlayer, awayPlayer, false);
        int count = getCrudEngine("INSERT_GAME").insert(session, game);
        logger.info("insert game: " + count + ": " + game);
        return (count > 0 ? game : null);
    }

    @Override
    public Game updateGame(Game game) {
        SqlSession session = getSqlSession();
        int count = getCrudEngine("UPDATE_GAME").update(session, game);
        logger.info("update game: " + count + ": " + game);
        return (count > 0 ? game : null);
    }

    @Override
    public boolean deleteGame(Game game) {
        SqlSession session = getSqlSession();
        int count = getCrudEngine("DELETE_GAME").delete(session, game);
        logger.info("delete game: " + count + ": " + game);
        return (count > 0);
    }

    @Override
    public Game getGame(Game game) {
        SqlSession session = getSqlSession();
        Game g = getCrudEngine("GET_GAME").get(session, Game.class, game);
        logger.info("get game: " + g);
        return g;
    }

    @Override
    public List<Game> findGame(GameForm gameForm) {
        SqlSession session = getSqlSession();
        logger.info("find games");
        return getQueryEngine("FIND_GAMES").query(session, Game.class, gameForm);
    }

}
