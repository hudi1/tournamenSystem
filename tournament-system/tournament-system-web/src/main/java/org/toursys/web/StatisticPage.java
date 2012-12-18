package org.toursys.web;

import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;

public class StatisticPage extends BasePage {

    private static final long serialVersionUID = 1L;

    public StatisticPage() {
        add(new Image("work", new PackageResourceReference(StatisticPage.class, "work.png")));
    }

    protected void createPage() {

    }

    @Override
    protected IModel<String> newHeadingModel() {
        return Model.of("Statistic page");
    }

}
