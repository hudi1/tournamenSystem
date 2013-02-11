package org.toursys.repository.dao;

import java.util.List;

import org.toursys.repository.model.Season;
import org.toursys.repository.model.Tournament;

public interface TournamentDao {

    public Season createTournament(Season season, Tournament... tournaments);

    public Tournament updateTournament(Tournament tournament);

    public boolean deleteTournament(Tournament tournament);

    public Tournament getTournament(Tournament tournament);

    public List<Tournament> getListTournaments(Tournament tournament);

}
