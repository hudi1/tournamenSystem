package org.toursys.repository.dao.impl;

import java.util.List;

import org.sqlproc.engine.SqlEngineFactory;
import org.sqlproc.engine.SqlQueryEngine;
import org.sqlproc.engine.SqlSessionFactory;
import org.toursys.repository.dao.ParticipantExtDao;
import org.toursys.repository.model.Participant;
import org.toursys.repository.model.Tournament;

public class ParticipantExtDaoImpl extends ParticipantDaoImpl implements ParticipantExtDao {

    public ParticipantExtDaoImpl(SqlEngineFactory sqlEngineFactory) {
        super(sqlEngineFactory);
    }

    public ParticipantExtDaoImpl(SqlEngineFactory sqlEngineFactory, SqlSessionFactory sqlSessionFactory) {
        super(sqlEngineFactory, sqlSessionFactory);
    }

    public List<Participant> listTournamentParticipants(Tournament tournament) {
        if (logger.isTraceEnabled()) {
            logger.trace("list registrated player results: " + tournament);
        }
        SqlQueryEngine sqlEnginePlayer = sqlEngineFactory.getCheckedQueryEngine("GET_PARTICIPANT_IN_TOURNAMENT");
        List<Participant> playerResultList = sqlEnginePlayer.query(sqlSessionFactory.getSqlSession(),
                Participant.class, tournament);

        if (logger.isTraceEnabled()) {
            logger.trace("list player size: " + ((playerResultList != null) ? playerResultList.size() : "null"));
        }
        return playerResultList;
    }

}
