package org.toursys.repository.dao;

import java.util.List;

import org.toursys.repository.form.TournamentForm;
import org.toursys.repository.model.Tournament;

public interface TournamentDao {

    public void createTournament(Tournament tournament);

    public void updateTournament(Tournament tournament);

    public void deleteTournament(Tournament tournament);

    public List<Tournament> findTournament(TournamentForm tournamentForm);

}
