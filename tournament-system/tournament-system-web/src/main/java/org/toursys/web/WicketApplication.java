package org.toursys.web;

import java.util.Locale;
import java.util.Properties;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

public class WicketApplication extends WebApplication {

    boolean isInitialized = false;
    private Locale locale;
    private Properties appProps;

    public static WicketApplication get() {
        Application application = Application.get();

        if (application instanceof WicketApplication == false) {
            throw new WicketRuntimeException("The application attached to the current thread is not a "
                    + WicketApplication.class.getSimpleName());
        }

        return (WicketApplication) application;
    }

    public Locale getLocale() {
        return locale;
    }

    private void mountPages() {
        mountPage("home", HomePage.class);
        mountPage("public", PublicPage.class);
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

    private void addListeners() {
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }

    public String getAppProp(String key) {
        return appProps.getProperty(key);
    }

    public static String getFilesPath() {
        String path = WebApplication.get().getServletContext().getRealPath("/") + "/files/";
        return path;
    }

}