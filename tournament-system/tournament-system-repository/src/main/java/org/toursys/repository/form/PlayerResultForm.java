package org.toursys.repository.form;

import org.sqlproc.engine.annotation.Pojo;
import org.toursys.repository.model.Table;
import org.toursys.repository.model.Tournament;

@Pojo
public class PlayerResultForm {

    private Table tournamentTable;
    private Tournament tournament;

    public PlayerResultForm(Tournament tournament) {
        this.tournament = tournament;
    }

    public PlayerResultForm(Table tournamentTable) {
        this.tournamentTable = tournamentTable;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Table getTournamentTable() {
        return tournamentTable;
    }

    public void setTournamentTable(Table tournamentTable) {
        this.tournamentTable = tournamentTable;
    }

}
