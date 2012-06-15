package org.toursys.repository.form;

import org.sqlproc.engine.annotation.Pojo;
import org.toursys.repository.model.Tournament;

@Pojo
public class TableForm {

	private Tournament tournament;

	public Tournament getTournament() {
		return tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	public TableForm(Tournament tournament) {
		this.tournament = tournament;
	}

}
