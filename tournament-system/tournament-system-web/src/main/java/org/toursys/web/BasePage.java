package org.toursys.web;

import java.util.Locale;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.RequestUtils;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toursys.processor.service.TournamentService;

public abstract class BasePage extends WebPage {

    private static final long serialVersionUID = 1L;

    @SpringBean(name = "tournamentService")
    protected TournamentService tournamentService;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private IModel<String> headingModel = new Model<String>();

    abstract protected IModel<String> newHeadingModel();

    protected final String PREVISOUS_PAGE = "history.go(-1)";

    public BasePage() {
        addMyComponents();
    }

    public final IModel<String> getHeadingModel() {
        return headingModel;
    }

    private void addMyComponents() {
        headingModel = newHeadingModel();
        add(new ExternalLink("heading", Model.of(RequestUtils.toAbsolutePath(
                urlFor(getApplication().getHomePage(), null).toString(), urlFor(HomePage.class, null).toString()
                        .toString())), headingModel));
        add(new BookmarkablePageLink<Void>("homePageMain", HomePage.class).add(new AttributeModifier("class",
                new ActiveReplaceModel(this instanceof HomePage))));
        add(new BookmarkablePageLink<Void>("seasonPage", SeasonPage.class).add(new AttributeModifier("class",
                new ActiveReplaceModel(this instanceof SeasonPage))));
        add(new BookmarkablePageLink<Void>("statisticPage", StatisticPage.class).add(new AttributeModifier("class",
                new ActiveReplaceModel(this instanceof StatisticPage))));
        add(new BookmarkablePageLink<Void>("playerPage", PlayerPage.class).add(new AttributeModifier("class",
                new ActiveReplaceModel(this instanceof PlayerPage))));

        add(new Link<Void>("goSk") {

            private static final long serialVersionUID = 1L;

            @Override
            public void onClick() {
                getSession().setLocale(new Locale("sk", "SK"));
            }
        });

        add(new Link<Void>("goEn") {

            private static final long serialVersionUID = 1L;

            @Override
            public void onClick() {
                getSession().setLocale(Locale.US);
            }
        });
        add(new FeedbackPanel("feedbackPanel"));

    }

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

}
