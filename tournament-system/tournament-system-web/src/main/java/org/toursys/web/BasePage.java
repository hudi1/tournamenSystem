package org.toursys.web;

import java.util.Locale;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
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
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toursys.processor.TournamentPageParameter;
import org.toursys.processor.service.FinalStandingService;
import org.toursys.processor.service.GameService;
import org.toursys.processor.service.GroupService;
import org.toursys.processor.service.ParticipantService;
import org.toursys.processor.service.PlayOffGameService;
import org.toursys.processor.service.PlayerService;
import org.toursys.processor.service.ScheduleService;
import org.toursys.processor.service.SeasonService;
import org.toursys.processor.service.TournamentService;
import org.toursys.processor.service.UserService;
import org.toursys.repository.model.Groups;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.model.User;
import org.toursys.web.session.TournamentAuthenticatedWebSession;

public abstract class BasePage extends WebPage implements TournamentPageParameter {

    private static final long serialVersionUID = 1L;

    @SpringBean(name = "playerService")
    protected PlayerService playerService;

    @SpringBean(name = "gameService")
    protected GameService gameService;

    @SpringBean(name = "scheduleService")
    protected ScheduleService scheduleService;

    @SpringBean(name = "seasonService")
    protected SeasonService seasonService;

    @SpringBean(name = "userService")
    protected UserService userService;

    @SpringBean(name = "groupService")
    protected GroupService groupService;

    @SpringBean(name = "participantService")
    protected ParticipantService participantService;

    @SpringBean(name = "playOffGameService")
    protected PlayOffGameService playOffGameService;

    @SpringBean(name = "finalStandingService")
    protected FinalStandingService finalStandingService;

    @SpringBean(name = "tournamentService")
    protected TournamentService tournamentService;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    abstract protected IModel<String> newHeadingModel();

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
        Component homePage = new BookmarkablePageLink<Void>("homePageMain", HomePage.class).add(new AttributeModifier(
                "class", new ActiveReplaceModel(this instanceof HomePage)));
        Component tournamentPage = new BookmarkablePageLink<Void>("tournamentPage", TournamentPage.class).add(
                new AttributeModifier("class", new ActiveReplaceModel(this instanceof TournamentPage))).setVisible(
                false);
        Component seasonPage = new BookmarkablePageLink<Void>("seasonPage", SeasonPage.class).add(
                new AttributeModifier("class", new ActiveReplaceModel(this instanceof SeasonPage))).setVisible(false);
        Component statisticPage = new BookmarkablePageLink<Void>("statisticPage", StatisticPage.class).add(
                new AttributeModifier("class", new ActiveReplaceModel(this instanceof StatisticPage)))
                .setVisible(false);
        Component playerPage = new BookmarkablePageLink<Void>("playerPage", PlayerPage.class).add(
                new AttributeModifier("class", new ActiveReplaceModel(this instanceof PlayerPage))).setVisible(false);
        Component userPage = new BookmarkablePageLink<Void>("userPage", UserPage.class).add(
                new AttributeModifier("class", new ActiveReplaceModel(this instanceof UserPage))).setVisible(false);
        Component logoutPage = new BookmarkablePageLink<Void>("logoutPage", LogoutPage.class).setVisible(false);
        Component loginPage = new BookmarkablePageLink<Void>("loginPage", LoginPage.class).add(
                new AttributeModifier("class", new ActiveReplaceModel(this instanceof LoginPage))).setVisible(false);
        Component registrationPage = new BookmarkablePageLink<Void>("registrationPage", RegistrationPage.class,
                getPageParameters()).add(
                new AttributeModifier("class", new ActiveReplaceModel(this instanceof RegistrationPage))).setVisible(
                false);
        Component groupPage = new BookmarkablePageLink<Void>("groupPage", GroupPage.class, groupPageParameter).add(
                new AttributeModifier("class", new ActiveReplaceModel(this instanceof GroupPage))).setVisible(false);
        Component playOffPage = new BookmarkablePageLink<Void>("playOffPage", PlayOffPage.class, getPageParameters())
                .add(new AttributeModifier("class", new ActiveReplaceModel(this instanceof PlayOffPage))).setVisible(
                        false);
        Component finalRankingPage = new BookmarkablePageLink<Void>("finalRankingPage", FinalRankingPage.class,
                getPageParameters()).add(
                new AttributeModifier("class", new ActiveReplaceModel(this instanceof FinalRankingPage))).setVisible(
                false);

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

        if (this instanceof TournamentHomePage) {
            // homePage.setVisible(false);
            registrationPage.setVisible(true);
            groupPage.setVisible(true);
            playOffPage.setVisible(true);
            finalRankingPage.setVisible(true);
            logoutPage.setVisible(true);
        } else {
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
        // System.out.println();

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

    protected Tournament getTournament(PageParameters pageParameters) {
        Tournament tournament = getTournamentSession().getTournament();
        Integer tournamentId = null;

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
            return tournament;
        }

        throw new RestartResponseAtInterceptPageException(TournamentPage.class);
    }

    protected Groups getGroup(PageParameters parameters) {
        Groups group = null;
        if (!parameters.get(GID).isNull()) {
            group = groupService.getGroup(new Groups()._setId(parameters.get(GID).toInteger()));
        }
        if (group == null) {
            group = groupService.getGroup(new Groups()._setTournament(getTournament(parameters))._setName("1"));
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
