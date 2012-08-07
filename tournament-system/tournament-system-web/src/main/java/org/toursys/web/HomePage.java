package org.toursys.web;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Welcome screen
 * 
 */
public class HomePage extends BasePage {

    private static final long serialVersionUID = 1L;

    public HomePage() {
        super();
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return Model.of("Welcome to the tournament system");
    }
}