package org.toursys.web;

import org.apache.wicket.request.mapper.parameter.PageParameters;

public class LogoutPage extends BasePage {

    private static final long serialVersionUID = 1L;

    public LogoutPage(final PageParameters pageParameters) {
        // TODO keep language after logout
        getSession().invalidate();
        setResponsePage(getApplication().getHomePage());
    }
}
