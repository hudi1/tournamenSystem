package org.toursys.web;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

public class LoginPage extends BasePage {
    private static final long serialVersionUID = 1L;

    public LoginPage() {
        createPage();
    }

    private void createPage() {
        add(new TournamentSignInPanel("signInPanel"));
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("login");
    }
}
