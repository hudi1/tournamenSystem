package org.toursys.repository.form;

import org.sqlproc.engine.annotation.Pojo;
import org.toursys.repository.model.Tournament;

@Pojo
public class PlayerForm {

    private Tournament tournament;

    public PlayerForm(Tournament tournament) {
        this.tournament = tournament;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }
}
