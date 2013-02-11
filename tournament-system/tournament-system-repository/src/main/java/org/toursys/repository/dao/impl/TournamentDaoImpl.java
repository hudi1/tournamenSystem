package org.toursys.repository.dao.impl;

import java.util.List;

import org.sqlproc.engine.SqlSession;
import org.toursys.repository.dao.TournamentDao;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Tournament;

public class TournamentDaoImpl extends BaseDaoImpl implements TournamentDao {

    @Override
    public Season createTournament(Season season, Tournament... tournaments) {
        if (tournaments != null) {
            SqlSession session = getSqlSession();
            for (Tournament tournament : tournaments) {
                tournament.setSeason(season);
                int count = getCrudEngine("INSERT_TOURNAMENT").insert(session, tournament);
                logger.info("insert tournament: " + count + ": " + tournament);
                if (count > 0)
                    season.getTournaments().add(tournament);
            }
        }
        return season;
    }

    @Override
    public Tournament updateTournament(Tournament tournament) {
        SqlSession session = getSqlSession();
        int count = getCrudEngine("UPDATE_TOURNAMENT").update(session, tournament);
        logger.info("update tournament: " + count + ": " + tournament);
        return (count > 0) ? tournament : null;
    }

    @Override
    public boolean deleteTournament(Tournament tournament) {
        SqlSession session = getSqlSession();
        int count = getCrudEngine("DELETE_TOURNAMENT").delete(session, tournament);
        logger.info("delete tournament: " + count + ": " + tournament);
        return (count > 0);
    }

    @Override
    public Tournament getTournament(Tournament tournament) {
        SqlSession session = getSqlSession();
        Tournament t = getCrudEngine("GET_TOURNAMENT").get(session, Tournament.class, tournament);
        logger.info("get playerResult: " + t);
        return t;
    }

    @Override
    public List<Tournament> getListTournaments(Tournament tournament) {
        SqlSession session = getSqlSession();
        logger.info("get tournament: " + tournament);
        return getQueryEngine("SELECT_TOURNAMENT").query(session, Tournament.class, tournament);
    }
}