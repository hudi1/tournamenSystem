package org.toursys.repository.dao.impl;

import java.util.List;

import org.sqlproc.engine.SqlSession;
import org.toursys.repository.dao.PlayOffResultDao;
import org.toursys.repository.model.PlayOffGame;
import org.toursys.repository.model.PlayOffResult;

public class PlayOffResultDaoImpl extends BaseDaoImpl implements PlayOffResultDao {

    @Override
    public PlayOffResult createPlayOffResult(PlayOffGame playOffGame) {
        SqlSession session = getSqlSession();
        PlayOffResult playOffResult = new PlayOffResult(false, playOffGame);
        int count = getCrudEngine("INSERT_PLAY_OFF_RESULT").insert(session, playOffResult);
        logger.info("insert playOffGame: " + count + ": " + playOffResult);
        playOffGame.getPlayOffResults().add(playOffResult);
        return (count > 0 ? playOffResult : null);
    }

    @Override
    public PlayOffResult updatePlayOffResult(PlayOffResult playOffResult) {
        SqlSession session = getSqlSession();
        int count = getCrudEngine("UPDATE_PLAY_OFF_RESULT").update(session, playOffResult);
        logger.info("update playOffResult: " + count + ": " + playOffResult);
        return (count > 0 ? playOffResult : null);
    }

    @Override
    public boolean deletePlayOffResult(PlayOffResult playOffResult) {
        SqlSession session = getSqlSession();
        int count = getCrudEngine("DELETE_PLAY_OFF_RESULT").delete(session, playOffResult);
        logger.info("delete playOffResult: " + count + ": " + playOffResult);
        return (count > 0);
    }

    @Override
    public PlayOffResult getPlayOffResult(PlayOffResult playOffResult) {
        SqlSession session = getSqlSession();
        PlayOffResult p = getCrudEngine("GET_PLAY_OFF_RESULT").get(session, PlayOffResult.class, playOffResult);
        logger.info("get playOffResult: " + p);
        return p;
    }

    @Override
    public List<PlayOffResult> findPlayOffResult(PlayOffResult playOffResult) {
        SqlSession session = getSqlSession();
        logger.info("select playOffResult");
        return getQueryEngine("SELECT_PLAY_OFF_GAME").query(session, PlayOffResult.class, playOffResult);
    }

}
