package org.toursys.web;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.sqlproc.engine.SqlProcessorException;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.User;

@AuthorizeInstantiation(Roles.USER)
public class PlayerEditPage extends BasePage {

    private static final long serialVersionUID = 1L;
    private User user;

    public PlayerEditPage() {
        this(new PageParameters());
    }

    public PlayerEditPage(PageParameters pageParameters) {
        super(pageParameters);
        this.user = getTournamentSession().getUser();

        createPage(new Player()._setUser(user));
    }

    protected void createPage(Player player) {
        add(new PlayerForm(player));
    }

    private class PlayerForm extends Form<Player> {

        private static final long serialVersionUID = 1L;

        public PlayerForm(final Player player) {
            super("playerEditForm", new CompoundPropertyModel<Player>(player));

            addPlayerTextFields();
            addSubmitButton(player);
            addBackButton();
        }

        private void addBackButton() {
            add(new Button("back", new ResourceModel("back")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    setResponsePage(PlayerPage.class, getPageParameters());
                };
            }.setDefaultFormProcessing(false));
        }

        private void addSubmitButton(final Player player) {
            add(new Button("submit", new ResourceModel("submit")) {

                private static final long serialVersionUID = 1L;

                @Override
                public void onSubmit() {
                    try {
                        if (player.getSurname().contains(" ")) {
                            player.setPlayerDiscriminator(player.getSurname().split(" ")[1].substring(0,
                                    Math.min(player.getSurname().split(" ")[1].length(), 3)));
                            player.setSurname(player.getSurname().split(" ")[0]);
                        }
                        if (player.getId() != null) {
                            playerService.updatePlayer(player);
                        } else {
                            playerService.createPlayer(player);
                        }
                    } catch (SqlProcessorException e) {
                        logger.error("Error edit player: ", e);
                        error(getString("sql.db.exception"));
                        return;
                    }
                    setResponsePage(PlayerPage.class, getPageParameters());
                }
            });
        }

        private void addPlayerTextFields() {
            add(new RequiredTextField<String>("name"));
            add(new RequiredTextField<String>("surname"));
            add(new TextField<String>("club"));
        }
    }

    @Override
    protected IModel<String> newHeadingModel() {
        return new ResourceModel("editPlayer");
    }

}
