package org.toursys.repository.model;

import java.io.Serializable;

import org.sqlproc.engine.annotation.Pojo;

@Pojo
public class Table implements Serializable {

    private static final long serialVersionUID = 1L;

    private long tableId;

    private String name;

    private Integer numberOfHockey;

    private TableType tableType;

    private Integer indexOfFirstHockey;

    private long tournamentId;

    public Table() {
    }

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfHockey() {
        return numberOfHockey;
    }

    public void setNumberOfHockey(Integer numberOfHockey) {
        this.numberOfHockey = numberOfHockey;
    }

    public TableType getTableType() {
        return tableType;
    }

    public void setTableType(TableType tableType) {
        this.tableType = tableType;
    }

    public Integer getIndexOfFirstHockey() {
        return indexOfFirstHockey;
    }

    public void setIndexOfFirstHockey(Integer indexOfFirstHockey) {
        this.indexOfFirstHockey = indexOfFirstHockey;
    }

    public long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(long tournamentId) {
        this.tournamentId = tournamentId;
    }

}
