package org.tahom.repository.dao.impl;

import org.sqlproc.engine.SqlCrudEngine;
import org.sqlproc.engine.SqlEngineFactory;
import org.sqlproc.engine.SqlSessionFactory;
import org.tahom.repository.dao.GameExtDao;
import org.tahom.repository.dao.impl.GameDaoImpl;
import org.tahom.repository.model.Game;

public class GameExtDaoImpl extends GameDaoImpl implements GameExtDao {

    public GameExtDaoImpl(SqlEngineFactory sqlEngineFactory) {
        super(sqlEngineFactory);
    }

    public GameExtDaoImpl(SqlEngineFactory sqlEngineFactory, SqlSessionFactory sqlSessionFactory) {
        super(sqlEngineFactory, sqlSessionFactory);
    }

    @Override
    public int updateOppositeGame(Game game) {
        if (logger.isTraceEnabled()) {
            logger.trace("updateOppositeGame game: " + game);
        }
        SqlCrudEngine sqlUpdateEngineGame = sqlEngineFactory.getCheckedCrudEngine("UPDATE_OPPOSITE_GAME");
        int count = sqlUpdateEngineGame.update(sqlSessionFactory.getSqlSession(), game);
        if (logger.isTraceEnabled()) {
            logger.trace("updateOppositeGame game result count: " + count);
        }
        return count;
    }

}
