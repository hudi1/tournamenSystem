package org.toursys.web;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.sqlproc.engine.SqlProcessorException;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Tournament;

public class TournamentEditPage extends BasePage {

    private static final long serialVersionUID = 1L;

    private Season season;

    public TournamentEditPage() {
        throw new RestartResponseAtInterceptPageException(new SeasonPage());
    }

    public TournamentEditPage(Season season) {
        this(season, new Tournament()._setFinalPromoting(6)._setLowerPromoting(5)._setWinPoints(2)._setPlayOffA(16)
                ._setPlayOffLower(8)._setMinPlayersInGroup(5));
    }

    public TournamentEditPage(Season season, Tournament tournament) {
        this.season = season;
        createPage(tournament);
    }

    protected void createPage(Tournament tournament) {
        add(new TournamentForm(tournament));
    }

    private class TournamentForm extends Form<Tournament> {

        private static final long serialVersionUID = 1L;

        public TournamentForm(final Tournament tournament) {
            super("tournamentEditForm", new CompoundPropertyModel<Tournament>(tournament));
            setOutputMarkupId(true);
            add(new RequiredTextField<String>("name"));

            add(new Button("submit", new ResourceModel("save")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    try {
                        if (tournament.getId() != null) {
                            tournamentService.updateTournament(tournament);
                        } else {
                            tournamentService.createTournament(season, tournament);
                        }
                    } catch (SqlProcessorException e) {
                        logger.error("Error edit tournament: ", e);
                        error(getString("sql.db.exception"));
                    }
                    setResponsePage(new TournamentPage(season));
                }

            });

            add(new AjaxButton("back", new ResourceModel("back")) {

                private static final long serialVersionUID = 1L;

                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    target.appendJavaScript(PREVISOUS_PAGE);
                };
            }.setDefaultFormProcessing(false));
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("editTournament");
    }
}
