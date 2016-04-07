package org.tahom.repository.model.impl;

import java.util.Date;

import org.sqlproc.engine.annotation.Pojo;
import org.tahom.repository.model.IthfTournament;

@Pojo
public class IthfTournamentForm extends IthfTournament {

	private static final long serialVersionUID = 1L;

	private Date startDate;

	private Date endDate;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "IthfTournamentForm [startDate=" + startDate + ", endDate=" + endDate + "]";
	}

}
