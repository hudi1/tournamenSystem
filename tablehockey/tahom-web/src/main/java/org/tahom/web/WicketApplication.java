package org.tahom.web;

import org.apache.wicket.Application;
import org.apache.wicket.ConverterLocator;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.core.request.handler.IPageRequestHandler;
import org.apache.wicket.core.request.handler.PageProvider;
import org.apache.wicket.core.request.handler.RenderPageRequestHandler;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.lang.Exceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.tahom.processor.TournamentException;
import org.tahom.repository.model.Results;
import org.tahom.repository.model.Surname;
import org.tahom.web.converter.ResultConverter;
import org.tahom.web.converter.SurnameConverter;
import org.tahom.web.finder.SpringStringResourceLoader;
import org.tahom.web.session.TournamentAuthenticatedWebSession;

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
			// getMarkupSettings().setDefaultAfterDisabledLink("");
			this.getResourceSettings().setResourcePollFrequency(null);
			getDebugSettings().setDevelopmentUtilitiesEnabled(true);
			getApplicationSettings().setAccessDeniedPage(HomePage.class);
			// getApplicationSettings().setInternalErrorPage(HomePage.class);
			// getExceptionSettings().setUnexpectedExceptionDisplay(IExceptionSettings.SHOW_INTERNAL_ERROR_PAGE);

			this.getRequestCycleListeners().add(new AbstractRequestCycleListener() {
				@Override
				public IRequestHandler onException(RequestCycle cycle, Exception e) {
					TournamentException myE = Exceptions.findCause(e, TournamentException.class);
					if (myE != null) {
						IPageRequestHandler handler = cycle.find(IPageRequestHandler.class);
						if (handler != null) {
							if (handler.isPageInstanceCreated()) {
								WebPage page = (WebPage) (handler.getPage());
								page.error(page.getString(myE.getCode()));
								return new RenderPageRequestHandler(new PageProvider(page));
							}
						}
					} else {
						// TODO errorPage
						// return new RenderPageRequestHandler(new PageProvider(ErrorPage.class));
					}

					return super.onException(cycle, e);
				}
			});
			loadI18nMessages();
		}
	}

	@Override
	protected IConverterLocator newConverterLocator() {
		ConverterLocator converter = new ConverterLocator();
		converter.set(Results.class, ResultConverter.getInstance());
		converter.set(Surname.class, SurnameConverter.getInstance());
		return converter;
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