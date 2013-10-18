package org.toursys.repository.dao;

import java.util.List;

import org.toursys.repository.model.Participant;
import org.toursys.repository.model.Tournament;

public interface ParticipantExtDao extends ParticipantDao {

    public List<Participant> listTournamentParticipants(Tournament tournament);

}
