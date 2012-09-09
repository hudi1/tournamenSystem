package org.toursys.repository.form;

import org.sqlproc.engine.annotation.Pojo;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.Table;
import org.toursys.repository.model.Tournament;

@Pojo
public class PlayerResultForm {

    private Table tournamentTable;
    private Tournament tournament;
    private Player player;
    private boolean table = false;;

    public PlayerResultForm(Tournament tournament) {
        this.tournament = tournament;
    }

    public PlayerResultForm(Player player) {
        this.player = player;
    }

    public PlayerResultForm(Table tournamentTable) {
        this.tournamentTable = tournamentTable;
    }

    public PlayerResultForm(Tournament tournament, Table tournamentTable) {
        if (tournamentTable.getTableId() != 0) {
            table = true;
        }
        this.tournament = tournament;
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
        if (tournamentTable.getTableId() != 0) {
            table = true;
        }
        this.tournamentTable = tournamentTable;
    }

    public boolean isTable() {
        return table;
    }

    public void setTable(boolean table) {
        this.table = table;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
