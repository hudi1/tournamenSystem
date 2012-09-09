package org.toursys.web;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.toursys.repository.model.Season;

public abstract class SeasonEditPage extends BasePage {

    private static final long serialVersionUID = 1L;

    private BasePage pageFrom;

    private class SeasonForm extends Form<Season> {

        private static final long serialVersionUID = 1L;

        public SeasonForm(final Season season) {
            super("seasonEditForm", new CompoundPropertyModel<Season>(season));
            setOutputMarkupId(true);
            add(new TextField<String>("name"));

            add(new Button("submit", new StringResourceModel("save", null)) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    if (season.getSeasonId() != 0) {
                        tournamentService.updateSeason(season);
                    } else {
                        tournamentService.createSeason(season);
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

    public SeasonEditPage(BasePage pageFrom, Season season) {
        this.pageFrom = pageFrom;
        add(new SeasonForm(season));
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new StringResourceModel("editSeason", null);
    }

}
