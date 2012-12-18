package org.toursys.web;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
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
        this.season = season;
        createPage(new Tournament());
    }

    public TournamentEditPage(Tournament tournament) {
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

            add(new Button("submit", new StringResourceModel("save", null)) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    try {
                        if (tournament.getId() != 0) {
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

            add(new AjaxLink<Void>("back") {

                private static final long serialVersionUID = 1L;

                @Override
                public void onClick(AjaxRequestTarget target) {
                    target.appendJavaScript(PREVISOUS_PAGE);
                }
            });
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("editTournament");
    }
}
