package org.toursys.web;

import org.apache.wicket.RestartResponseAtInterceptPageException;

public class LoginPage extends BasePage {
    private static final long serialVersionUID = 1L;

    public LoginPage() {
        if (getTournamentSession().isSignedIn()) {
            throw new RestartResponseAtInterceptPageException(getApplication().getHomePage());
        }
        createPage();
    }

    private void createPage() {
        add(new TournamentSignInPanel("signInPanel"));
    }

}
