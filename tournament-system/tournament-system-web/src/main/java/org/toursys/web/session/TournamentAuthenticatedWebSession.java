package org.toursys.web.session;

import java.util.Locale;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.toursys.processor.service.UserService;
import org.toursys.processor.util.TournamentUtil;
import org.toursys.repository.model.Season;
import org.toursys.repository.model.Tournament;
import org.toursys.repository.model.User;

public class TournamentAuthenticatedWebSession extends AuthenticatedWebSession {

    @SpringBean(name = "userService")
    protected UserService userService;
    private static final long serialVersionUID = 1L;
    private Roles roles = new Roles();
    private User user;
    private Season season;
    private Tournament tournament;

    public TournamentAuthenticatedWebSession(Request request) {
        super(request);
        setLocale(Locale.US);
        Injector.get().inject(this);
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
