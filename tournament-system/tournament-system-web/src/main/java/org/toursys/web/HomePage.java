package org.toursys.web;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

public class HomePage extends BasePage {

    private static final long serialVersionUID = 1L;

    public HomePage() {
        createPage();
    }

    private void createPage() {
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("welcome");
    }
}
