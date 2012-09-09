package org.toursys.web;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.toursys.repository.model.Tournament;

public abstract class TournamentEditPage extends BasePage {

    private static final long serialVersionUID = 1L;

    private BasePage pageFrom;

    private class TournamentForm extends Form<Tournament> {

        private static final long serialVersionUID = 1L;

        public TournamentForm(final Tournament tournament) {
            super("tournamentEditForm", new CompoundPropertyModel<Tournament>(tournament));
            setOutputMarkupId(true);
            add(new TextField<String>("name"));

            add(new Button("submit", new StringResourceModel("save", null)) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    if (tournament.getTournamentId() != 0) {
                        tournamentService.updateTournament(tournament);
                    } else {
                        tournamentService.createTournament(tournament);
                    }
                    setResponsePage(pageFrom);
                }

            });

            add(new Button("back", new StringResourceModel("back", null)) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(pageFrom);
                }

                @Override
                public void onError() {
                    getSession().cleanupFeedbackMessages();
                    setResponsePage(pageFrom);
                }
            });
        }

    }

    public TournamentEditPage(BasePage pageFrom, Tournament tournament) {
        this.pageFrom = pageFrom;
        add(new TournamentForm(tournament));
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return Model.of("Edit tournament");
    }

}
