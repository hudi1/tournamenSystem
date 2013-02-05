package org.toursys.repository.dao.impl;

import java.util.List;

import org.sqlproc.engine.SqlSession;
import org.toursys.repository.dao.SeasonDao;
import org.toursys.repository.model.Season;

public class SeasonDaoImpl extends BaseDaoImpl implements SeasonDao {

    @Override
    public Season createSeason(Season season) {
        SqlSession session = getSqlSession();
        int count = getCrudEngine("INSERT_SEASON").insert(session, season);
        logger.info("insert season: " + count + ": " + season);
        return (count > 0 ? season : null);
    }

    @Override
    public Season updateSeason(Season season) {
        SqlSession session = getSqlSession();
        int count = getCrudEngine("UPDATE_SEASON").update(session, season);
        logger.info("update season: " + count + ": " + season);
        return (count > 0 ? season : null);
    }

    @Override
    public boolean deleteSeason(Season season) {
        SqlSession session = getSqlSession();
        int count = getCrudEngine("DELETE_SEASON").delete(session, season);
        logger.info("delete season: " + count + ": " + season);
        return (count > 0);
    }

    @Override
    public Season getSeason(Season season) {
        SqlSession session = getSqlSession();
        Season s = getCrudEngine("GET_SEASON").get(session, Season.class, season);
        logger.info("get season: " + s);
        return s;
    }

    @Override
    public List<Season> getListSeason(Season season) {
        SqlSession session = getSqlSession();
        logger.info("find season");
        return getQueryEngine("SELECT_SEASON").query(session, Season.class, season);
    }

    @Override
    public List<Season> getAllSeasons() {
        SqlSession session = getSqlSession();
        logger.info("get all seasons");
        return getQueryEngine("GET_ALL_SEASONS").query(session, Season.class);
    }

}
