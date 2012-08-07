package org.toursys.repository.dao.impl;

import java.util.List;

import org.sqlproc.engine.SqlSession;
import org.toursys.repository.dao.SeasonDao;
import org.toursys.repository.model.Season;

public class SeasonDaoImpl extends BaseDaoImpl implements SeasonDao {

    @Override
    public void createSeason(Season season) {
        SqlSession session = getSqlSession();
        getCrudEngine("INSERT_SEASON").insert(session, season);
    }

    @Override
    public void updateSeason(Season season) {
        SqlSession session = getSqlSession();
        getCrudEngine("UPDATE_SEASON").update(session, season);
    }

    @Override
    public void deleteSeason(Season season) {
        SqlSession session = getSqlSession();
        getCrudEngine("DELETE_SEASON").delete(session, season);
    }

    @Override
    public List<Season> getAllSeason() {
        SqlSession session = getSqlSession();
        return getQueryEngine("GET_ALL_SEASON").query(session, Season.class);
    }

}
