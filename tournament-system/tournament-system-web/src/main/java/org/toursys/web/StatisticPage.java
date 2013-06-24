package org.toursys.web;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.toursys.web.session.TournamentAuthenticatedWebSession;

@AuthorizeInstantiation(Roles.USER)
public class StatisticPage extends BasePage {

    private static final long serialVersionUID = 1L;

    public StatisticPage() {
        add(new Image("work", new PackageResourceReference(StatisticPage.class, "work.png")));
    }

    protected void createPage() {

    }

    @Override
    protected void setVisibility() {
        if (!((TournamentAuthenticatedWebSession) getSession()).isSignedIn()) {
            setVisible(false);
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return Model.of("Statistic page");
    }

}
