package org.toursys.web;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.toursys.repository.model.Season;

public abstract class SeasonEditor extends BasePage {

    private static final long serialVersionUID = 1L;

    private BasePage pageFrom;

    private class SeasonForm extends Form<Season> {

        private static final long serialVersionUID = 1L;

        public SeasonForm(IModel<Season> model) {
            super("seasonForm", new CompoundPropertyModel<Season>(model));
            setOutputMarkupId(true);
            add(new TextField<String>("street"));
            add(new TextField<Integer>("postNum"));

            add(new Button("submit") {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    onSeasonChange(SeasonForm.this.getModelObject());
                    setResponsePage(pageFrom);
                }

            });

            add(new Button("back") {

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

    abstract protected void onSeasonChange(Season season);

    public SeasonEditor(BasePage pageFrom, IModel<Season> model) {
        super(model);
        this.pageFrom = pageFrom;
        add(new SeasonForm(model));
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return Model.of("Examples - Address Editor");
    }

}
