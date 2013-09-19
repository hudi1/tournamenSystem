package org.toursys.web;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.sqlproc.engine.SqlProcessorException;
import org.toursys.repository.model.Season;

@AuthorizeInstantiation(Roles.USER)
public class SeasonEditPage extends BasePage {

    private static final long serialVersionUID = 1L;

    public SeasonEditPage() {
        throw new RestartResponseAtInterceptPageException(new SeasonPage());
    }

    public SeasonEditPage(Season season) {
        createPage(season);
    }

    private void createPage(Season season) {
        add(new SeasonForm(season));
    }

    private class SeasonForm extends Form<Season> {

        private static final long serialVersionUID = 1L;

        public SeasonForm(final Season season) {
            super("seasonEditForm", new CompoundPropertyModel<Season>(season));
            setOutputMarkupId(true);
            add(new RequiredTextField<String>("name"));

            add(new Button("submit", new ResourceModel("save")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    try {
                        if (season.getId() != null) {
                            seasonService.updateSeason(season);
                        } else {
                            seasonService.createSeason(season);
                        }
                    } catch (SqlProcessorException e) {
                        logger.error("Error edit season: ", e);
                        error(getString("sql.db.exception"));
                        return;
                    }
                    setResponsePage(SeasonPage.class);
                }
            });

            add(new Button("back", new ResourceModel("back")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(SeasonPage.class);
                };
            }.setDefaultFormProcessing(false));
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("editSeason");
    }
}
