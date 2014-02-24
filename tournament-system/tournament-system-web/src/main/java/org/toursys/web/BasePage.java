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
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.model.TournamentImpl;
import org.toursys.repository.model.User;
import org.toursys.web.session.TournamentAuthenticatedWebSession;

public abstract class BasePage extends WebPage {

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

    private IModel<String> headingModel = new Model<String>();

    abstract protected IModel<String> newHeadingModel();

    protected FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel");

    public BasePage() {
        this(null);
    }

    public BasePage(PageParameters parameters) {
        super(parameters);
        addMyComponents();
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
        Component tournamentPage = new BookmarkablePageLink<Void>("tournamentPage", TournamentPage.class)
                .add(new AttributeModifier("class", new ActiveReplaceModel(this instanceof TournamentPage)));
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
        add(tournamentPage);
        add(statisticPage);
        add(playerPage);
        add(userPage);
        add(logoutPage);
        add(loginPage);
        if (!((TournamentAuthenticatedWebSession) getSession()).isSignedIn()) {
            tournamentPage.setVisible(false);
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

        Link<User> userProfileLink = new Link<User>("userProfile", new AbstractReadOnlyModel<User>() {

            private static final long serialVersionUID = 1L;

            @Override
            public User getObject() {
                if (getSession() == null)
                    return null;
                if (getSession() instanceof TournamentAuthenticatedWebSession) {
                    return ((TournamentAuthenticatedWebSession) getSession()).getUser();
                }
                return null;
            }

        }) {

            private static final long serialVersionUID = 1L;

            @Override
            public void onClick() {
                User user = ((TournamentAuthenticatedWebSession) getSession()).getUser();
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
                    User user = ((TournamentAuthenticatedWebSession) getSession()).getUser();
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

    protected TournamentImpl getTournament(PageParameters parameters) {
        Tournament tournamentDb = tournamentService.getTournament(new Tournament()._setId(parameters
                .get("tournamentid").toInteger()));
        if (tournamentDb == null) {
            return null;
        }
        return new TournamentImpl(tournamentDb);
    }

    protected Groups getGroup(PageParameters parameters) {
        Groups group = groupService.getGroup(new Groups()._setId(parameters.get("groupid").toInteger()));
        if (group == null) {
            group = groupService.getGroup(new Groups()._setTournament(getTournament(parameters))._setName("1"));
        }
        return group;
    }

    public TournamentAuthenticatedWebSession getTournamentSession() {
        return ((TournamentAuthenticatedWebSession) getSession());
    }

    protected Season getSeason(User user) {
        if (user.getSeasons().isEmpty()) {
            return new Season();
        } else {
            Integer id = user.getSeasons().get(0).getId();
            Season season = new Season();
            season.setId(id);
            season.setInit(Season.Association.tournaments);
            return seasonService.getSeason(season);
        }
    }

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
        super.onBeforeRender();
    }
}
