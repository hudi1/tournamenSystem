package org.toursys.web;

import java.util.Locale;

import org.apache.commons.lang.BooleanUtils;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.authentication.IAuthenticationStrategy;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.protocol.http.RequestUtils;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.toursys.processor.TournamentPageParameter;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.model.User;
import org.toursys.web.link.BookmarkableModelPageLink;
import org.toursys.web.session.TournamentAuthenticatedWebSession;

public abstract class BasePage extends AbstractBasePage implements TournamentPageParameter {

    private static final long serialVersionUID = 1L;

    protected IModel<String> newHeadingModel() {
        return new ResourceModel(this.getClass().getSimpleName());
    }

    protected FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel");

    protected static final int ITEM_PER_PAGE = 10;

    public BasePage() {
        this(null);
    }

    public BasePage(PageParameters parameters) {
        super(parameters);
        addMyComponents();
    }

    private void addMyComponents() {

        PageParameters groupPageParameter = new PageParameters(getPageParameters());
        if (this instanceof SchedulePage) {
            groupPageParameter.set(UPDATE, true);
        }

        add(new ExternalLink("logo", Model.of(RequestUtils.toAbsolutePath(urlFor(getApplication().getHomePage(), null)
                .toString(), urlFor(HomePage.class, null).toString().toString()))));

        Component homePage = new BookmarkableModelPageLink<Void>("homePage", HomePage.class, this instanceof HomePage);
        Component tournamentPage = new BookmarkableModelPageLink<Void>("tournamentPage", TournamentPage.class,
                this instanceof HomePage);
        Component seasonPage = new BookmarkableModelPageLink<Void>("seasonPage", SeasonPage.class,
                this instanceof SeasonPage);
        Component statisticPage = new BookmarkableModelPageLink<Void>("statisticPage", StatisticPage.class,
                this instanceof StatisticPage);
        Component playerPage = new BookmarkableModelPageLink<Void>("playerPage", PlayerPage.class,
                this instanceof PlayerPage);
        Component userPage = new BookmarkableModelPageLink<Void>("userPage", UserPage.class, this instanceof UserPage);
        Component logoutPage = new BookmarkableModelPageLink<Void>("logoutPage", LogoutPage.class);
        Component loginPage = new BookmarkableModelPageLink<Void>("loginPage", LoginPage.class,
                this instanceof LoginPage);
        Component registrationPage = new BookmarkableModelPageLink<Void>("registrationPage", RegistrationPage.class,
                this instanceof RegistrationPage);
        Component groupPage = new BookmarkableModelPageLink<Void>("groupPage", GroupPage.class, groupPageParameter,
                this instanceof RegistrationPage);
        Component playOffPage = new BookmarkableModelPageLink<Void>("playOffPage", PlayOffPage.class,
                getPageParameters(), this instanceof PlayOffPage);
        Component finalRankingPage = new BookmarkableModelPageLink<Void>("finalRankingPage", FinalStandingsPage.class,
                this instanceof FinalStandingsPage);
        Component publicTournamentPage = new BookmarkableModelPageLink<Void>("publicTournamentPage",
                PublicTournamentPage.class, groupPageParameter, this instanceof PublicTournamentPage);

        add(homePage);
        add(seasonPage);
        add(tournamentPage);
        add(statisticPage);
        add(logoutPage);
        add(loginPage);
        add(userPage);
        add(playerPage);
        add(registrationPage);
        add(groupPage);
        add(playOffPage);
        add(finalRankingPage);
        add(publicTournamentPage);

        homePage.setVisible(true);

        if (this instanceof TournamentHomePage) {
            registrationPage.setVisible(true);
            groupPage.setVisible(true);
            playOffPage.setVisible(true);
            finalRankingPage.setVisible(true);
            logoutPage.setVisible(true);
        } else {
            publicTournamentPage.setVisible(true);
            if (getTournamentSession().isSignedIn()) {
                seasonPage.setVisible(true);
                tournamentPage.setVisible(true);
                statisticPage.setVisible(true);
                logoutPage.setVisible(true);
            } else {
                loginPage.setVisible(true);
            }

            if (getTournamentSession().getRoles().contains(Roles.ADMIN)) {
                userPage.setVisible(true);
            }
        }

        add(new Label("language", new ResourceModel("language")));
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

        add(new Label("user", new ResourceModel("user")));
        Link<User> userProfileLink = new Link<User>("userProfile", new AbstractReadOnlyModel<User>() {

            private static final long serialVersionUID = 1L;

            @Override
            public User getObject() {
                if (getSession() == null)
                    return null;
                if (getSession() instanceof TournamentAuthenticatedWebSession) {
                    return getTournamentSession().getUser();
                }
                return null;
            }

        }) {

            private static final long serialVersionUID = 1L;

            @Override
            public void onClick() {
                User user = getTournamentSession().getUser();
                setResponsePage(new UserEditPage(user, false));
            }

            @Override
            public boolean isEnabled() {
                return getModelObject() != null;
            }

        };
        userProfileLink.add(new Label("username", new AbstractReadOnlyModel<String>() {

            private static final long serialVersionUID = 1L;

            @Override
            public String getObject() {
                if (getSession() instanceof TournamentAuthenticatedWebSession) {
                    User user = getTournamentSession().getUser();
                    return user == null ? getString("guest") : user.getUserName();
                }
                return null;
            }

        }));
        add(userProfileLink);

        add(feedbackPanel.setOutputMarkupId(true));

    }

    protected Tournament getTournament(PageParameters pageParameters) {
        return getTournament(pageParameters, false);
    }

    protected Tournament getTournament(PageParameters pageParameters, boolean ignoreSession) {
        Tournament tournament = null;
        Integer tournamentId = null;

        if (!ignoreSession) {
            tournament = getTournamentSession().getTournament();
        }

        if (tournament == null) {
            if (!pageParameters.get(TID).isNull()) {
                tournamentId = pageParameters.get(TID).toInteger();
            }
        } else if (tournament.getWinPoints() == null) {
            tournamentId = tournament.getId();
        }

        if (tournamentId != null) {
            tournament = tournamentService.getTournament(new Tournament()._setId(tournamentId));
        }

        if (tournament != null) {
            if (this instanceof TournamentOverviewPage) {
                if (BooleanUtils.isNotTrue(tournament.getPublish())) {
                    throw new RestartResponseAtInterceptPageException(PublicTournamentPage.class);
                }
            }
            return tournament;
        }

        throw new RestartResponseAtInterceptPageException(TournamentPage.class);
    }

    protected Groups getGroup(PageParameters parameters, Tournament tournament) {
        return getGroup(parameters, tournament, false);
    }

    protected Groups getGroup(PageParameters parameters, Tournament tournament, boolean ignoreSession) {
        Groups group = null;
        if (!parameters.get(GID).isNull()) {
            group = groupService.getGroupById(parameters.get(GID).toInteger());
        }
        if (group == null) {
            group = groupService.getGroupByTournament(tournament);
        }
        return group;
    }

    public TournamentAuthenticatedWebSession getTournamentSession() {
        return ((TournamentAuthenticatedWebSession) getSession());
    }

    @Override
    protected void onRender() {
        logger.trace("Render start: " + getClass());
        long time = System.currentTimeMillis();
        super.onRender();
        time = System.currentTimeMillis() - time;
        logger.trace("Render end: " + time + " ms");
    };

    // automaticke prihlasovanie pri zapametani
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
        addOrReplace(new Label("heading", newHeadingModel()));
        super.onBeforeRender();
    }
}
