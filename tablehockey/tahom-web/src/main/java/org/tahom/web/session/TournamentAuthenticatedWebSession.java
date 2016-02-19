package org.tahom.web.session;

import java.util.Locale;

import org.apache.wicket.Session;
import org.apache.wicket.authentication.IAuthenticationStrategy;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.cookies.CookieUtils;
import org.tahom.processor.service.user.UserService;
import org.tahom.processor.util.TournamentUtil;
import org.tahom.repository.model.Season;
import org.tahom.repository.model.Tournament;
import org.tahom.repository.model.User;

public class TournamentAuthenticatedWebSession extends AuthenticatedWebSession {

	@SpringBean(name = "userService")
	protected UserService userService;
	private static final long serialVersionUID = 1L;
	private Roles roles = new Roles();
	private User user;
	private Season season;
	private Tournament tournament;
	private CookieUtils cookieUtils = new CookieUtils();

	private final String LOCALE_KEY = "LC";

	public TournamentAuthenticatedWebSession(Request request) {
		super(request);
		Injector.get().inject(this);
		rememberMeSignIn();
		loadLocale();
	}

	private void loadLocale() {
		String locale = cookieUtils.load(LOCALE_KEY);
		if (locale != null) {
			setLocale(new Locale(locale));
		} else {
			setLocale(Locale.US);
		}
	}

	@Override
	public Session setLocale(Locale locale) {
		Session session = super.setLocale(locale);
		cookieUtils.save(LOCALE_KEY, locale.getLanguage());
		return session;
	}

	private void rememberMeSignIn() {
		if (isSignedIn() == false) {
			IAuthenticationStrategy authenticationStrategy = getApplication().getSecuritySettings()
			        .getAuthenticationStrategy();
			String[] data = authenticationStrategy.load();
			if ((data != null) && (data.length > 1)) {
				signIn(data[0], data[1]);
			}
		}
	}

	@Override
	public boolean authenticate(final String username, final String password) {
		User user = userService.getUser(new User()._setUserName(username)._setInit(User.Association.seasons));
		roles.clear();
		if (user != null && user.getPassword().equals(TournamentUtil.encryptUserPassword(password))) {
			this.user = user;
			if (user.getRole() != null) {
				roles.add(user.getRole().getName());
			}
			roles.add(Roles.USER);
			return true;
		}
		return false;
	}

	@Override
	public Roles getRoles() {
		return roles;
	}

	public User getUser() {
		return user;
	}

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	public Tournament getTournament() {
		return tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

}
