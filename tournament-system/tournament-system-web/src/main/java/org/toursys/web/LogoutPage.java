package org.toursys.web;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class LogoutPage extends BasePage {

    private static final long serialVersionUID = 1L;

    public LogoutPage(final PageParameters pageParameters) {
        getSession().invalidate();
        setResponsePage(getApplication().getHomePage());
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("logout");
    }

}
