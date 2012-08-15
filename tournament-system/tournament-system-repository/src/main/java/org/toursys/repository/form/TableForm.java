package org.toursys.repository.form;

import org.sqlproc.engine.annotation.Pojo;
import org.toursys.repository.model.Tournament;

@Pojo
public class TableForm {

    private Tournament tournament;
    private String name;

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public TableForm(String name, Tournament tournament) {
        this.tournament = tournament;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
