package org.toursys.repository.form;

import java.util.ArrayList;
import java.util.List;

import org.sqlproc.engine.annotation.Pojo;
import org.toursys.repository.model.TableType;
import org.toursys.repository.model.Tournament;

@Pojo
public class TableForm {

    private Tournament tournament;
    private String name;
    private List<TableType> tableType;

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public TableForm(Tournament tournament, List<TableType> tableType) {
        this.tournament = tournament;
        this.tableType = tableType;
    }

    public TableForm(Tournament tournament, TableType tableType) {
        this.tableType = new ArrayList<TableType>();
        this.tableType.add(tableType);
        this.tournament = tournament;
    }

    public TableForm(String name, Tournament tournament, List<TableType> tableType) {
        this.name = name;
        this.tournament = tournament;
        this.tableType = tableType;
    }

    public TableForm(String name, Tournament tournament, TableType tableType) {
        this.name = name;
        this.tournament = tournament;
        this.tableType = new ArrayList<TableType>();
        this.tableType.add(tableType);
    }

    public TableForm(String name, Tournament tournament) {
        this.tournament = tournament;
        this.name = name;
    }

    public TableForm(Tournament tournament) {
        this.tournament = tournament;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TableType> getTableType() {
        return tableType;
    }

    public void setTableType(List<TableType> tableType) {
        this.tableType = tableType;
    }

}
