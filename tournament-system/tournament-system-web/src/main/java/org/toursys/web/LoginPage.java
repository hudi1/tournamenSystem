package org.toursys.web;


public class LoginPage extends BasePage {
    private static final long serialVersionUID = 1L;

    public LoginPage() {
        createPage();
    }

    private void createPage() {
        add(new TournamentSignInPanel("signInPanel"));
    }

}
