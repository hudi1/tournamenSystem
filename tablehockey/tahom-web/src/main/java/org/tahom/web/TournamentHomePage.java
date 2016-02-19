package org.tahom.web;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.tahom.repository.model.Tournament;

public abstract class TournamentHomePage extends BasePage {

    private static final long serialVersionUID = 1L;
    protected Tournament tournament;

    public TournamentHomePage() {
        this(new PageParameters());
    }

    public TournamentHomePage(PageParameters parameters) {
        super(parameters);
        this.tournament = getTournament(parameters);
    }

}
