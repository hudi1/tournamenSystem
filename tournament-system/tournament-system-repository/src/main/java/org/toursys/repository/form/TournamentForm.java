package org.toursys.repository.form;

import org.sqlproc.engine.annotation.Pojo;
import org.toursys.repository.model.Season;

@Pojo
public class TournamentForm {

	private Season season;

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	public TournamentForm(Season season) {
		this.season = season;
	}

}
