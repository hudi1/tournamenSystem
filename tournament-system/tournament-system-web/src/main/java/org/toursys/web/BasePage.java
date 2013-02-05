package org.toursys.web;

import java.util.Locale;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.authentication.IAuthenticationStrategy;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
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

    // TODO treda domysliet historiu
    protected final String PREVISOUS_PAGE = "history.go(-1)";

    public BasePage() {
        addMyComponents();
        setVisibility();
    }

    public final IModel<String> getHeadingModel() {
        return headingModel;
    }

    private void addMyComponents() {
        headingModel = newHeadingModel();
        add(new ExternalLink("logo", Model.of(RequestUtils.toAbsolutePath(urlFor(getApplication().getHomePage(), null)
                .toString(), urlFor(HomePage.class, null).toString().toString()))));
        add(new Label("heading", headingModel));
        Component homePage = new BookmarkablePageLink<Void>("homePageMain", HomePage.class).add(new AttributeModifier(
                "class", new ActiveReplaceModel(this instanceof HomePage)));
        Component seasonPage = new BookmarkablePageLink<Void>("seasonPage", SeasonPage.class)
                .add(new AttributeModifier("class", new ActiveReplaceModel(this instanceof SeasonPage)));
        Component statisticPage = new BookmarkablePageLink<Void>("statisticPage", StatisticPage.class)
                .add(new AttributeModifier("class", new ActiveReplaceModel(this instanceof StatisticPage)));
        Component playerPage = new BookmarkablePageLink<Void>("playerPage", PlayerPage.class)
                .add(new AttributeModifier("class", new ActiveReplaceModel(this instanceof PlayerPage)));
        Component userPage = new BookmarkablePageLink<Void>("userPage", UserPage.class).add(new AttributeModifier(
                "class", new ActiveReplaceModel(this instanceof UserPage)));
        Component logoutPage = new BookmarkablePageLink<Void>("logoutPage", LogoutPage.class);
        Component loginPage = new BookmarkablePageLink<Void>("loginPage", LoginPage.class).add(new AttributeModifier(
                "class", new ActiveReplaceModel(this instanceof LoginPage)));

        add(homePage);
        add(seasonPage);
        add(statisticPage);
        add(playerPage);
        add(userPage);
        add(logoutPage);
        add(loginPage);
        if (!((TournamentAuthenticatedWebSession) getSession()).isSignedIn()) {
            seasonPage.setVisible(false);
            statisticPage.setVisible(false);
            playerPage.setVisible(false);
            logoutPage.setVisible(false);
        } else {
            loginPage.setVisible(false);
        }

        if (!((TournamentAuthenticatedWebSession) getSession()).getRoles().contains(Roles.ADMIN)) {
            userPage.setVisible(false);
        }

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

    protected void setVisibility() {
        setVisible(true);
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

    @Override
    protected void onBeforeRender() {
        if (((TournamentAuthenticatedWebSession) getSession()).isSignedIn() == false) {
            IAuthenticationStrategy authenticationStrategy = getApplication().getSecuritySettings()
                    .getAuthenticationStrategy();
            String[] data = authenticationStrategy.load();
            if ((data != null) && (data.length > 1)) {
                if (((TournamentAuthenticatedWebSession) getSession()).signIn(data[0], data[1])) {
                    if (!continueToOriginalDestination()) {
                        throw new RestartResponseException(getSession().getPageFactory().newPage(
                                getApplication().getHomePage()));
                    }
                }
            }
        }
        super.onBeforeRender();
    }
}
