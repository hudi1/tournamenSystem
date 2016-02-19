package org.tahom.repository.model.wch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.tahom.repository.model.wch.season.WChRecord;

public class WChTableRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<WChRecord> records;
	private List<String> wchTournamentHeaders;
	private List<String> wchSeasonHeaders;

	public List<WChRecord> getRecords() {
		return records;
	}

	public void setRecords(List<WChRecord> records) {
		this.records = records;
	}

	public List<String> getWchTournamentHeaders() {
		if (wchTournamentHeaders == null) {
			wchTournamentHeaders = new ArrayList<String>();
		}
		return wchTournamentHeaders;
	}

	public List<String> getWchSeasonHeaders() {
		if (wchSeasonHeaders == null) {
			wchSeasonHeaders = new ArrayList<String>();
		}
		return wchSeasonHeaders;
	}

}
