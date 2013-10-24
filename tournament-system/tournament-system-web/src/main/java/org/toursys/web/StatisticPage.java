package org.toursys.web;

import java.util.Locale;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.ContextRelativeResource;

@AuthorizeInstantiation(Roles.USER)
public class StatisticPage extends BasePage {

    private static final long serialVersionUID = 1L;

    public StatisticPage() {
        createPage();
    }

    protected void createPage() {
        add(new Image("work", new ContextRelativeResource(getLocaleImagePath("/img/work.png"))));
    }

    private String getLocaleImagePath(String path) {
        Locale locale = Session.get().getLocale();
        String[] splitPath = path.split("\\.");
        String finalPath = splitPath[0] + "_" + locale.getLanguage() + "." + splitPath[1];
        return finalPath;
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return Model.of("Statistic page");
    }

}
