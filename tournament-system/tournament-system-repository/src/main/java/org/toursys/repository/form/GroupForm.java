package org.toursys.repository.form;

import java.util.ArrayList;
import java.util.List;

import org.sqlproc.engine.annotation.Pojo;
import org.toursys.repository.model.GroupType;
import org.toursys.repository.model.Tournament;

@Pojo
public class GroupForm {

    private Tournament tournament;
    private String name;
    private List<GroupType> tableType;

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public GroupForm(Tournament tournament, List<GroupType> tableType) {
        this.tournament = tournament;
        this.tableType = tableType;
    }

    public GroupForm(Tournament tournament, GroupType tableType) {
        this.tableType = new ArrayList<GroupType>();
        this.tableType.add(tableType);
        this.tournament = tournament;
    }

    public GroupForm(String name, Tournament tournament, List<GroupType> tableType) {
        this.name = name;
        this.tournament = tournament;
        this.tableType = tableType;
    }

    public GroupForm(String name, Tournament tournament, GroupType tableType) {
        this.name = name;
        this.tournament = tournament;
        this.tableType = new ArrayList<GroupType>();
        this.tableType.add(tableType);
    }

    public GroupForm(String name, Tournament tournament) {
        this.tournament = tournament;
        this.name = name;
    }

    public GroupForm(Tournament tournament) {
        this.tournament = tournament;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GroupType> getTableType() {
        return tableType;
    }

    public void setTableType(List<GroupType> tableType) {
        this.tableType = tableType;
    }

}
