package org.toursys.web;

import java.util.Locale;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public abstract class BasePage extends WebPage {

    private static final long serialVersionUID = 1L;

    private Locale locale = new Locale("cs_CZ");

    private IModel<String> headingModel = new Model<String>();

    abstract protected IModel<String> newHeadingModel();

    private class ActiveReplaceModel extends AbstractReadOnlyModel<String> {

        private static final long serialVersionUID = 1L;

        private boolean active;

        public ActiveReplaceModel(boolean active) {
            this.active = active;
        }

        @Override
        public String getObject() {
            return active ? "active" : "";
        }

    }

    public final IModel<String> getHeadingModel() {
        return headingModel;
    }

    private void addMyComponents() {
        headingModel = newHeadingModel();
        add(new Label("heading", headingModel));
        add(new BookmarkablePageLink<Void>("homePageMain", HomePage.class).add(new AttributeModifier("class",
                new ActiveReplaceModel(this instanceof HomePage))));
        add(new BookmarkablePageLink<Void>("seasonPage", SeasonPage.class).add(new AttributeModifier("class",
                new ActiveReplaceModel(this instanceof SeasonPage))));
        add(new BookmarkablePageLink<Void>("publicPage", PublicPage.class).add(new AttributeModifier("class",
                new ActiveReplaceModel(this instanceof PublicPage))));
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    public BasePage() {
        super();
        addMyComponents();
    }

    /*public BasePage(IModel<?> model) {
        super(model);
        addMyComponents();
    }*/

    public BasePage(PageParameters parameters) {
        super(parameters);
        addMyComponents();
    }

}
