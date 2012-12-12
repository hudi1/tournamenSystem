package org.toursys.repository.dao.impl;

import java.util.List;

import org.sqlproc.engine.SqlSession;
import org.toursys.repository.dao.PlayerResultDao;
import org.toursys.repository.form.PlayerResultForm;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.PlayerResult;
import org.toursys.repository.model.Score;

public class PlayerResultDaoImpl extends BaseDaoImpl implements PlayerResultDao {

    @Override
    public PlayerResult createPlayerResult(Player player, Groups group) {
        if (player != null && group != null) {
            SqlSession session = getSqlSession();
            PlayerResult playerResult = new PlayerResult(0, group, player, new Score(0, 0));
            getCrudEngine("INSERT_PLAYER_RESULT").insert(session, playerResult);
            logger.info("insert player result: " + playerResult);
            return playerResult;
        }
        return null;
    }

    @Override
    public PlayerResult updatePlayerResult(PlayerResult playerResult) {
        SqlSession session = getSqlSession();
        int count = getCrudEngine("UPDATE_PLAYER_RESULT").update(session, playerResult);
        logger.info("update player result: " + playerResult);
        return (count > 0) ? playerResult : null;
    }

    @Override
    public boolean deletePlayerResult(PlayerResult playerResult) {
        SqlSession session = getSqlSession();
        int count = getCrudEngine("DELETE_PLAYER_RESULT").delete(session, playerResult);
        logger.info("delete player result: " + playerResult);
        return (count > 0);
    }

    @Override
    public PlayerResult getPlayerResult(PlayerResult playerResult) {
        SqlSession session = getSqlSession();
        PlayerResult p = getCrudEngine("GET_PLAYER_RESULT").get(session, PlayerResult.class, playerResult);
        logger.info("get playerResult: " + p);
        return p;
    }

    @Override
    public List<PlayerResult> findPlayerResult(PlayerResultForm playerResultForm) {
        SqlSession session = getSqlSession();
        logger.info("find player result: " + playerResultForm);
        return getQueryEngine("FIND_PLAYER_RESULT").query(session, PlayerResult.class, playerResultForm);
    }
}
