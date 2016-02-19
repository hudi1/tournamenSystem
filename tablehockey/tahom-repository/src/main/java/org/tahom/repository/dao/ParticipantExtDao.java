package org.tahom.repository.dao;

import java.util.List;

import org.tahom.repository.dao.ParticipantDao;
import org.tahom.repository.model.Participant;
import org.tahom.repository.model.Tournament;

public interface ParticipantExtDao extends ParticipantDao {

    public List<Participant> listTournamentParticipants(Tournament tournament);

}
