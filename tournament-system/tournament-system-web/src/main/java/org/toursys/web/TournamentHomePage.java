package org.toursys.web;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.toursys.repository.model.Tournament;

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

    @Override
    protected IModel<String> newHeadingModel() {
        return Model.of(tournament.getName());
    }
}
