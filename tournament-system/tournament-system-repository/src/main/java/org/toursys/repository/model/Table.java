package org.toursys.repository.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.sqlproc.engine.annotation.Pojo;

@Pojo
public class Table implements Serializable {

	private static final long serialVersionUID = 1L;

	private long idTable;

	private String name;

	private Integer numberOfHockey;

	private TableType tableType;

	private Integer numberOfFirstHokcey;

	private Tournament tournamen;

	private List<PlayerResult> playerResults = new ArrayList<PlayerResult>();

	public Table() {
	}

	public List<PlayerResult> getPlayerResults() {
		return playerResults;
	}

	public void setPlayerResults(List<PlayerResult> playerResults) {
		this.playerResults = playerResults;
	}

	public long getIdTable() {
		return idTable;
	}

	public void setIdTable(long idTable) {
		this.idTable = idTable;
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

	public Integer getNumberOfFirstHokcey() {
		return numberOfFirstHokcey;
	}

	public void setNumberOfFirstHokcey(Integer numberOfFirstHokcey) {
		this.numberOfFirstHokcey = numberOfFirstHokcey;
	}

	public Tournament getTournamen() {
		return tournamen;
	}

	public void setTournamen(Tournament tournamen) {
		this.tournamen = tournamen;
	}
}
