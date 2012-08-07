package org.toursys.repository.form;

import org.sqlproc.engine.annotation.Pojo;
import org.toursys.repository.model.Table;

@Pojo
public class PlayerResultForm {

    private Table tournamentTable;

    public PlayerResultForm(Table tournamentTable) {
        this.tournamentTable = tournamentTable;
    }

    public Table getTournamentTable() {
        return tournamentTable;
    }

    public void setTournamentTable(Table tournamentTable) {
        this.tournamentTable = tournamentTable;
    }

}
