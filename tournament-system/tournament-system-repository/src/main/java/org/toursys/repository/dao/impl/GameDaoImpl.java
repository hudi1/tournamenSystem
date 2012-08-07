package org.toursys.repository.dao.impl;

import java.util.List;

import org.sqlproc.engine.SqlSession;
import org.toursys.repository.dao.GameDao;
import org.toursys.repository.form.GameForm;
import org.toursys.repository.model.Game;

public class GameDaoImpl extends BaseDaoImpl implements GameDao {

    @Override
    public void createGame(Game game) {
        SqlSession session = getSqlSession();
        getCrudEngine("INSERT_GAME").insert(session, game);
    }

    @Override
    public void updateGame(Game game) {
        SqlSession session = getSqlSession();
        getCrudEngine("UPDATE_GAME").update(session, game);
    }

    @Override
    public void deleteGame(Game game) {
        SqlSession session = getSqlSession();
        getCrudEngine("DELETE_GAME").delete(session, game);
    }

    @Override
    public List<Game> findGame(GameForm gameForm) {
        SqlSession session = getSqlSession();
        return getQueryEngine("GET_GAME").query(session, Game.class, gameForm);
    }

}
