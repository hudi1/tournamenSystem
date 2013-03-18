package org.toursys.web;

import java.util.Locale;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.toursys.processor.service.TournamentService;
import org.toursys.repository.model.User;

public class TournamentAuthenticatedWebSession extends AuthenticatedWebSession {

    @SpringBean(name = "tournamentService")
    protected TournamentService tournamentService;
    private static final long serialVersionUID = 1L;
    private Roles roles = new Roles();
    private User user;

    public TournamentAuthenticatedWebSession(Request request) {
        super(request);
        setLocale(Locale.US);
        Injector.get().inject(this);
    }

    @Override
    public boolean authenticate(final String username, final String password) {
        this.user = tournamentService.getUser(new User()._setUserName(username));
        roles.clear();
        if (user != null && user.getPassword().equals(tournamentService.encryptUserPassword(password))) {
            if (user.getRole() != null) {
                roles.add(user.getRole());
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

}
