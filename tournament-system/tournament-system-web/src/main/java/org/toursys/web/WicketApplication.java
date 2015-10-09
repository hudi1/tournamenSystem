package org.toursys.web;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.toursys.web.finder.SpringStringResourceLoader;
import org.toursys.web.session.TournamentAuthenticatedWebSession;

public class WicketApplication extends AuthenticatedWebApplication {

    boolean isInitialized = false;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public static WicketApplication get() {
        Application application = Application.get();

        if (application instanceof WicketApplication == false) {
            throw new WicketRuntimeException("The application attached to the current thread is not a "
                    + WicketApplication.class.getSimpleName());
        }

        return (WicketApplication) application;
    }

    private void mountPages() {
        mountPage("home", HomePage.class);
        mountPage("statistic", StatisticPage.class);
        mountPage("season", SeasonPage.class);
        mountPage("tournament", TournamentPage.class);
        mountPage("registration", RegistrationPage.class);
        mountPage("group", GroupPage.class);
        mountPage("player", PlayerPage.class);
        mountPage("playerInfo", PlayerEditPage.class);
        mountPage("schedule", SchedulePage.class);
        mountPage("options", TournamentOptionsPage.class);
        mountPage("playOff", PlayOffPage.class);
        mountPage("user", UserPage.class);
        mountPage("userInfo", UserEditPage.class);
        mountPage("login", LoginPage.class);
        mountPage("logout", LogoutPage.class);
        mountPage("compare", ComparePage.class);
        mountPage("finalStandings", FinalStandingsPage.class);
        mountPage("tournamentHome", TournamentHomePage.class);
        mountPage("publicTournament", PublicTournamentPage.class);
        mountPage("tournamentOverview", TournamentOverviewPage.class);
        mountPage("wchPage", WChPage.class);
    }

    private void mountResource() {
        getSharedResources().add("delete", new ContextRelativeResource("img/delete.png"));
        getSharedResources().add("enter", new ContextRelativeResource("img/enter.png"));
    }

    @Override
    protected void init() {
        if (!isInitialized) {
            super.init();
            addListeners();
            isInitialized = true;
            Injector.get().inject(this);
            initConfiguration();
            mountPages();
            mountResource();
            getMarkupSettings().setStripWicketTags(true);
            getMarkupSettings().setCompressWhitespace(true);
            getMarkupSettings().setDefaultAfterDisabledLink("");
            this.getResourceSettings().setResourcePollFrequency(null);
            getDebugSettings().setDevelopmentUtilitiesEnabled(true);
            // getApplicationSettings().setInternalErrorPage(HomePage.class);
            // getExceptionSettings().setUnexpectedExceptionDisplay(IExceptionSettings.SHOW_INTERNAL_ERROR_PAGE);

            this.getRequestCycleListeners().add(new AbstractRequestCycleListener() {
                @Override
                public IRequestHandler onException(RequestCycle cycle, Exception e) {
                    return super.onException(cycle, e);
                    // return new RenderPageRequestHandler(new PageProvider(new ExceptionPage(e)));
                }
            });
            loadI18nMessages();
        }
    }

    private void loadI18nMessages() {
        getResourceSettings().getStringResourceLoaders()
                .add(new SpringStringResourceLoader(WebApplicationContextUtils
                        .getWebApplicationContext(getServletContext())));
    }

    private void initConfiguration() {
    }

    private void addListeners() {
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }

    public static String getFilesPath() {
        String path = WebApplication.get().getServletContext().getRealPath("/") + "/";
        return path;
    }

    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
        return TournamentAuthenticatedWebSession.class;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
        return LoginPage.class;
    }

}