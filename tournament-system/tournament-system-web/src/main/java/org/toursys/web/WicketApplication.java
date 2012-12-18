package org.toursys.web;

import java.util.Locale;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

public class WicketApplication extends WebApplication {

    boolean isInitialized = false;

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
        mountPage("seasonEdit", SeasonEditPage.class);
        mountPage("season", SeasonPage.class);
        mountPage("tournament", TournamentPage.class);
        mountPage("tournamentEdit", TournamentEditPage.class);
        mountPage("registration", RegistrationPage.class);
        mountPage("group", GroupPage.class);
        mountPage("player", PlayerPage.class);
        mountPage("playerEdit", PlayerEditPage.class);
        mountPage("schedule", SchedulePage.class);
        mountPage("options", TournamentOptionsPage.class);
        mountPage("playOff", PlayOffPage.class);
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
            getMarkupSettings().setStripWicketTags(true);
            getMarkupSettings().setCompressWhitespace(true);
            getMarkupSettings().setDefaultAfterDisabledLink("");
            this.getResourceSettings().setResourcePollFrequency(null);
        }
    }

    private void initConfiguration() {
    }

    @Override
    public Session newSession(Request request, Response response) {
        Session session = new TournamentSession(request);
        session.setLocale(Locale.US);
        return session;
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

}